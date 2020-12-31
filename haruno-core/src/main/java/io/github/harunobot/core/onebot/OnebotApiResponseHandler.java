/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventTranslatorOneArg;
import java.io.IOException;
import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.proto.onebot.api.response.GroupMemberInfo;
import io.github.harunobot.core.proto.onebot.api.type.Status;
import java.util.List;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotApiResponseHandler implements com.lmax.disruptor.EventHandler<OnebotApiResponse>, EventTranslatorOneArg<OnebotApiResponse, JsonNode>  {
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(OnebotApiResponseHandler.class);

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final OnebotProtoHandler protoHandler;
    
    public OnebotApiResponseHandler(OnebotProtoHandler protoHandler){
        this.protoHandler = protoHandler;
    }
    
    @Override
    public void translateTo(OnebotApiResponse apiResponse, long sequence, JsonNode json)
    {
        try {
//            JsonFactory jfactory = new JsonFactory();
//            JsonParser jParser = jfactory.createParser(json.toPrettyString());
//            JsonToken jToken;
//            List<GroupMemberInfo> groupMemberList = null;
//            while ((jToken = jParser.nextToken()) != JsonToken.END_OBJECT) {
//                String fieldname = jParser.getCurrentName();
//                if ("status".equals(fieldname)) {
//                    jToken = jParser.nextToken();
//                    String parsedName = jParser.getText();
//                }
//
//                if ("retcode".equals(fieldname)) {
//                    jToken = jParser.nextToken();
//                    int parsedAge = jParser.getIntValue();
//                }
//                        
//                if ("echo".equals(fieldname)) {
//                    jToken = jParser.nextToken();
//                    long parsedAge = jParser.getLongValue();
//                }
//                
//                if ("data".equals(fieldname)) {
//                    jToken = jParser.nextToken();
//                    groupMemberList = jParser.readValueAs(new TypeReference<List<GroupMemberInfo>>(){});
//                    while ((jToken = jParser.nextToken()) != JsonToken.END_ARRAY) {
//                        groupMemberList = jParser.readValueAs(new TypeReference<List<GroupMemberInfo>>(){});
////                        addresses.add(jParser.getText());
//                    }
//                }
//            }
//            jParser.close();
            
            objectMapper.readerForUpdating(apiResponse).readValue(json);
        } catch (JsonProcessingException ex) {
            MDC.put("module", "OnebotApiResponseHandler");
            LOG.error("translate failed: {}", json.toString(), ex);
            MDC.clear();
        } catch (IOException ex) {
            MDC.put("module", "OnebotApiResponseHandler");
            LOG.error("translate failed: {}", json.toString(), ex);
            MDC.clear();
        }
    }
    
    @Override
    public void onEvent(OnebotApiResponse apiResponse, long sequence, boolean endOfBatch)
    {
        if(apiResponse.getStatus() == Status.FAILED) {
            try {
                MDC.put("module", "OnebotApiResponseHandler");
                LOG.warn("handleApi sequence: {} endOfBatch: {} {}", sequence, endOfBatch, objectMapper.writeValueAsString(apiResponse));  
                MDC.clear();
            } catch (JsonProcessingException ex) {
                LOG.error("", ex);
                return;
            }
        }
        try {
            protoHandler.handleApiResponse(apiResponse);
        } catch (Exception ex) {
            LOG.error("", ex);
        }
    }
    
    
}
