package com.kencorhealth.campaign.json;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bson.Document;

public class JsonUtil {
    public static <T> List<T> asList(Iterable<Document> doc, Type type)
        throws CampaignException {
        List<T> retVal = new ArrayList();
        
        JavaType jt = MAPPER.getTypeFactory().constructType(type);
        
        for (Document item: doc) {
            retVal.add(asObject(item, jt));
        }
        
        return retVal;
    }

    public static <T> Document asDoc(T obj) throws CampaignException {
        Document retVal = null;
        
        if (obj != null) {
            try {
                String json = asJson(obj);
                retVal = Document.parse(json);
            } catch (Exception e) {
                throw new CampaignException(e);
            }
        }
        
        return retVal;
    }
    	
    public static <T> String asJson(T obj) throws CampaignException {
        String retVal = null;
        
        if (obj != null) {
            try {
                retVal = MAPPER.writeValueAsString(obj);
            } catch (Exception e) {
                throw new CampaignException(e);
            }
        }
        
        return retVal;
    }

    public static <T> String asPrettyJson(T obj) throws CampaignException {
        String retVal = null;
        
        if (obj != null) {
            try {
                retVal = PRETTY_MAPPER.writeValueAsString(obj);
            } catch (Exception e) {
                throw new CampaignException(e);
            }
        }
        
        return retVal;
    }

    public static <T> T asObject(Document doc, Type type)
        throws CampaignException {
        T retVal = null;
        
        if (doc != null) {
            try {
                String json = doc.toJson();
                JavaType jt = MAPPER.getTypeFactory().constructType(type);
                retVal = MAPPER.readValue(json, jt);
            } catch (Exception e) {
                throw new CampaignException(e);
            }
        }
        
        return retVal;
    }

    public static <T> T asObject(String json, TypeReference typeReference)
        throws CampaignException {
        return asObject(json, typeReference.getType());
    }
    
    public static <T> T asObject(String json, JavaType jt)
        throws CampaignException {
        T retVal = null;
        
        if (json != null) {
            try {
                retVal = MAPPER.readValue(json, jt);
            } catch (Exception e) {
                throw new CampaignException(e);
            }
        }
        
        return retVal;
    }
    
    public static <T> T asObject(String json, Type type)
        throws CampaignException {
        T retVal = null;
        
        if (json != null) {
            try {
                if (type.equals(String.class)) {
                    retVal = (T) json;
                } else {
                    JavaType jt = MAPPER.getTypeFactory().constructType(type);
                    retVal = MAPPER.readValue(json, jt);
                }
            } catch (Exception e) {
                throw new CampaignException(e);
            }
        }
        
        return retVal;
    }
    
    public static JavaType collectionType(Class outer, Class inner) {
        return
            MAPPER.getTypeFactory().constructCollectionLikeType(outer, inner);
    }

    public static JavaType mapType(Class keyType, Class valueType) {
        return
            MAPPER.getTypeFactory().constructMapType(
                Map.class, keyType, valueType
            );
    }

    private static final ObjectMapper MAPPER;
    private static final ObjectMapper PRETTY_MAPPER;

    static {
        MAPPER = new ObjectMapper();
        PRETTY_MAPPER = new ObjectMapper();
        SimpleModule sm = new SimpleModule();
        
        PRETTY_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
        
        sm.addDeserializer(Long.class, new BsonLongDeserializer());
        MAPPER.setSerializationInclusion(Include.NON_NULL).registerModule(sm);
        PRETTY_MAPPER
            .setSerializationInclusion(Include.NON_NULL)
            .registerModule(sm);
    }
}
