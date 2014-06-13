package com.wechat.message;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ComplexMessage extends Message {
	private String toUserName;
	private String fromUserName;
	private String createTime;
	private String messageType;
	private int articleCount;
	private ArrayList<ComplexMessageItem> Articles;
	private String funcFlag;
	
	public ComplexMessage() {
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

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public ArrayList<ComplexMessageItem> getArticles() {
		return Articles;
	}

	public void setArticles(ArrayList<ComplexMessageItem> articles) {
		Articles = articles;
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
		xstream.alias("xml", ComplexMessage.class);
		xstream.aliasField("ToUserName", ComplexMessage.class, "toUserName");
		xstream.aliasField("FromUserName", ComplexMessage.class, "fromUserName");
		xstream.aliasField("CreateTime", ComplexMessage.class, "createTime");
		xstream.aliasField("MsgType", ComplexMessage.class, "messageType");
		xstream.aliasField("Articles", ComplexMessage.class, "Articles");
		xstream.aliasField("ArticleCount", ComplexMessage.class, "articleCount");
		xstream.aliasField("FuncFlag", ComplexMessage.class, "funcFlag");
		xstream.alias("item", ComplexMessageItem.class);
		xstream.aliasField("Title", ComplexMessageItem.class, "title");
		xstream.aliasField("Description", ComplexMessageItem.class, "description");
		xstream.aliasField("PicUrl", ComplexMessageItem.class, "picUrl");
		xstream.aliasField("Url", ComplexMessageItem.class, "url");
		String xml =xstream.toXML(this);
		
		ArrayList<String> attrnames = new ArrayList<String>();
		attrnames.add("ToUserName");
		attrnames.add("FromUserName");
		attrnames.add("MsgType");
		attrnames.add("Title");
		attrnames.add("Description");
		attrnames.add("PicUrl");
		attrnames.add("Url");
		xml = MessageUtil.addCDataSection(xml, attrnames);
		
		return xml;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ComplexMessage cm = new ComplexMessage();
		cm.setMessageType("news");
		cm.setCreateTime(Long.toString(System.currentTimeMillis()));
		cm.setToUserName("openid");  
		cm.setFromUserName("wechat");
		cm.setFuncFlag("0");
		cm.setArticleCount(2);
		
		ArrayList<ComplexMessageItem> list = new ArrayList<ComplexMessageItem>();
		ComplexMessageItem item = new ComplexMessageItem();
		item.setTitle("¿¡½é");
		item.setDescription("<xml>test&&.';\" </xml>");
		item.setPicUrl("http://bcs.duapp.com/yishi-music/111.jpg?sign=MBO:97068c69ccb2ab230a497c59d528dcce:hmzcBYxgI4yUaTd9GvahO1GvE%2BA%3D");
		item.setUrl("http://baike.baidu.com/view/6300265.htm");
		list.add(item);
		
		item = new ComplexMessageItem();
		item.setTitle("ÎåÃ«");
		item.setDescription("ÎåÃ«");
		item.setPicUrl("http://bcs.duapp.com/yishi-music/111.jpg?sign=MBO:97068c69ccb2ab230a497c59d528dcce:hmzcBYxgI4yUaTd9GvahO1GvE%2BA%3D");
		item.setUrl("http://baike.baidu.com/view/6300265.htm");
		list.add(item);
		
		cm.setArticles(list);
		
		System.out.println(cm.toXML());
	}

}
