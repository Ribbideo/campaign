package com.kencorhealth.campaign.dm.common;

import com.google.common.net.UrlEscapers;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Collection;
import java.util.UUID;
import org.jasypt.util.password.BasicPasswordEncryptor;

public class CampaignUtil {
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
    
    public static String encrypt(String token) {
        String retVal = null;

        if (valid(token)) {
            String encrypted =
                new BasicPasswordEncryptor().encryptPassword(token);
                
            retVal = base64Encode(encrypted);
        }

        return retVal;
    }
    
    public static boolean verify(String original, String encrypted) {
        boolean retVal = false;

        if (valid(original) && valid(encrypted)) {
            String decoded = base64Decode(encrypted);
            
            retVal =
                new BasicPasswordEncryptor().checkPassword(original, decoded);
        }

        return retVal;
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
