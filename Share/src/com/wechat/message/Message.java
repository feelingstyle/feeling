package com.wechat.message;

import java.util.HashMap;
import java.lang.reflect.*;

import net.sf.json.JSONArray;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class Message {

	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String messageType;
	
	public Message() {
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

	public HashMap<String,String> toMap()
	{
		HashMap<String,String> map = new HashMap<String, String>();
		Method[] ms = this.getClass().getDeclaredMethods();
		try{
			for(int i =0 ;i<ms.length;i++){
	            if(ms[i].getName().startsWith("get") && ms[i].getName().length()>3){
	                Object object = ms[i].invoke(this,null);
	                map.put(ms[i].getName().substring(3, 4).toLowerCase()+ms[i].getName().substring(4), (String)object);
	            }
	         }
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return map;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Message msg = new Message();
		msg.setCreateTime("createtime");
		msg.setFromUserName("from");
		msg.setMessageType("msgtype");
		msg.setToUserName("to");
		msg.toMap();
		System.out.println(JSONArray.fromObject(msg));
	}

}
