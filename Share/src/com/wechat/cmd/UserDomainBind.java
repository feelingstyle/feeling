package com.wechat.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import com.util.*;
import com.wechat.message.ComplexMessage;
import com.wechat.message.ComplexMessageItem;
import com.wechat.message.RequestTextMessage;

public class UserDomainBind extends Command implements CommandInterface {

	public UserDomainBind() {
		// TODO Auto-generated constructor stub
	}

	public UserDomainBind(int id, int classorder, String classname,
			String paraconfig, int status, String returnclassname) {
		super(id, classorder, classname, paraconfig, status, returnclassname);
		// TODO Auto-generated constructor stub
	}

	public UserDomainBind(Vector<String> v) {
		super(v);
		// TODO Auto-generated constructor stub
	}

	public String call(HashMap<String,String> reqmsg) {
		String bindip = CommonDataManager.getValue("domainbind", "BASIC", "ip");
		if (bindip==null)
			bindip = "";
		
		ComplexMessage cm = new ComplexMessage();
		cm.setMessageType("news");
		cm.setCreateTime(Long.toString(System.currentTimeMillis()));
		cm.setToUserName(reqmsg.get("fromUserName"));  
		cm.setFromUserName(reqmsg.get("toUserName"));
		cm.setFuncFlag("0");
		cm.setArticleCount(1);
		
		ArrayList<ComplexMessageItem> list = new ArrayList<ComplexMessageItem>();
		ComplexMessageItem item = new ComplexMessageItem();
		item.setTitle("Share信息分享系统微信用户绑定");
		item.setDescription("为了确定你有权使用Share订阅号的一些服务，我们在这里为你提供域用户鉴权及绑定的功能。绑定成功后，你将可以通过微信订阅号入口享受到随身的信息获取和知识分享！");
		item.setPicUrl(bindip+"/Share/pic/title-whitecolar.png");
		item.setUrl(bindip+"/Share/wechat/domainbind.jsp?openid="+reqmsg.get("fromUserName"));
		list.add(item);
		
		cm.setArticles(list);
		return cm.toXML();
	}

	public String call(HashMap<String,String> reqmsg, HashMap<String, String> map) {
		return call(reqmsg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
