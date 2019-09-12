package com.kencorhealth.campaign.dm.common;

import com.google.common.net.UrlEscapers;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.jasypt.util.text.StrongTextEncryptor;

public class CampaignUtil {
    public static Calendar utcCalendar() {
        return new GregorianCalendar(TimeZone.getTimeZone("GMT"));
    }
    
    public static long utcTime() {
        Calendar calendar = utcCalendar();
        return calendar.getTimeInMillis();
    }
    
    public static <T> T extractFromData(Class<T> dataClass, Object... data) {
	T retVal = null;

	for (Object item : data) {
            Class itemClass = item.getClass();

            if (itemClass == dataClass ||
                dataClass.isAssignableFrom(itemClass)) {
		retVal = (T) item;
		break;
            }
	}

	return retVal;
    }
    
    public static String smartLowerCase(String str) {
        String retVal = null;
        
        if (valid(str)) {
            retVal = Character.toLowerCase(str.charAt(0)) + str.substring(1);
        }
        
        return retVal;
    }
    
    public static String uniqueString() {
        return UUID.randomUUID().toString();
    }
    
    public static String base64Encode(String str) {
        String retVal = null;
        
        if (valid(str)) {
            try {
                retVal =
                    Base64.getEncoder().encodeToString(
                        str.getBytes("UTF-8")
                    );
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        
        return retVal;
    }
    
    public static String base64Decode(String str) {
        String retVal = null;
        
        if (valid(str)) {
            try {
                byte[] bytes = Base64.getDecoder().decode(str);
                retVal = new String(bytes, "UTF-8");
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
        
        return retVal;
    }
    
    public static Crypter crypter() {
        return new Crypter() {
            @Override
            public String encrypt(String plain, SaltProvider sp) {
                StrongTextEncryptor st = new StrongTextEncryptor();
                st.setPassword(sp.provideSalt());
                String encrypted = st.encrypt(plain);
                return base64Encode(encrypted);
            }

            @Override
            public String decrypt(String encrypted, SaltProvider sp) {
                encrypted = base64Decode(encrypted);
                StrongTextEncryptor st = new StrongTextEncryptor();
                st.setPassword(sp.provideSalt());
                return st.decrypt(encrypted);
            }
        };
    }
    
    public static String hash(String token) {
        String retVal = null;

        if (valid(token)) {
            String encrypted =
                new BasicPasswordEncryptor().encryptPassword(token);
                
            retVal = base64Encode(encrypted);
        }

        return retVal;
    }
    
    public static boolean verify(String clear, String hashed) {
        boolean retVal = false;

        if (valid(clear) && valid(hashed)) {
            String decoded = base64Decode(hashed);
            
            retVal =
                new BasicPasswordEncryptor().checkPassword(clear, decoded);
        }

        return retVal;
    }
    
    public static boolean verify(
        String clear, String encrypted, SaltProvider sp) {
        String decrypted = crypter().decrypt(encrypted, sp);

        return decrypted.equals(clear);
    }
    
    public static String protect(Object obj) {
        return "********";
    }
    
    public static boolean valid(String str) {
        return str != null && str.length() > 0;
    }

    public static boolean valid(Collection<?> values) {
        return values != null && values.size() > 0;
    }

    public static String urlSafe(String str) {
        String retVal = null;
        
        if (valid(str)) {
            retVal = UrlEscapers.urlFragmentEscaper().escape(str);
        }
        
        return retVal;
    }
}
