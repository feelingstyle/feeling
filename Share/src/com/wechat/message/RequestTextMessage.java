package com.wechat.message;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.xml.*;

import java.lang.reflect.Method;
import java.util.*;

public class RequestTextMessage extends Message {

	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String messageType;
	private String content;
	private String msgId;
	
	public RequestTextMessage() {
		// TODO Auto-generated constructor stub
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public static RequestTextMessage get(String xml)
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("xml", RequestTextMessage.class);
		xstream.aliasField("ToUserName", RequestTextMessage.class, "toUserName");
		xstream.aliasField("FromUserName", RequestTextMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", RequestTextMessage.class, "createTime");
		xstream.aliasField("MsgType", RequestTextMessage.class, "messageType");
		xstream.aliasField("Content", RequestTextMessage.class, "content");
		xstream.aliasField("MsgId", RequestTextMessage.class, "msgId");
		RequestTextMessage requestTextMessage = (RequestTextMessage)xstream.fromXML(xml); 
		return requestTextMessage;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
