/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.plugin;

import io.github.harunobot.plugin.data.PluginDescription;
import io.github.harunobot.plugin.data.PluginRegistration;
import io.github.harunobot.proto.event.BotEvent;
import io.github.harunobot.proto.event.BotMessage;
import io.github.harunobot.proto.request.BotRequest;
import io.github.harunobot.proto.request.type.RequestType;
import org.atteo.classindex.IndexSubclasses;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

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
    
    public void set(){
        
    }
    
    public String get(){
        return null;
    }
    
    private final String id;
    private final String version;
    private final String name;
    private final PluginDescription description;
    private HarunoPluginCallback callback;
    
    protected HarunoPlugin(PluginDescription description){
        this.id = description.id();
        this.version = description.version();
        this.name = description.name();
        this.description = description;
    }
    
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
    
    public final void initPlugin(HarunoPluginCallback callback){
        this.callback = callback;
    }

    @Override
    public void close() throws Exception {
        callback = null;
    }
    
    protected final BotRequest fastReply(RequestType requestType){
        return new BotRequest.Builder().requestType(requestType).build();
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
    
    protected final void sendGroupMessage(long groupId, BotMessage[] meaasges, boolean mark, int alivetime){
        callback.pluginBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(groupId)
                    .requestType(RequestType.MESSAGE_PUBLIC)
                    .messages(meaasges)
                    .alivetime(alivetime)
                    .build()
        );
    }
    
    protected final void sendPrivateMessage(long userId, BotMessage[] meaasges, boolean mark, int alivetime){
        callback.pluginBotRequest(
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
        callback.pluginBotRequest(
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
        callback.pluginBotRequest(
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
        callback.pluginBotRequest(
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
        callback.pluginBotRequest(
                id,
                new BotRequest.Builder()
                    .groupId(groupId)
                    .userId(userId)
                    .requestType(RequestType.MUTE)
                    .muteDuration(duration)
                    .build()
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
