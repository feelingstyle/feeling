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
		item.setTitle("Share��Ϣ����ϵͳ΢���û���");
		item.setDescription("Ϊ��ȷ������Ȩʹ��Share���ĺŵ�һЩ��������������Ϊ���ṩ���û���Ȩ���󶨵Ĺ��ܡ��󶨳ɹ����㽫����ͨ��΢�Ŷ��ĺ�������ܵ��������Ϣ��ȡ��֪ʶ����");
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
