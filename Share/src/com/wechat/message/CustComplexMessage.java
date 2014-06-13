package com.wechat.message;
import java.util.ArrayList;
import java.util.HashMap;


import net.sf.json.JSONObject;

import net.sf.json.JSONException;







public class CustComplexMessage {

	private String touser;
	private String msgtype;
	private ArrayList<ComplexMessageItem> articles;
	
	public CustComplexMessage() {
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

	public ArrayList<ComplexMessageItem> getarticles() {
		return articles;
	}

	public void setarticles(ArrayList<ComplexMessageItem> articles) {
		this.articles = articles;
	}



	public JSONObject toJson() throws JSONException
	{
		HashMap<String,Object> m=new HashMap<String,Object>();
		m.put("articles",this.articles);

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
		ComplexMessageItem cmi1=new ComplexMessageItem();
		cmi1.setTitle("title");
		cmi1.setPicUrl("purl");
		cmi1.setUrl("url");
		cmi1.setDescription("des");
		ComplexMessageItem cmi=new ComplexMessageItem();
		cmi.setTitle("titl");
		cmi.setPicUrl("pur");
		cmi.setUrl("ur");
		cmi.setDescription("de");
		ArrayList list=new ArrayList();
		list.add(cmi);
		list.add(cmi1);
		CustComplexMessage msg = new CustComplexMessage();
		msg.settouser("OPENID");
		msg.setmsgtype("news");
		msg.setarticles(list);
		System.out.println(msg.toJson().toString());
	}

}
