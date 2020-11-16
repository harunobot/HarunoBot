/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.onebot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.harunobot.console.configuration.PluginPermissionProperties;
import io.github.harunobot.core.onebot.OnebotApiHandler;
import io.github.harunobot.core.onebot.OnebotProtoHandler;
import io.github.harunobot.core.onebot.base.OnebotTransmitter;
import io.github.harunobot.core.onebot.util.ApiGenerator;
import io.github.harunobot.core.proto.onebot.api.OnebotApi;
import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.proto.onebot.api.type.Status;
import io.github.harunobot.core.proto.onebot.event.OnebotEvent;
import io.github.harunobot.core.proto.onebot.event.message.ArrayMessage;
import io.github.harunobot.core.proto.onebot.event.type.ArrayMessageDataType;
import io.github.harunobot.core.proto.onebot.event.type.SubType;
import io.github.harunobot.exception.HarunoIllegalArgumentException;
import io.github.harunobot.plugin.HarunoPlugin;
import io.github.harunobot.plugin.HarunoPluginCallback;
import io.github.harunobot.plugin.data.PluginAccessControlConstant;
import io.github.harunobot.plugin.data.ResponseActionWrapper;
import io.github.harunobot.plugin.data.PluginFilterWrapper;
import io.github.harunobot.plugin.data.PluginHandlerWrapper;
import io.github.harunobot.plugin.data.PluginRegistration;
import io.github.harunobot.plugin.data.PluginAccessControlWrapper;
import io.github.harunobot.plugin.data.PluginPermissionsWrapper;
import io.github.harunobot.plugin.data.type.BlockType;
import io.github.harunobot.plugin.data.type.Permission;
import io.github.harunobot.plugin.data.type.PluginRecivevType;
import io.github.harunobot.plugin.data.type.PluginTextType;
import io.github.harunobot.plugin.data.type.ResponseActionType;
import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.plugin.util.BotEventUtils;
import io.github.harunobot.proto.request.BotRequest;
import io.github.harunobot.proto.request.type.RequestType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 *
 * @author iTeam_VEP
 */
public class PluginManager extends PluginLoader implements HarunoPluginCallback, OnebotProtoHandler {   //, EventHandler<BotEvent>, EventTranslatorOneArg<BotEvent, OnebotEvent>
    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(PluginManager.class);
    static{MDC.put("module", "PluginManager");}
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private long selfId = 0L;
    private Map<String, List<PluginHandlerWrapper>> textPrefixHandlers = new HashMap();
    private Map<String, List<PluginHandlerWrapper>> textSuffixHandlers = new HashMap();
    private List<PluginHandlerWrapper> imageHandlers = new ArrayList();
    private List<PluginHandlerWrapper> atHandlers = new ArrayList();
    private List<PluginFilterWrapper> pluginFilters = new ArrayList();
    
    private Map<String, PluginPermissionsWrapper> pluginPermissions = new HashMap();
    
    private ExecutorService pluginPool = Executors.newCachedThreadPool();
    private ScheduledExecutorService scheduledTaskPool = Executors.newScheduledThreadPool(4);
    private Cache<Long, ResponseActionWrapper> echoCache = Caffeine.newBuilder()
                                .expireAfterWrite(90, TimeUnit.SECONDS)
                                .maximumSize(1000)
                                .build();
    private AtomicLong serialId = new AtomicLong(1);
    private OnebotTransmitter transmitter;
    
    public void init(List<PluginPermissionProperties> permissionProperties){
        if(permissionProperties == null){
            LOG.warn("plugin permission properties is empty");
            return;
        }
        permissionProperties.forEach(properties -> {
            pluginPermissions.put(properties.getId(), new PluginPermissionsWrapper(properties.getId(), properties.getPermissions(), properties));
        });
    }
    
    @Override
    void manageUnload(HarunoPlugin plugin, PluginRegistration registration){
        String pluginId = plugin.id();
        if(registration.filters() != null){
            registration.filters().forEach((parameter, filter) -> {
                pluginFilters.removeIf(filterWrapper -> pluginId.equals(filterWrapper.id()));
                MDC.put("module", "PluginManager");
                LOG.info("plugin {} filter {} unloaded", plugin.id(), parameter.name());
            });
        }
        
        if(registration.handlers() != null){
            registration.handlers().forEach((matcher, handler) -> {
                switch(matcher.matcherType()){
                    case TEXT:
                        if(matcher.textType() == PluginTextType.PREFIX){
                            unloadHandler(pluginId, textPrefixHandlers.get(matcher.trait()));
                        }
                        if(matcher.textType() == PluginTextType.SUFFIX){
                            unloadHandler(pluginId, textSuffixHandlers.get(matcher.trait()));
                        }
                        break;
                    case IMAGE:
                        break;
                    case TOME:
                        break;
                    default:
                        return;
                }
            });
        }
    }
    
    private void unloadHandler(String pluginId, List<PluginHandlerWrapper> plugins){
        for (Iterator<PluginHandlerWrapper> iterator = plugins.iterator(); iterator.hasNext();) {
        PluginHandlerWrapper handlerWrapper= iterator.next();
          if (pluginId.equals(handlerWrapper.id())) {
             // Remove the current element from the iterator and the list.
             iterator.remove();
          }
      }
    }
    
    @Override
    void manageLoad(HarunoPlugin plugin, PluginRegistration registration){
        String pluginId = plugin.id();
        plugin.initPlugin(this);
        
        if(pluginPermissions.containsKey(pluginId)){
            LOG.info("plugin {} permissions preset exist", pluginId);
        } else {
            pluginPermissions.put(pluginId, new PluginPermissionsWrapper(pluginId, registration.permissions(), null));
        }
        PluginPermissionsWrapper permissionsWrapper = pluginPermissions.get(pluginId);
        
        PluginAccessControlWrapper aclWrapper = new PluginAccessControlWrapper(
                pluginId, 
                registration.permissions(), 
                permissionsWrapper.publicUser(), 
                permissionsWrapper.privateUser(), 
                BlockType.DEFAULT
        );
        if(registration.filters() != null){
            registration.filters().forEach((parameter, filter) -> {
                pluginFilters.add(new PluginFilterWrapper(pluginId, parameter.recivevType(), aclWrapper, parameter.priority(), filter));
                MDC.put("module", "PluginManager");
                LOG.info("plugin {} filter {} loaded", plugin.id(), parameter.name());
            });
        }
        
        if(registration.handlers() != null){
            registration.handlers().forEach((matcher, handler) -> {
                switch(matcher.matcherType()){
                    case TEXT:
                        if(matcher.textType() == PluginTextType.PREFIX){
                            if(!textPrefixHandlers.containsKey(matcher.trait())){
                                textPrefixHandlers.put(matcher.trait(), new ArrayList());
                            }
                            textPrefixHandlers.get(matcher.trait()).add(new PluginHandlerWrapper(pluginId, matcher.recivevType(), aclWrapper, handler));
                        }
                        if(matcher.textType() == PluginTextType.SUFFIX){
                            if(!textSuffixHandlers.containsKey(matcher.trait())){
                                textSuffixHandlers.put(matcher.trait(), new ArrayList());
                            }
                            textSuffixHandlers.get(matcher.trait()).add(new PluginHandlerWrapper(pluginId, matcher.recivevType(), aclWrapper, handler));
                        }
                        break;
                    case IMAGE:
                        break;
                    case TOME:
                        break;
                    default:
                        return;
                }
            });
        }
    }
    
    @Override
    void loadCompleted(){
        Collections.sort(pluginFilters, (filter1, filter2) -> {
            return filter1.priority() - filter2.priority();
        });
    }
    
    @Override
    void unloadCompleted(){
        
    }
    
    private void handleMessage(OnebotEvent event, PluginRecivevType recivevType, Permission permission){
        BotEvent botEvent = BotEventUtils.convertMessage(event);
        long group = event.getGroupId();
        
        if(!pluginFilters.isEmpty()) {
            for(PluginFilterWrapper wrapper:pluginFilters){
                if(!wrapper.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                    continue;
                }
                
                if(!wrapper.filter().toNext(botEvent)){
                    return;
                }
            }
        }
        ArrayMessage[] array = (ArrayMessage[]) event.getMessage();
        ArrayMessage message = array[0];
        if(message.getType() == ArrayMessageDataType.REPLY){
            int lastIndex = array.length-1;
            for(int i=1; i<array.length; i++){
                if(array[i].getType() != ArrayMessageDataType.AT){
                    if(array[i].getType() == ArrayMessageDataType.TEXT){
                        if(!((String) array[i].getData().get(ArrayMessageDataType.TEXT.key()[0])).trim().isBlank()){
                            message = array[i];
                            break;
                        }
                    }else if(i == lastIndex){
                        LOG.warn("get reply message with unhandled type {}", JsonUtils.objectToJson(event));
                        message = array[i];
                        break;
                    }
                }
            }
        }
        if(message.getType() == ArrayMessageDataType.AT && Long.valueOf((String) message.getData().get("qq")) == selfId){
            atHandlers.forEach(plugin -> {
                if(!plugin.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                    return;
                }
                pluginPool.execute(() -> handleBotRequest(plugin.id(), botEvent, plugin.handler().handle(null, botEvent)));
            });
        }
        if(message.getType() == ArrayMessageDataType.TEXT){
            String msg = (String) message.getData().get(ArrayMessageDataType.TEXT.key()[0]);
            textPrefixHandlers.forEach((trait, plugins) -> {
                if(msg.startsWith(trait)){
                    plugins.forEach(plugin -> {
                        if(!plugin.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                            return;
                        }
                        pluginPool.execute(() -> handleBotRequest(plugin.id(), botEvent, plugin.handler().handle(trait, botEvent)));
                    });
                }
            });
        }
        if(array[array.length - 1].getType() == ArrayMessageDataType.TEXT){
            String msg = (String) array[array.length - 1].getData().get(ArrayMessageDataType.TEXT.key()[0]);
            textSuffixHandlers.forEach((trait, plugins) -> {
                if(msg.startsWith(trait)){
                    plugins.forEach(plugin -> {
                        if(!plugin.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                            return;
                        }
                        pluginPool.execute(() -> handleBotRequest(plugin.id(), botEvent, plugin.handler().handle(trait, botEvent)));
                    });
                }
            });
        }
    }
    
    @Override
    public void handlePrivateMessage(OnebotEvent event){
        MDC.put("module", "PrivateMessage");
        if(event.getSubType() == SubType.FRIEND){
            handleMessage(event, PluginRecivevType.PRIVATE, Permission.RECEIVE_PRIVATE_MESSAGE);
        }
        if(event.getSubType() == SubType.GROUP){
            handleMessage(event, PluginRecivevType.PRIVATE, Permission.RECEIVE_PRIVATE_MESSAGE);
        }
        if(event.getSubType() == SubType.OTHER){
            try {
                LOG.warn("{}", objectMapper.writeValueAsString(event));
            } catch (JsonProcessingException ex) {
            }
        }
        MDC.clear();
    }
    
    @Override
    public void handleGroupMessage(OnebotEvent event){
        MDC.put("module", "GroupMessage");
        if(event.getSubType() == SubType.NORMAL){
            handleMessage(event, PluginRecivevType.PUBLIC, Permission.RECEIVE_PUBLIC_MESSAGE);
            
            StringBuilder sb = new StringBuilder();
            if(event.getMessage() instanceof String){
                sb.append("group ").append(event.getGroupId()).append("\tqq ").append(event.getUserId()).append("\t msg ").append(event.getMessage());
            } else {
                ArrayMessage[] array = (ArrayMessage[]) event.getMessage();
                sb.append("group ").append(event.getGroupId()).append(" qq ").append(event.getUserId()).append("\t");
                for(ArrayMessage message:array){
                    if(message.getType() == ArrayMessageDataType.TEXT){
                        sb.append((String) message.getData().get(ArrayMessageDataType.TEXT.key()[0]));
                    } else {
                        try{
                            if(message.getType().key() != null){
                                sb.append(message.getType().name()).append(" - ").append(((String) message.getData().get(message.getType().key()[0]).toString()));
                            } else {
                                System.out.println("type is null");
                            }
                            sb.append(message.getType().name()).append(" - ").append(((String) message.getData().get(message.getType().key()[0]).toString()));
                        }catch(Exception ex){
                            LOG.info("", ex);
                            ex.toString();
                            LOG.info("{}", JsonUtils.objectToJson(message));
//                            System.out.println(message);
                        }
                    } 
                }
                LOG.info("{}", sb.toString());
//                System.out.println(sb.toString());
            }
        }
        if(event.getSubType() == SubType.ANONYMOUS){
            
        }
        if(event.getSubType() == SubType.NOTICE){
            try {
                LOG.warn("{}", objectMapper.writeValueAsString(event));
            } catch (JsonProcessingException ex) {
            }
        }
        MDC.clear();
    }
    
    @Override
    public void handleMetaEventMessage(OnebotEvent event){
        
    }
    
    @Override
    public void handleNoticeMessage(OnebotEvent event){
        
    }
    
    @Override
    public void handleRequestMessage(OnebotEvent event){
        
    }
    
    @Override
    public void handleDefaultMessage(OnebotEvent event){
        
    }
    
    @Override
    public void handleApiResponse(OnebotApiResponse apiResponse){
        MDC.put("module", "ApiResponse");
        LOG.info("ret: {} {} {} {}"
                , apiResponse.getRetcode()
                , apiResponse.getStatus()
                , apiResponse.getEcho()
                , JsonUtils.objectToJson(apiResponse.getData()));
        if(apiResponse.getEcho() > 0){
            ResponseActionWrapper actionWrapper = echoCache.getIfPresent(apiResponse.getEcho());
            if(actionWrapper.getAction() == ResponseActionType.DELETE){
                scheduledTaskPool.schedule(
                    ()->{
                        try {
                            handleApiRequest(new ApiGenerator.DeleteMsg().messageId(apiResponse.getData().get("message_id").asInt()).build());
                        } catch (HarunoIllegalArgumentException ex) {
                            LOG.info("scheduled task execute failed", ex);
                        }
                    }
                , actionWrapper.getDuration(), TimeUnit.SECONDS);
            }
        }
        MDC.clear();
    }

    private void handleApiRequest(OnebotApi api) {
        transmitter.transmit(api);
        MDC.put("module", "ApiRequest");
        LOG.info("{}", api.getAction());
        MDC.clear();
    }
    
    private boolean allow(){
        return true;
    } 
    
    private void handleBotRequest(BotRequest request) {
        MDC.put("module", "handleBotRequest");
        try{
            long sid = -1;
            if(request.getAlivetime()> 0){
                sid = serialId.getAndIncrement();
                if(sid >= Long.MAX_VALUE){
                    serialId.set(1);
                }
                echoCache.put(sid, new ResponseActionWrapper(sid, request.getAlivetime()));
            }
            if(request.getRequestType()== RequestType.MESSAGE_PRIVATE){
                handleApiRequest(new ApiGenerator.SendPrivateMsg().ssid(sid).userId(request.getUserId()).message(BotEventUtils.convertMessage(request.getMessages(), request.isDestruct())).build());
            }
            if(request.getRequestType() == RequestType.MESSAGE_PUBLIC){
                handleApiRequest(new ApiGenerator.SendGroupMsg().ssid(sid).groupId(request.getGroupId()).message(BotEventUtils.convertMessage(request.getMessages(), request.isDestruct())).build());
            }
            if(request.getRequestType() == RequestType.MUTE){
                handleApiRequest(new ApiGenerator.SetGroupBan().groupId(request.getGroupId()).userId(request.getUserId()).duration(request.getMuteDuration()).build());
            }
        } catch (HarunoIllegalArgumentException ex) {
            LOG.info("", ex);
        }
        MDC.clear();
    }
    
    private void handleBotRequest(String id, BotEvent event, BotRequest request) {
        if(request == null){
            return;
        }
        request.setBotId(event.botId());
        request.setGroupId(event.groupId());
        request.setChannelId(event.channelId());
        request.setUserId(event.userId());
        if(!allowPluginRequest(id, request)){
            
            return;
        }
        handleBotRequest(request);
    }
    
    public void gmsg(long groupId){
        try {
            handleApiRequest(new ApiGenerator.GetGroupMemberList().groupId(groupId).build());
        } catch (HarunoIllegalArgumentException ex) {
            LOG.info("", ex);
        }
    }
    
    @Override
    public void pluginBotRequest(String id, BotRequest request){
        if(!allowPluginRequest(id, request)){
            
            return;
        }
        handleBotRequest(request);
    }
    
    @Override
    public long pluginMarkBotRequest(String id, BotRequest request){
        if(!allowPluginRequest(id, request)){
            
            return -1;
        }
        long ssid = -1;
        handleBotRequest(request);
        return ssid;
    }
    
    private boolean allowPluginRequest(String id, BotRequest request){
        return true;
    }

    @Override
    public void setTransmitter(OnebotTransmitter transmitter) {
        this.transmitter = transmitter;
    }
    
//    @Override
//    public long pluginSendPrivateMessage(long userId, BotMessage[] meaasges, boolean mark, long duration) {
//        long ssid = -1;
//        try {
//            handleApiRequest(new ApiGenerator.SendPrivateMsg().userId(userId).message(BotEventUtils.convertMessage(meaasges, duration)).build());
//        } catch (HarunoIllegalArgumentException ex) {
//            
//        }
//        return ssid;
//    }
//
//    @Override
//    public long pluginSendGroupMessage(long groupId, BotMessage[] meaasges, boolean mark, long duration) {
//        long ssid = -1;
//        try {
//            handleApiRequest(new ApiGenerator.SendGroupMsg().groupId(groupId).message(BotEventUtils.convertMessage(meaasges, duration)).build());
//        } catch (HarunoIllegalArgumentException ex) {
//            
//        }
//        return ssid;
//    }
    
}
