/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin;

import io.github.harunobot.async.BotResponseCallback;
import io.github.harunobot.plugin.data.PluginDescription;
import io.github.harunobot.plugin.data.PluginRegistration;
import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.proto.event.BotMessage;
import io.github.harunobot.proto.event.type.MessageContentType;
import io.github.harunobot.proto.request.BotRequest;
import io.github.harunobot.proto.request.type.RequestType;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Timed;
import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import org.atteo.classindex.IndexSubclasses;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeam_VEP
 */
@IndexSubclasses
public abstract class HarunoPlugin implements AutoCloseable {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HarunoPlugin.class);
    
    public abstract PluginRegistration onLoad(String path) throws Exception;
    
    public abstract boolean onUnload();
    
    public abstract boolean onEnable();
    
//    public abstract void handleResponse(long sid, RequestType requestType, BotResponse response);
    
    public void set(){
        
    }
    
    public String get(){
        return null;
    }
    
    private final String id;
    private final String version;
    private final String name;
    private final PluginDescription description;
    private Vertx vertx;
    private WebClient webClient;
//    private io.vertx.reactivex.core.Vertx rxVertx;
//    private io.vertx.reactivex.ext.web.client.WebClient rxWebClient;
    private HarunoPluginSupport pluginSupport;
    
    protected HarunoPlugin(PluginDescription description){
        this.id = description.id();
        this.version = description.version();
        this.name = description.name();
        this.description = description;
    }
    
//    public void handleResponse(long sid, BotResponse response){
//        throw new UnsupportedOperationException("Plugin does not override the default method.");
////        throw new NotImplementedException("");
//    }
    
    public boolean onReload(){
        return true;
    }
    
    protected final boolean send() {
        return false;
    }
    
    public final String id(){
        return id;
    }
    
    public final PluginDescription description(){
        return description;
    }
    
    public final void initPlugin(Vertx vertx, WebClient webClient, HarunoPluginSupport pluginSupport){
        this.vertx = vertx;
        this.webClient = webClient;
//        this.rxVertx = rxVertx;
//        this.rxWebClient = rxWebClient;
        this.pluginSupport = pluginSupport;
    }
    
    public ExecutorService executorService(){
        return pluginSupport.executorService();
    }
    
    public <T> void scheduleTask(String schedulId, String cron, Consumer<? super Timed<Long>> onNext, Consumer<? super Throwable> onError){
        pluginSupport.scheduleTask(id, schedulId, cron, onNext, onError);
    }
    
    public void cancelScheduledTask(String taskId){
        pluginSupport.cancelScheduledTask(id, taskId);
    }
    
    protected final <T> void supplyAsync(Supplier<T> supplier, BiConsumer<? super T, ? super Throwable> action, long timeout, TimeUnit unit){
        CompletableFuture.supplyAsync(supplier)
                        .orTimeout(timeout, unit)
                        .whenComplete(action);
    }
    
    protected final Vertx vertx(){
        return this.vertx;
    }
    
    protected final WebClient webClient(){
        return this.webClient;
    }
    
//    protected final io.vertx.reactivex.core.Vertx rxVertx(){
//        return this.rxVertx;
//    }
//    
//    protected final io.vertx.reactivex.ext.web.client.WebClient rxWebClient(){
//        return this.rxWebClient;
//    }

    @Override
    public void close() throws Exception {
        pluginSupport = null;
    }
    
    protected final BotRequest fastReply(RequestType requestType){
        return new BotRequest.Builder().requestType(requestType).build();
    }
    
    protected final BotRequest fastReply(RequestType requestType, String info){
        BotMessage[] meaasges = new BotMessage[1];
        meaasges[0] = new BotMessage.Builder().messageType(MessageContentType.TEXT).data(info).build();
        return new BotRequest.Builder().requestType(requestType).messages(meaasges).build();
    }
    
    protected final BotRequest fastReply(RequestType requestType, BotMessage[] meaasges){
        return new BotRequest.Builder().requestType(requestType).messages(meaasges).build();
    }
    
    protected final BotRequest fastReply(RequestType requestType, BotMessage[] meaasges, int alivetime){
        return new BotRequest.Builder().requestType(requestType).messages(meaasges).alivetime(alivetime).build();
    }
    
    protected final BotRequest generateRequest(BotEvent event, RequestType requestType, BotMessage[] meaasges){
        return new BotRequest.Builder()
                .groupId(event.groupId())
                .userId(event.userId())
                .requestType(requestType)
                .messages(meaasges)
                .build();
    }
    
    protected final void sendGroupMessage(long groupId, BotMessage[] meaasges, int alivetime){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(groupId)
                    .requestType(RequestType.MESSAGE_PUBLIC)
                    .messages(meaasges)
                    .alivetime(alivetime)
                    .build()
        );
    }
    
    protected final void sendGroupMessage(long groupId, String info, int alivetime){
        BotMessage[] meaasges = new BotMessage[1];
        meaasges[0] = new BotMessage.Builder().messageType(MessageContentType.TEXT).data(info).build();
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(groupId)
                    .requestType(RequestType.MESSAGE_PUBLIC)
                    .messages(meaasges)
                    .alivetime(alivetime)
                    .build()
        );
    }
    
    protected final void sendGroupMessageWithMention(long groupId, long userId, String info, int alivetime){
        BotMessage[] meaasges = new BotMessage[2];
        meaasges[0] = new BotMessage.Builder().messageType(MessageContentType.MENTION).data(String.valueOf(userId)).build();
        meaasges[1] = new BotMessage.Builder().messageType(MessageContentType.TEXT).data(info).build();
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(groupId)
                    .requestType(RequestType.MESSAGE_PUBLIC)
                    .messages(meaasges)
                    .alivetime(alivetime)
                    .build()
        );
    }
    
    protected final void sendPrivateMessage(long userId, BotMessage[] meaasges, int alivetime){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .userId(userId)
                    .requestType(RequestType.MESSAGE_PUBLIC)
                    .messages(meaasges)
                    .alivetime(alivetime)
                    .build()
        );
    }
    
    protected final void revokeMuteUser(BotEvent event){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(event.groupId())
                    .userId(event.userId())
                    .requestType(RequestType.MUTE)
                    .muteDuration(0)
                    .build()
        );
    }
    
    protected final void revokeMuteUser(long groupId, long userId){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(groupId)
                    .userId(userId)
                    .requestType(RequestType.MUTE)
                    .muteDuration(0)
                    .build()
        );
    }
    
    protected final void muteUser(BotEvent event, int duration){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(event.groupId())
                    .userId(event.userId())
                    .requestType(RequestType.MUTE)
                    .muteDuration(duration)
                    .build()
        );
    }
    
    protected final void muteUser(long groupId, long userId, int duration){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(groupId)
                    .userId(userId)
                    .requestType(RequestType.MUTE)
                    .muteDuration(duration)
                    .build()
        );
    }
    
    protected final void handleSocialAddRequest(BotEvent event, boolean approve, String info){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .botEvent(event)
                    .approve(approve)
                    .info(info)
                    .requestType(RequestType.HANDLE_SOCIAL_ADD_REQUEST)
                    .build()
        );
    }
    
    protected final void kickMember(long groupId, long userId, boolean approveRejection){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(groupId)
                    .userId(userId)
                    .approve(approveRejection)
                    .requestType(RequestType.KICK_MEMBER)
                    .build()
        );
    }
    
    protected final void deleteMessage(long messageId){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .messageId(messageId)
                    .requestType(RequestType.DELETE)
                    .build()
        );
    }
    
    protected final long getReplySourceMessageId(BotEvent event){
        for(BotMessage botMessage:event.messages()){
            if(botMessage.messageType() == MessageContentType.REPLY){
                return Long.valueOf(botMessage.data());
            }
        }
        throw new IllegalArgumentException();
    }
    
    protected final <T> void fetchMessage(long messageId, BotResponseCallback<T> pluginCallback){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .messageId(messageId)
                    .requestType(RequestType.GET_MESSAGE)
                    .build(),
                pluginCallback
        );
    }
    
    protected final <T> void fetchImage(long messageId, BotResponseCallback<T> pluginCallback){
        pluginSupport.sendBotRequest(
                id,
                new BotRequest.Builder()
                    .messageId(messageId)
                    .requestType(RequestType.GET_MESSAGE)
                    .build(),
                pluginCallback
        );
    }
    
//    protected void logDebug(String log, Object[] args){
//        MDC.put("module", name);
//        LOG.debug(log, args);
//    }
//    
//    protected void logInfo(String log, Object[] args){
//        MDC.put("module", name);
//        LOG.info(log, args);
//    }
//    
//    protected void logWarn(String log, Object[] args){
//        MDC.put("module", name);
//        LOG.warn(log, args);
//    }
//    
//    protected void logError(String log, Object[] args){
//        MDC.put("module", name);
//        LOG.error(log, args);
//    }
    
}
