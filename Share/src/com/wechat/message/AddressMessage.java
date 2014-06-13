package com.wechat.message;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class AddressMessage {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String messageType;
	private String event;
	private String Latitude;
	private String Longitude;
	private String Precision;

	public AddressMessage() {
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

	public String getLatitude() {
		return Latitude;
	}

	public void setLatitude(String Latitude) {
		this.Latitude = Latitude;
	}

	public String getLongitude() {
		return Longitude;
	}

	public void setLongitude(String Longitude) {
		this.Longitude = Longitude;
	}

	public String getPrecision() {
		return Precision;
	}

	public void setPrecision(String Precision) {
		this.Precision = Precision;
	}


	public static AddressMessage get(String xml)
	{
		XStream xstream = new XStream(new DomDriver());
		xstream.alias("xml", AddressMessage.class);
		xstream.aliasField("ToUserName", AddressMessage.class, "toUserName");
		xstream.aliasField("FromUserName", AddressMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", AddressMessage.class, "createTime");
		xstream.aliasField("MsgType", AddressMessage.class, "messageType");
		xstream.aliasField("Event", AddressMessage.class, "event");
		xstream.aliasField("Latitude", AddressMessage.class, "Latitude");
		xstream.aliasField("Longitude", AddressMessage.class, "Longitude");
		xstream.aliasField("Precision", AddressMessage.class, "Precision");
		AddressMessage addressmessage = (AddressMessage)xstream.fromXML(xml); 
		return addressmessage;
	}
	
	public static void main(String[] args) {
		
	}

}
