package com.wechat.message;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RequestClickEventMessage extends Message{

	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String messageType;
	private String event;
	private String eventKey;
	
	public RequestClickEventMessage() {
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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}
	
	public static RequestClickEventMessage get(String xml)
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("xml", RequestClickEventMessage.class);
		xstream.aliasField("ToUserName", RequestClickEventMessage.class, "toUserName");
		xstream.aliasField("FromUserName", RequestClickEventMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", RequestClickEventMessage.class, "createTime");
		xstream.aliasField("MsgType", RequestClickEventMessage.class, "messageType");
		xstream.aliasField("Event", RequestClickEventMessage.class, "event");
		xstream.aliasField("EventKey", RequestClickEventMessage.class, "eventKey");
		RequestClickEventMessage requestClickEventMessage = (RequestClickEventMessage)xstream.fromXML(xml); 
		return requestClickEventMessage;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
