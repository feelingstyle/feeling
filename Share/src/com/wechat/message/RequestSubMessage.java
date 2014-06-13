package com.wechat.message;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class RequestSubMessage extends Message{

	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String messageType;
	private String event;
	
	public RequestSubMessage() {
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

	
	
	public static RequestSubMessage get(String xml)
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("xml", RequestSubMessage.class);
		xstream.aliasField("ToUserName", RequestSubMessage.class, "toUserName");
		xstream.aliasField("FromUserName", RequestSubMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", RequestSubMessage.class, "createTime");
		xstream.aliasField("MsgType", RequestSubMessage.class, "messageType");
		xstream.aliasField("Event", RequestSubMessage.class, "event");
		RequestSubMessage requestSubMessage = (RequestSubMessage)xstream.fromXML(xml); 
		return requestSubMessage;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
