/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.harunobot.core.proto.onebot.event.message.ArrayMessage;
import java.io.IOException;

/**
 *
 * @author iTeam_VEP
 */
public class MessageDeserializer extends JsonDeserializer<Object> {

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException, JsonProcessingException {
        try{
            return jsonParser.readValueAs(ArrayMessage[].class);
        }catch(JsonProcessingException ex){
        }
        return jsonParser.readValueAs(String.class);
    }

}
