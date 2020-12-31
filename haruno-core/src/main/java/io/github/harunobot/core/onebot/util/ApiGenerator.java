/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.onebot.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.awt.TrayIcon.MessageType;
import java.util.HashMap;
import java.util.Map;
import io.github.harunobot.exception.HarunoIllegalArgumentException;
import io.github.harunobot.core.proto.onebot.api.OnebotApi;
import io.github.harunobot.core.proto.onebot.api.type.ApiAction;
import io.github.harunobot.core.proto.onebot.event.message.Anonymous;
import io.github.harunobot.core.proto.onebot.event.message.ArrayMessage;
import io.github.harunobot.core.proto.onebot.event.type.HonorType;
import io.github.harunobot.core.proto.onebot.event.type.OutFormat;
import io.github.harunobot.core.proto.onebot.event.type.SubType;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeam_VEP
 */
public class ApiGenerator {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ApiGenerator.class);
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    private static final String protoToJson(OnebotApi onebotApi){
        try {
            return objectMapper.writeValueAsString(onebotApi);
        } catch (JsonProcessingException ex) {
        }
        return "";
    }
    
    private static class ParamsBuilder {
        private Map<String, Object> params = new HashMap();
        
        private ParamsBuilder(){
//            params.put("auto_escape", true);
        }
        
        public void userId(long userId){
            params.put("user_id", userId);
        }
        
        public void message(String message){
            params.put("message", message);
        }
        
        public void message(ArrayMessage[] messages){
            params.put("message", messages);
        }
        
        public void autoEscape(boolean autoEscape){
            params.put("auto_escape", autoEscape);
        }
        
        public void reason(String reason){
            params.put("reason", reason);
        }
        
        public void groupId(long groupId){
            params.put("group_id", groupId);
        }
        
        public void id(String id){
            params.put("id", id);
        }
        
        public void times(int times){
            params.put("times", times);
        }
        
        public void messageId(long messageId){
            params.put("message_id", messageId);
        }
        
        public void rejectAddRequest(boolean rejectAddRequest){
            params.put("reject_add_request", rejectAddRequest);
        }
        
        public void duration(int duration){
            params.put("duration", duration);
        }
        
        public void anonymous(Anonymous anonymous){
            params.put("anonymous", anonymous);
        }
        
        public void anonymousFlag(String anonymousFlag){
            params.put("anonymous_flag ", anonymousFlag);
        }
        
        public void enable(boolean enable){
            params.put("enable", enable);
        }
        
        public void card(String card){
            params.put("card", card);
        }
        
        public void groupName(String groupName){
            params.put("group_name", groupName);
        }
        
        public void isDismiss(boolean isDismiss){
            params.put("is_dismiss", isDismiss);
        }
        
        public void specialTitle(String specialTitle){
            params.put("special_title", specialTitle);
        }
        
        public void approve(boolean approve){
            params.put("approve", approve);
        }
        
        public void remark(String remark){
            params.put("remark", remark);
        }
        
        public void flag(String flag){
            params.put("flag", flag);
        }
        
        public void subType(SubType subType){
            params.put("sub_type", subType);
        }
        
        public void messageType(MessageType messageType){
            params.put("message_type", messageType);
        }
        
        public void nickname(String nickname){
            params.put("nickname", nickname);
        }
        
        public void noCache(boolean noCache){
            params.put("no_cache", noCache);
        }
        
        public void honorType(HonorType honorType){
            params.put("type", honorType);
        }
        
        public void domain(String domain){
            params.put("domain", domain);
        }
        
        public void file(String file){
            params.put("file", file);
        }
        
        public void outFormat(OutFormat outFormat){
            params.put("out_format", outFormat);
        }
        
        public void delay(long delay){
            params.put("delay", delay);
        }
        
        public Map<String, Object> values() throws HarunoIllegalArgumentException {
            return params;
        }
    }
    
    public static class SendPrivateMsg {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public SendPrivateMsg ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public SendPrivateMsg userId(long userId){
            params.userId(userId);
            return this;
        }
        
        public SendPrivateMsg message(String message){
            params.message(message);
            return this;
        }
        
        public SendPrivateMsg message(ArrayMessage[] messages){
            params.message(messages);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SEND_PRIVATE_MSG, params.values(), ssid);
        }
    }
    
    public static class SendGroupMsg {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public SendGroupMsg ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public SendGroupMsg groupId(long groupId){
            params.groupId(groupId);
            return this;
        }
        
        public SendGroupMsg message(String message){
            params.message(message);
            return this;
        }
        
        public SendGroupMsg message(ArrayMessage[] messages){
            params.message(messages);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SEND_GROUP_MSG, params.values(), ssid);
        }
    }
    
    public static class SendMsg {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public SendMsg ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public SendMsg messageType(MessageType messageType){
            params.messageType(messageType);
            return this;
        }
        
        public SendMsg userId(long userId){
            params.userId(userId);
            return this;
        }
        
        public SendMsg groupId(long groupId){
            params.groupId(groupId);
            return this;
        }
        
        public SendMsg message(String message){
            params.message(message);
            return this;
        }
        
        public SendMsg message(ArrayMessage[] messages){
            params.message(messages);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 4){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SEND_MSG, params.values(), ssid);
        }
    }
    
    public static class DeleteMsg {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public DeleteMsg ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public DeleteMsg messageId(long messageId){
            params.messageId(messageId);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.DELETE_MSG, params.values(), ssid);
        }
    }
    
    public static class GetMsg {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public GetMsg ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public GetMsg messageId(long messageId){
            params.messageId(messageId);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_MSG, params.values(), ssid);
        }
    }
    
    public static class GetForwardMsg {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void id(String id){
            params.id(id);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_FORWARD_MSG, params.values(), ssid);
        }
    }
    
    public static class SendLike {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void userId(long userId){
            params.userId(userId);
        }
        
        public void times(int times){
            params.times(times);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SEND_LIKE, params.values(), ssid);
        }
    }
    
    public static class SetGroupKick  {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public SetGroupKick ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public SetGroupKick groupId(long groupId){
            params.groupId(groupId);
            return this;
        }
        
        public SetGroupKick userId(long userId){
            params.userId(userId);
            return this;
        }
        
        public SetGroupKick rejectAddRequest(boolean rejectAddRequest){
            params.rejectAddRequest(rejectAddRequest);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 3){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_KICK, params.values(), ssid);
        }
    }
    
    public static class SetGroupBan {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public SetGroupBan ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public SetGroupBan groupId(long groupId){
            params.groupId(groupId);
            return this;
        }
        
        public SetGroupBan userId(long userId){
            params.userId(userId);
            return this;
        }
        
        public SetGroupBan duration(int duration){
            params.duration(duration);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 3){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_BAN, params.values(), ssid);
        }
    }
    
    public static class SetGroupAnonymousBan  {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void anonymous(Anonymous anonymous){
            params.anonymous(anonymous);
        }
        
        public void anonymousFlag (String anonymousFlag){
            params.anonymousFlag(anonymousFlag);
        }
        
        public void duration(int duration){
            params.duration(duration);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 4){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_ANONYMOUS_BAN, params.values(), ssid);
        }
    }
    
    public static class SetGroupWholeBan  {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void enable(boolean enable){
            params.enable(enable);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_WHOLE_BAN, params.values(), ssid);
        }
    }
    
    public static class SetGroupAdmin  {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void userId(long userId){
            params.userId(userId);
        }
        
        public void enable(boolean enable){
            params.enable(enable);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 3){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_ADMIN, params.values(), ssid);
        }
    }
    
    public static class SetGroupAnonymous  {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void enable(boolean enable){
            params.enable(enable);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_ANONYMOUS, params.values(), ssid);
        }
    }
    
    public static class SetGroupCard   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void userId(long userId){
            params.userId(userId);
        }
        
        public void card(String card){
            params.card(card);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 3){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_CARD, params.values(), ssid);
        }
    }
    
    public static class SetGroupName   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void groupName(String groupName){
            params.groupName(groupName);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_NAME, params.values(), ssid);
        }
    }
    
    public static class SetGroupLeave   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void isDismiss(boolean isDismiss){
            params.isDismiss(isDismiss);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_LEAVE, params.values(), ssid);
        }
    }
    
    public static class SetGroupSpecialTitle   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void userId(long userId){
            params.userId(userId);
        }
        
        public void specialTitle(String specialTitle){
            params.specialTitle(specialTitle);
        }
        
        public void duration(int duration){
            params.duration(duration);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 4){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_SPECIAL_TITLE, params.values(), ssid);
        }
    }
    
    public static class SetFriendAddRequest   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void flag(String flag){
            params.flag(flag);
        }
        
        public void remark(String remark){
            params.remark(remark);
        }
        
        public void approve(boolean approve){
            params.approve(approve);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 3){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_FRIEND_ADD_REQUEST, params.values(), ssid);
        }
    }
    
    public static class SetGroupAddRequest   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public SetGroupAddRequest ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public SetGroupAddRequest flag(String flag){
            params.flag(flag);
            return this;
        }
        
        public SetGroupAddRequest reason(String reason){
            params.reason(reason);
            return this;
        }
        
        public SetGroupAddRequest subType (SubType subType){
            params.subType(subType);
            return this;
        }
        
        public SetGroupAddRequest approve(boolean approve){
            params.approve(approve);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 4){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_GROUP_ADD_REQUEST, params.values(), ssid);
        }
    }
    
    public static class GetLoginInfo {
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            return new OnebotApi(ApiAction.GET_LOGIN_INFO, null, ssid);
        }
    }
    
    public static class GetStrangerInfo {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void userId(long userId){
            params.userId(userId);
        }
        
        public void noCache(boolean noCache){
            params.noCache(noCache);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_STRANGER_INFO, params.values(), ssid);
        }
    }
    
    public static class GetFriendList    {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void userId(long userId){
            params.userId(userId);
        }
        
        public void nickname(String nickname){
            params.nickname(nickname);
        }
        
        public void remark(String remark){
            params.remark(remark);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 3){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_FRIEND_LIST, params.values(), ssid);
        }
    }
    
    public static class GetGroupInfo    {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void noCache(boolean noCache){
            params.noCache(noCache);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_GROUP_INFO, params.values(), ssid);
        }
    }
    
    public static class GetGroupList {
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            return new OnebotApi(ApiAction.GET_GROUP_LIST, null, ssid);
        }
    }
    
    public static class GetGroupMemberInfo {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void userId(long userId){
            params.userId(userId);
        }
        
        public void noCache(boolean noCache){
            params.noCache(noCache);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 3){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_GROUP_MEMBER_INFO, params.values(), ssid);
        }
    }
    
    public static class GetGroupMemberList   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public GetGroupMemberList ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public GetGroupMemberList groupId(long groupId){
            params.groupId(groupId);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_GROUP_MEMBER_LIST, params.values(), ssid);
        }
    }
    
    public static class GetGroupSystemMsg   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public GetGroupSystemMsg ssid(long ssid){
            this.ssid = ssid;
            return this;
        }
        
        public GetGroupSystemMsg groupId(long groupId){
            params.groupId(groupId);
            return this;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_GROUP_SYSTEM_MSG, params.values(), ssid);
        }
    }
    
    public static class GetGroupHonorInfo   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void groupId(long groupId){
            params.groupId(groupId);
        }
        
        public void honorType(HonorType honorType){
            params.honorType(honorType);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_GROUP_HONOR_INFO, params.values(), ssid);
        }
    }
    
    public static class GetCookies   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void domain(String domain){
            params.domain(domain);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_COOKIES, params.values(), ssid);
        }
    }
    
    public static class GetCsrfToken   {
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            return new OnebotApi(ApiAction.GET_CSRF_TOKEN, null, ssid);
        }
    }
    
    public static class GetCredentials   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void domain(String domain){
            params.domain(domain);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_CREDENTIALS, params.values(), ssid);
        }
    }
    
    public static class GetRecord {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void file(String file){
            params.file(file);
        }
        
        public void outFormat(OutFormat outFormat){
            params.outFormat(outFormat);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 2){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_RECORD, params.values(), ssid);
        }
    }
    
    public static class GetImage {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void file(String file){
            params.file(file);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.GET_IMAGE, params.values(), ssid);
        }
    }
    
    public static class CanSendImage   {
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            return new OnebotApi(ApiAction.CAN_SEND_IMAGE, null, ssid);
        }
    }
    
    public static class CanSendRecord   {
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            return new OnebotApi(ApiAction.CAN_SEND_RECORD, null, ssid);
        }
    }
    
    public static class GetStatus   {
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            return new OnebotApi(ApiAction.GET_STATUS, null, ssid);
        }
    }
    
    public static class GetVersionInfo   {
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            return new OnebotApi(ApiAction.GET_VERSION_INFO, null, ssid);
        }
    }
    
    public static class SetRestart   {
        private final ParamsBuilder params = new ParamsBuilder();
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public void delay(long delay){
            params.delay(delay);
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            if(params.values().size() != 1){
                throw new HarunoIllegalArgumentException();
            }
            return new OnebotApi(ApiAction.SET_RESTART, null, ssid);
        }
    }
    
    public static class CleanCache   {
        private long ssid;
        
        public void ssid(long ssid){
            this.ssid = ssid;
        }
        
        public OnebotApi build() throws HarunoIllegalArgumentException {
            return new OnebotApi(ApiAction.CLEAN_CACHE, null, ssid);
        }
    }
}
