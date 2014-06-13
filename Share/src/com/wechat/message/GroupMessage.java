package com.wechat.message;

import java.util.HashMap;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class GroupMessage {

	private int group_id;
	private String msgtype;
	private String content;

	public GroupMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public int getGroupid() {
		return group_id;
	}

	public void setGroupid(int group_id) {
		this.group_id = group_id;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JSONObject toJson() throws JSONException
	{
		HashMap<String,Object> map=new HashMap<String,Object>();
		
		HashMap<String,Object> m1=new HashMap<String,Object>();
		m1.put("group_id",this.group_id);
		map.put("filter",m1);
		
		String contype=null;
		if(this.msgtype.equals("text"))
			contype="content";
		else 
			contype="media_id";		
		HashMap<String,Object> m2=new HashMap<String,Object>();
		m2.put(contype, this.content);		
		map.put(this.msgtype,m2);
		map.put("msgtype",this.msgtype);
		
		JSONObject json = JSONObject.fromObject(map); 
		return json;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
