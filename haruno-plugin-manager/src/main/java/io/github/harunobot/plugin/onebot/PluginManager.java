/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin.onebot;

import com.diabolicallabs.vertx.cron.CronObservable;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.RunAutomaton;
import io.github.harunobot.async.BotResponseCallback;
import io.github.harunobot.concurrent.VertxExecutor;
import io.github.harunobot.console.configuration.PluginPermissionProperties;
import io.github.harunobot.core.onebot.OnebotHttpApiClient;
import io.github.harunobot.core.onebot.OnebotProtoHandler;
import io.github.harunobot.core.onebot.base.OnebotTransmitter;
import io.github.harunobot.core.onebot.util.ApiGenerator;
import io.github.harunobot.core.proto.onebot.api.OnebotApi;
import io.github.harunobot.core.proto.onebot.api.OnebotApiResponse;
import io.github.harunobot.core.proto.onebot.event.OnebotEvent;
import io.github.harunobot.core.proto.onebot.event.message.ArrayMessage;
import io.github.harunobot.core.proto.onebot.event.type.ArrayMessageDataType;
import io.github.harunobot.core.proto.onebot.event.type.SubType;
import io.github.harunobot.exception.HarunoIllegalArgumentException;
import io.github.harunobot.plugin.HarunoPlugin;
import io.github.harunobot.plugin.data.PluginAccessControlConstant;
import io.github.harunobot.plugin.data.ResponseActionWrapper;
import io.github.harunobot.plugin.data.PluginFilterWrapper;
import io.github.harunobot.plugin.data.PluginHandlerWrapper;
import io.github.harunobot.plugin.data.PluginRegistration;
import io.github.harunobot.plugin.data.PluginAccessControlWrapper;
import io.github.harunobot.plugin.data.PluginPermissionsWrapper;
import io.github.harunobot.plugin.data.type.BlockType;
import io.github.harunobot.pojo.type.Permission;
import io.github.harunobot.plugin.data.type.PluginReceivedType;
import io.github.harunobot.plugin.data.type.PluginTextType;
import io.github.harunobot.plugin.data.type.ResponseActionType;
import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.core.util.BotEventUtils;
import io.github.harunobot.core.util.BotResponseUtils;
import io.github.harunobot.proto.request.BotRequest;
import io.github.harunobot.proto.request.type.RequestType;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.iharu.util.JsonUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import io.github.harunobot.plugin.data.PluginScheduledTaskWrapper;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Timed;
import io.vertx.reactivex.RxHelper;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import io.github.harunobot.plugin.HarunoPluginSupport;
import io.github.harunobot.plugin.PluginHandler;
import io.github.harunobot.plugin.data.PluginWrapper;
import io.github.harunobot.proto.event.type.DirectiveType;
import io.github.harunobot.proto.event.type.SourceType;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;

/**
 *
 * @author iTeam_VEP
 */
public class PluginManager extends PluginLoader implements HarunoPluginSupport, OnebotProtoHandler, AutoCloseable {   //, EventHandler<BotEvent>, EventTranslatorOneArg<BotEvent, OnebotEvent>
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(PluginManager.class);
    static{MDC.put("module", "PluginManager");}
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    private final Vertx vertx;
    private final WorkerExecutor messagePool;
//    private final Executor pluginPool;
    private final Scheduler cronScheduler;
    private final OnebotHttpApiClient httpApiClient;
    private final boolean allowHttp;
    private final Set<Long> selfIds;
    
    private final ExecutorService pluginPool = Executors.newCachedThreadPool();
    
    private Map<String, List<PluginHandlerWrapper>> textPrefixHandlers = new HashMap();
    private Map<String, List<PluginHandlerWrapper>> textFullMatchHandlers = new HashMap();
    private List<PluginHandlerWrapper> textRegexHandlers = new ArrayList();
    private List<PluginHandlerWrapper> imageHandlers = new ArrayList();
    private List<PluginHandlerWrapper> atHandlers = new ArrayList();
    private List<PluginFilterWrapper> pluginFilters = new ArrayList();
    private List<PluginHandlerWrapper> interactiveHandlers = new ArrayList();
    
    private Map<String, PluginPermissionsWrapper> pluginPermissions = new HashMap();
    private Map<String, List<PluginScheduledTaskWrapper>> pluginScheduledTasks = new HashMap();
    
    private final Cache<Long, List<ResponseActionWrapper>> echoCache;
    private AtomicLong serialId = new AtomicLong(1);
    private OnebotTransmitter transmitter;
    private Vertx pluginVertx;
//    private io.vertx.reactivex.core.Vertx pluginRxVertx;
    private WebClient pluginWebClient;
//    private io.vertx.reactivex.ext.web.client.WebClient pluginRxWebClient;

    public PluginManager(List<Long> selfIds, Vertx vertx, OnebotHttpApiClient httpApiClient, Vertx pluginVertx){
        super();
        this.selfIds = new HashSet(selfIds);
        this.vertx = vertx;
        this.pluginVertx = pluginVertx;
        this.pluginWebClient = WebClient.create(pluginVertx, new WebClientOptions().setSsl(true));
//        this.pluginRxVertx = io.vertx.reactivex.core.Vertx.newInstance(pluginVertx);
//        this.pluginRxWebClient = io.vertx.reactivex.ext.web.client.WebClient.create(pluginRxVertx, new WebClientOptions().setSsl(true));
        this.cronScheduler = RxHelper.scheduler(pluginVertx);
        if(httpApiClient != null){
            this.allowHttp = true;
            this.httpApiClient = httpApiClient;
        } else {
            this.allowHttp = false;
            this.httpApiClient = null;
        }
        this.messagePool = vertx.createSharedWorkerExecutor("message-worker-pool"
                , Runtime.getRuntime().availableProcessors()
                , 30
                , TimeUnit.SECONDS);
//        this.pluginPool = new VertxExecutor(this.vertx);
        this.echoCache = Caffeine.newBuilder()
                .executor(new VertxExecutor(this.vertx))
                .expireAfterWrite(90, TimeUnit.SECONDS)
                .maximumSize(1000)
                .build();
    }
    
    public void init(List<PluginPermissionProperties> permissionProperties){
        if(permissionProperties == null){
            LOG.warn("plugin permission properties is empty");
            return;
        }
        permissionProperties.forEach(properties -> {
            pluginPermissions.put(properties.getId(), new PluginPermissionsWrapper(properties.getId(), properties.getPermissions(), properties));
        });
//        this.cronScheduler.start();
    }
    
    @Override
    void manageUnload(HarunoPlugin plugin, PluginRegistration registration){
        String pluginId = plugin.id();
        if(pluginScheduledTasks.containsKey(pluginId)){
            pluginScheduledTasks.get(pluginId).forEach(scheduledTasks -> {
                scheduledTasks.getTask().dispose();
            });
            pluginScheduledTasks.get(pluginId).clear();
        }
        
        if(registration.interactiveHandler()!= null){
            interactiveHandlers.removeIf(pluginWrapper -> pluginId.equals(pluginWrapper.id()));
            MDC.put("module", "PluginManager");
            LOG.info("plugin {} interactive handler unloaded", plugin.id());
        }
        
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
                        if(matcher.textType() == PluginTextType.FULL_MATCH){
                            unloadHandler(pluginId, textFullMatchHandlers.get(matcher.trait()));
                        }
                        if(matcher.textType() == PluginTextType.REGEX){
                            unloadHandler(pluginId, textRegexHandlers);
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
//                LOG.info("plugin {} handler unloaded", handlerWrapper.id());
            }
        }
   }
    
    @Override
    void initPlugin(HarunoPlugin plugin){
        plugin.initPlugin(pluginVertx, pluginWebClient, this);
    }
    
    @Override
    void manageLoad(HarunoPlugin plugin, PluginRegistration registration){
        String pluginId = plugin.id();
        
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
        
        if(registration.interactiveHandler() != null){
            interactiveHandlers.add(new PluginHandlerWrapper(pluginId, PluginReceivedType.BOTH, aclWrapper, registration.interactiveHandler()));
            MDC.put("module", "PluginManager");
            LOG.info("plugin {} interactive handler loaded", plugin.id());
        }
        
        if(registration.filters() != null){
            registration.filters().forEach((parameter, filter) -> {
                pluginFilters.add(new PluginFilterWrapper(pluginId, parameter.receivedType(), aclWrapper, parameter.priority(), filter));
                MDC.put("module", "PluginManager");
                LOG.info("plugin {} filter {} loaded", plugin.id(), parameter.name());
            });
        }
        
        if(registration.handlers() != null){
            registration.handlers().forEach((matcher, handler) -> {
                switch(matcher.matcherType()){
                    case TEXT:
                        String trait = matcher.trait();
                        if(matcher.textType() == PluginTextType.PREFIX){
                            if(!textPrefixHandlers.containsKey(trait)){
                                textPrefixHandlers.put(trait, new ArrayList());
                            }
                            PluginHandlerWrapper wrapper = new PluginHandlerWrapper(pluginId, matcher.recivevType(), aclWrapper, handler);
                            wrapper.setTrait(trait);
                            textPrefixHandlers.get(trait).add(wrapper);
                        }
                        if(matcher.textType() == PluginTextType.FULL_MATCH){
                            if(!textFullMatchHandlers.containsKey(trait)){
                                textFullMatchHandlers.put(trait, new ArrayList());
                            }
                            PluginHandlerWrapper wrapper = new PluginHandlerWrapper(pluginId, matcher.recivevType(), aclWrapper, handler);
                            wrapper.setTrait(trait);
                            textFullMatchHandlers.get(trait).add(wrapper);
                        }
                        if(matcher.textType() == PluginTextType.REGEX){
                            PluginHandlerWrapper wrapper = new PluginHandlerWrapper(pluginId, matcher.recivevType(), aclWrapper, handler);
                            wrapper.setRegex(new RunAutomaton(new RegExp(trait).toAutomaton()));
                            textFullMatchHandlers.get(trait).add(wrapper);
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
    
    private ArrayMessage findMessage(OnebotEvent event){
        MDC.put("module", "findMessage");
        ArrayMessage[] array = (ArrayMessage[]) event.getMessage();
        ArrayMessage message = array[0];
        if(message.getType() == ArrayMessageDataType.REPLY){
            int lastIndex = array.length-1;
            for(int i=1; i<array.length; i++){
                if(array[i].getType() != ArrayMessageDataType.AT){
                    if(array[i].getType() == ArrayMessageDataType.TEXT){
                        if(!((String) array[i].getData().get(ArrayMessageDataType.TEXT.key()[0])).trim().isBlank()){
                            return array[i];
                        }
                    }else if(array[i].getType() == ArrayMessageDataType.IMAGE){
                        return array[i];
                    }else if(i == lastIndex){
                        LOG.warn("get reply message with unhandled type {}", JsonUtils.objectToJson(event));
                        return array[i];
                    }
                }
            }
        }
        if(message.getType() == ArrayMessageDataType.AT){
            return message;
        }
        if(message.getType() == ArrayMessageDataType.TEXT){
            return message;
        }
        if(message.getType() == ArrayMessageDataType.IMAGE){
            return message;
        }
        if(message.getType() != ArrayMessageDataType.FACE){
            LOG.warn("findMessage type unhandled: {}", JsonUtils.objectToJson(event));
        }
        return null;
    }
    
    private void handleMessage(OnebotEvent event, PluginReceivedType recivevType, Permission permission){
        BotEvent botEvent = BotEventUtils.convertMessage(event);
        if(botEvent.messages() == null){
            LOG.error("messages is empty {}", JsonUtils.objectToJson(event));
        }
        long group = event.getGroupId();
        
        messagePool.executeBlocking((Promise<Boolean> promise) -> {
            if(!pluginFilters.isEmpty()) {
                for(PluginFilterWrapper wrapper:pluginFilters){
                    if(!wrapper.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                        continue;
                    }

                    if(!wrapper.filter().toNext(botEvent)){
                        promise.complete(Boolean.FALSE);
                        return;
                    }
                }
            }
            promise.complete(Boolean.TRUE);
        }, false, res -> {
            if(!res.result()){
                return;
            }
            ArrayMessage message = findMessage(event);
            if(message == null){
                return;
            }
            if(message.getType() == ArrayMessageDataType.AT && selfIds.contains(Long.valueOf((String) message.getData().get("qq")))){
                atHandlers.forEach(plugin -> {
                    if(!plugin.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                        return;
                    }
                    pluginPool.execute(() -> handleBotRequest(plugin.id(), botEvent, plugin.handler().handle(null, null, botEvent)));
                });
            }
            if(message.getType() == ArrayMessageDataType.TEXT){
                String msg = ((String) message.getData().get(ArrayMessageDataType.TEXT.key()[0])).trim();
                if(textFullMatchHandlers.containsKey(msg)){
                    textFullMatchHandlers.get(msg).forEach(plugin -> {
                        if(!plugin.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                            return;
                        }
                        pluginPool.execute(() -> handleBotRequest(plugin.id(), botEvent, plugin.handler().handle(plugin.getTrait(), msg, botEvent)));
                    });
                }
                textPrefixHandlers.forEach((trait, plugins) -> {
                    if(msg.startsWith(trait)){
                        plugins.forEach(plugin -> {
                            if(!plugin.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                                return;
                            }
                            pluginPool.execute(() -> handleBotRequest(plugin.id(), botEvent, plugin.handler().handle(trait, msg, botEvent)));
                        });
                    }
                });
                textRegexHandlers.forEach(plugin -> {
                    if(plugin.getRegex().run(msg)){
                        if(!plugin.allow(recivevType, permission, event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                            return;
                        }
                        pluginPool.execute(() -> handleBotRequest(plugin.id(), botEvent, plugin.handler().handle(plugin.getTrait(), msg, botEvent)));
                    }
                });
            }
        });
    }
    
    @Override
    public void handlePrivateMessage(OnebotEvent event){
        MDC.put("module", "PrivateMessage");
        if(event.getSubType() == SubType.FRIEND){
            handleMessage(event, PluginReceivedType.PRIVATE, Permission.RECEIVE_PRIVATE_MESSAGE);
        }
        if(event.getSubType() == SubType.GROUP){
            handleMessage(event, PluginReceivedType.PRIVATE, Permission.RECEIVE_PRIVATE_MESSAGE);
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
            handleMessage(event, PluginReceivedType.PUBLIC, Permission.RECEIVE_PUBLIC_MESSAGE);
            
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
//        MDC.clear();
    }
    
    private void handleInteractive(OnebotEvent event){
        BotEvent botEvent = BotEventUtils.convertMessage(event);
        interactiveHandlers.forEach(plugin -> {
            if(!plugin.allow(botEvent.sourceType()
                    == SourceType.PRIVATE 
                    ? PluginReceivedType.PRIVATE
                    : PluginReceivedType.PUBLIC
                    , botEvent.permission(), event.getGroupId(), PluginAccessControlConstant.GLOBAL_ID.value(), event.getUserId())){
                return;
            }
            pluginPool.execute(() -> handleBotRequest(plugin.id(), botEvent, plugin.handler().handle(null, null, botEvent)));
        });
    }
    
    @Override
    public void handleMetaEventMessage(OnebotEvent event){
        
    }
    
    @Override
    public void handleNoticeMessage(OnebotEvent event){
        handleInteractive(event);
    }
    
    @Override
    public void handleRequestMessage(OnebotEvent event){
        handleInteractive(event);
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
            List<ResponseActionWrapper> actionWrappers = echoCache.getIfPresent(apiResponse.getEcho());
            if(actionWrappers == null){
                return;
            }
            actionWrappers.forEach(actionWrapper -> {
                switch(actionWrapper.getAction()){
                    case DELETE:{
                        vertx.setTimer(actionWrapper.getDuration() * 1000, timepass -> {
                            try {
                                handleApiRequest(new ApiGenerator.DeleteMsg().ssid(-1).messageId(apiResponse.getData().get("message_id").asInt()).build());
                            } catch (HarunoIllegalArgumentException ex) {
                                LOG.info("scheduled task execute failed", ex);
                            }
                        });
                        break;
                    }
                    case CALLBACK:{
                        pluginPool.execute(() -> handlePluginCallback(actionWrapper, apiResponse));
                        break;
                    }
                    default:{
                    }
                }
            });
            
        }
        MDC.clear();
    }
    
    private void handlePluginCallback(ResponseActionWrapper wrapper, OnebotApiResponse apiResponse){
        switch(wrapper.getRequestType()){
            case GET_MESSAGE:{
                if(apiResponse.getData().get("group").asBoolean()){
                    ((ObjectNode)apiResponse.getData()).put("sub_type", "normal");
                } else {
                    ((ObjectNode)apiResponse.getData()).put("sub_type", "friend");
                }
                ((ObjectNode)apiResponse.getData()).put("post_type", "message");
                
                wrapper.getCallback()
                        .handle(BotResponseUtils
                                        .convertMessage(wrapper.getRequestType(), apiResponse));
                break;
            }
        }
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
    
    private <T> long handleBotRequest(String id, BotRequest request, BotResponseCallback<T> callback) {
        MDC.put("module", "handleBotRequest");
        long sid = -1;
        try{
            if(callback != null){
                sid = serialId.getAndIncrement();
                if(sid >= Long.MAX_VALUE){
                    serialId.set(1);
                }
                if(request.getAlivetime()> 0){
                    echoCache.put(sid, Arrays.asList(
                            new ResponseActionWrapper(sid, id, request.getRequestType(), callback),
                            new ResponseActionWrapper(sid, request.getAlivetime())
                    ));
                } else {
                    echoCache.put(sid, Arrays.asList(
                            new ResponseActionWrapper(sid, id, request.getRequestType(), callback)
                    ));
                }
            } else if(request.getAlivetime()> 0){
                sid = serialId.getAndIncrement();
                if(sid >= Long.MAX_VALUE){
                    serialId.set(1);
                }
                echoCache.put(sid, Arrays.asList(new ResponseActionWrapper(sid, request.getAlivetime())));
            }
            if(request.getRequestType()== RequestType.MESSAGE_PRIVATE){
                handleApiRequest(new ApiGenerator.SendPrivateMsg().ssid(sid).userId(request.getUserId()).message(BotEventUtils.convertMessage(request.getMessages())).build());
            }
            if(request.getRequestType() == RequestType.MESSAGE_PUBLIC){
                handleApiRequest(new ApiGenerator.SendGroupMsg().ssid(sid).groupId(request.getGroupId()).message(BotEventUtils.convertMessage(request.getMessages())).build());
            }
            if(request.getRequestType() == RequestType.MUTE){
                handleApiRequest(new ApiGenerator.SetGroupBan().groupId(request.getGroupId()).userId(request.getUserId()).duration(request.getMuteDuration()).build());
            }
            if(request.getRequestType() == RequestType.GET_MESSAGE){
                handleApiRequest(new ApiGenerator.GetMsg().ssid(sid).messageId(request.getMessageId()).build());
            }
            if(request.getRequestType() == RequestType.KICK_MEMBER){
                handleApiRequest(new ApiGenerator.SetGroupKick().ssid(sid).groupId(request.getGroupId()).userId(request.getUserId()).rejectAddRequest(request.isApprove()).build());
            }
            if(request.getRequestType() == RequestType.HANDLE_SOCIAL_ADD_REQUEST){
                if(request.getBotEvent().directiveType() == DirectiveType.PUBLIC_ADD_REQUEST){
                    handleApiRequest(new ApiGenerator.SetGroupAddRequest().ssid(sid).approve(request.isApprove()).reason(request.getInfo()).flag(request.getBotEvent().flag()).subType(SubType.ADD).build());
                } else if(request.getBotEvent().directiveType() == DirectiveType.PUBLIC_INVITE_REQUEST) {
                    handleApiRequest(new ApiGenerator.SetGroupAddRequest().ssid(sid).approve(request.isApprove()).reason(request.getInfo()).flag(request.getBotEvent().flag()).subType(SubType.INVITE).build());
                }
            }
        } catch (HarunoIllegalArgumentException ex) {
            LOG.info("", ex);
        }
        MDC.clear();
        return sid;
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
        handleBotRequest(id, request, null);
    }
    
    @Override
    public ExecutorService executorService(){
        return pluginPool;
    }
    
    @Override
    public <T> void scheduleTask(String pluginId, String taskId, String cron, Consumer<? super Timed<Long>> onNext, Consumer<? super Throwable> onError){
        Disposable disposable = CronObservable.cronspec(cronScheduler, cron, "Etc/UTC")
//          .take(5) //If you only want it to hit 5 times, add this, remove for continuous emission
          .subscribe(onNext, onError);
        if(!pluginScheduledTasks.containsKey(pluginId)){
            pluginScheduledTasks.put(pluginId, new ArrayList());
        } else {
            if(pluginScheduledTasks.get(pluginId).parallelStream()
                    .anyMatch(taskWrapper -> taskId.equals(taskWrapper.getTaskId()))){
                throw new IllegalStateException("Duplicate Task " + taskId);
            }
        }
        pluginScheduledTasks.get(pluginId).add(new PluginScheduledTaskWrapper(taskId, disposable));
    }
    
    @Override
    public void cancelScheduledTask(String pluginId, String taskId){
        if(!pluginScheduledTasks.containsKey(pluginId)){
            LOG.warn("plugin {} doesn't have scheduled tasks", pluginId, taskId);
            return;
        }
        pluginScheduledTasks.get(pluginId)
                .removeIf(taskWrapper -> {
                    if(taskId.equals(taskWrapper.getTaskId())){
                        taskWrapper.getTask().dispose();
                        return true;
                    }
                    return false;
                });
    }
    
//    public <T> void supplyAsync(Supplier<T> supplier, BiConsumer<? super T, ? super Throwable> action, long timeout, TimeUnit unit){
//        CompletableFuture.supplyAsync(supplier)
//                        .orTimeout(timeout, unit)
//                        .whenComplete(action);
//    }
    
    public void gmsg(long groupId){
        try {
            handleApiRequest(new ApiGenerator.GetGroupMemberList().groupId(groupId).build());
        } catch (HarunoIllegalArgumentException ex) {
            LOG.info("", ex);
        }
    }
    
    
    @Override
    public <T> void sendBotRequest(String pluginId, BotRequest request, BotResponseCallback<T> callback) {
        if(!allowPluginRequest(pluginId, request)){
            
            return;
        }
        if(allowHttp){
            
        }
        handleBotRequest(pluginId, request, callback);
    }
    
    @Override
    public void sendBotRequest(String id, BotRequest request){
        if(!allowPluginRequest(id, request)){
            
            return;
        }
        handleBotRequest(id, request, null);
    }
    
    private boolean allowPluginRequest(String id, BotRequest request){
        return true;
    }

    @Override
    public void setTransmitter(OnebotTransmitter transmitter) {
        this.transmitter = transmitter;
    }
    
    @Override
    public void close() throws Exception {
        cronScheduler.shutdown();
        pluginPool.shutdownNow();
        messagePool.close();
        if(httpApiClient != null){
            httpApiClient.close();
        }
        this.pluginWebClient.close();
        this.pluginVertx.close();
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
