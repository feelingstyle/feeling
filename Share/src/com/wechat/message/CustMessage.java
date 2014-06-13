package com.wechat.message;

import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class CustMessage {
	private String touser;
	private String msgtype;
	private String content;
	
	public CustMessage() {
		// TODO Auto-generated constructor stub
	}

	public String gettouser() {
		return touser;
	}

	public void settouser(String touser) {
		this.touser = touser;
	}

	public String getmsgtype() {
		return msgtype;
	}

	public void setmsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getcontent() {
		return content;
	}

	public void setcontent(String content) {
		this.content = content;
	}



	public JSONObject toJson() throws JSONException
	{
		HashMap<String,Object> m=new HashMap<String,Object>();
		m.put("content",this.content);

		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("touser",this.touser);
		map.put("msgtype",this.msgtype);				
		map.put(this.msgtype,m);
		JSONObject json = JSONObject.fromObject(map); 
		return json;
	}
	
	/**
	 * @param args
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws JSONException {
		// TODO Auto-generated method stub
		CustMessage msg = new CustMessage();
		msg.settouser("OPENID");
		msg.setmsgtype("text");
		msg.setcontent("很高兴为您服务");
		System.out.println(msg.toJson().toString());
	}

}
