package com.wechat.message;

import java.util.ArrayList;

import com.thoughtworks.xstream.*;
import com.thoughtworks.xstream.io.xml.*;

public class TextMessage extends Message {

	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String messageType;
	private String content;
	private String funcFlag;
	
	public TextMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public TextMessage(String toUserName, String fromUserName,
			String createTime, String messageType, String content,
			String funcFlag) {
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.createTime = createTime;
		this.messageType = messageType;
		this.content = content;
		this.funcFlag = funcFlag;
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

	public String getFuncFlag() {
		return funcFlag;
	}

	public void setFuncFlag(String funcFlag) {
		this.funcFlag = funcFlag;
	}

	public String toXML()
	{
		XStream xstream = new XStream(new DomDriver()); 
		xstream.alias("xml", TextMessage.class);
		xstream.aliasField("ToUserName", TextMessage.class, "toUserName");
		xstream.aliasField("FromUserName", TextMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", TextMessage.class, "createTime");
		xstream.aliasField("MsgType", TextMessage.class, "messageType");
		xstream.aliasField("Content", TextMessage.class, "content");
		xstream.aliasField("FuncFlag", TextMessage.class, "funcFlag");
		String xml =xstream.toXML(this);
		
		ArrayList<String> attrnames = new ArrayList<String>();
		attrnames.add("ToUserName");
		attrnames.add("FromUserName");
		attrnames.add("MsgType");
		attrnames.add("Content");
		xml = MessageUtil.addCDataSection(xml, attrnames);
		return xml;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
