package com.kencorhealth.campaign.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BsonLongDeserializer  extends JsonDeserializer<Long> {
    @Override
    public Class<Long> handledType() {
        return Long.class;
    }

    @Override
    public Long deserialize(JsonParser p, DeserializationContext ctxt)
        throws IOException, JsonProcessingException {
        Long retVal = null;
        
        if (p.currentTokenId() == JsonTokenId.ID_START_OBJECT) {
            Map<String, Object> map = ctxt.readValue(p, HashMap.class);
            String valueAsStr = (String) map.get("$numberLong");
            retVal = Long.parseLong(valueAsStr);
        }
        
        return retVal;
    }
}