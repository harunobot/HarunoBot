/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.core.proto.onebot.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.harunobot.core.proto.onebot.event.message.Anonymous;
import io.github.harunobot.core.proto.onebot.event.message.Sender;
import io.github.harunobot.core.proto.onebot.event.notice.OnebotFile;
import io.github.harunobot.core.proto.onebot.event.type.HonorType;
import io.github.harunobot.core.proto.onebot.event.type.MetaEventType;
import io.github.harunobot.core.proto.onebot.event.type.NoticeType;
import io.github.harunobot.core.proto.onebot.event.type.PostType;
import io.github.harunobot.core.proto.onebot.event.type.RequestType;
import io.github.harunobot.core.proto.onebot.event.type.MessageType;
import io.github.harunobot.core.proto.onebot.event.type.SubType;

/**
 *
 * @author iTeam_VEP
 */
public class OnebotEvent {
    
    private long time;
    @JsonProperty(value="self_id")
    private long selfId;
    @JsonProperty(value="post_type")
    private PostType postType;
    @JsonProperty(value="message_id")
    private int messageId;
    @JsonProperty(value="group_id")
    private long groupId;
    @JsonProperty(value="user_id")
    private long userId;
    @JsonProperty(value="operator_id")
    private long operatorId;
    @JsonProperty(value="target_id")
    private long targetId;
    @JsonProperty(value="message_type")
    private MessageType messageType;
    @JsonProperty(value="sub_type")
    private SubType subType;
    @JsonProperty(value="meta_event_type")
    private MetaEventType metaEventType;
    @JsonProperty(value="notice_type")
    private NoticeType noticeType;
    @JsonProperty(value="honor_type")
    private HonorType honorType;
    @JsonProperty(value="request_type")
    private RequestType requestType;
    @JsonProperty(value="raw_message")
    private String rawMessage;
    @JsonProperty(value="sender_id")
    private long senderId;
    @JsonProperty(value="real_id")
    private long realId;
    @JsonDeserialize(using = MessageDeserializer.class)
    private Object message;
    private boolean group;
    private Anonymous anonymous;
    private OnebotFile file;
    private long duration;
    private Object status;
    private long interval;
    private Sender sender;
    private String comment;
    private String flag;
    private int font;
    @JsonProperty(value="_post_method")
    private int postMethod;
    @JsonProperty(value="card_new")
    private String groupCardNew;
    @JsonProperty(value="card_old")
    private String groupCardOld;

    /**
     * @return the time
     */
    public long getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * @return the selfId
     */
    public long getSelfId() {
        return selfId;
    }

    /**
     * @param selfId the selfId to set
     */
    public void setSelfId(long selfId) {
        this.selfId = selfId;
    }

    /**
     * @return the postType
     */
    public PostType getPostType() {
        return postType;
    }

    /**
     * @param postType the postType to set
     */
    public void setPostType(PostType postType) {
        this.postType = postType;
    }

    /**
     * @return the messageId
     */
    public int getMessageId() {
        return messageId;
    }

    /**
     * @param messageId the messageId to set
     */
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    /**
     * @return the groupId
     */
    public long getGroupId() {
        return groupId;
    }

    /**
     * @param groupId the groupId to set
     */
    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * @return the operatorId
     */
    public long getOperatorId() {
        return operatorId;
    }

    /**
     * @param operatorId the operatorId to set
     */
    public void setOperatorId(long operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * @return the targetId
     */
    public long getTargetId() {
        return targetId;
    }

    /**
     * @param targetId the targetId to set
     */
    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }

    /**
     * @return the messageType
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the subType
     */
    public SubType getSubType() {
        return subType;
    }

    /**
     * @param subType the subType to set
     */
    public void setSubType(SubType subType) {
        this.subType = subType;
    }

    /**
     * @return the metaEventType
     */
    public MetaEventType getMetaEventType() {
        return metaEventType;
    }

    /**
     * @param metaEventType the metaEventType to set
     */
    public void setMetaEventType(MetaEventType metaEventType) {
        this.metaEventType = metaEventType;
    }

    /**
     * @return the noticeType
     */
    public NoticeType getNoticeType() {
        return noticeType;
    }

    /**
     * @param noticeType the noticeType to set
     */
    public void setNoticeType(NoticeType noticeType) {
        this.noticeType = noticeType;
    }

    /**
     * @return the honorType
     */
    public HonorType getHonorType() {
        return honorType;
    }

    /**
     * @param honorType the honorType to set
     */
    public void setHonorType(HonorType honorType) {
        this.honorType = honorType;
    }

    /**
     * @return the requestType
     */
    public RequestType getRequestType() {
        return requestType;
    }

    /**
     * @param requestType the requestType to set
     */
    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     * @return the rawMessage
     */
    public String getRawMessage() {
        return rawMessage;
    }

    /**
     * @param rawMessage the rawMessage to set
     */
    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    /**
     * @return the message
     */
    public Object getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(Object message) {
        this.message = message;
    }

    /**
     * @return the anonymous
     */
    public Anonymous getAnonymous() {
        return anonymous;
    }

    /**
     * @param anonymous the anonymous to set
     */
    public void setAnonymous(Anonymous anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * @return the file
     */
    public OnebotFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(OnebotFile file) {
        this.file = file;
    }

    /**
     * @return the duration
     */
    public long getDuration() {
        return duration;
    }

    /**
     * @param duration the duration to set
     */
    public void setDuration(long duration) {
        this.duration = duration;
    }

    /**
     * @return the status
     */
    public Object getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Object status) {
        this.status = status;
    }

    /**
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * @param interval the interval to set
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * @return the sender
     */
    public Sender getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(Sender sender) {
        this.sender = sender;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the font
     */
    public int getFont() {
        return font;
    }

    /**
     * @param font the font to set
     */
    public void setFont(int font) {
        this.font = font;
    }

    /**
     * @return the postMethod
     */
    public int getPostMethod() {
        return postMethod;
    }

    /**
     * @param postMethod the postMethod to set
     */
    public void setPostMethod(int postMethod) {
        this.postMethod = postMethod;
    }

    /**
     * @return the senderId
     */
    public long getSenderId() {
        return senderId;
    }

    /**
     * @param senderId the senderId to set
     */
    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    /**
     * @return the groupCardNew
     */
    public String getGroupCardNew() {
        return groupCardNew;
    }

    /**
     * @param groupCardNew the groupCardNew to set
     */
    public void setGroupCardNew(String groupCardNew) {
        this.groupCardNew = groupCardNew;
    }

    /**
     * @return the groupCardOld
     */
    public String getGroupCardOld() {
        return groupCardOld;
    }

    /**
     * @param groupCardOld the groupCardOld to set
     */
    public void setGroupCardOld(String groupCardOld) {
        this.groupCardOld = groupCardOld;
    }

    /**
     * @return the group
     */
    public boolean isGroup() {
        return group;
    }

    /**
     * @param group the group to set
     */
    public void setGroup(boolean group) {
        this.group = group;
    }

    /**
     * @return the realId
     */
    public long getRealId() {
        return realId;
    }

    /**
     * @param realId the realId to set
     */
    public void setRealId(long realId) {
        this.realId = realId;
    }
    
}
