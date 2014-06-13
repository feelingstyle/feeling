package com.wechat.cmd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import com.wechat.message.*;
import com.operator.*;

public class Announcement extends Command implements CommandInterface {

	public Announcement() {
		// TODO Auto-generated constructor stub
	}

	public Announcement(int id, int classorder, String classname,
			String paraconfig, int status, String returnclassname) {
		super(id, classorder, classname, paraconfig, status, returnclassname);
		// TODO Auto-generated constructor stub
	}

	public Announcement(Vector<String> v) {
		super(v);
		// TODO Auto-generated constructor stub
	}

	public String call(HashMap<String,String> reqmsg) {
		if (OperatorExt.get("OpenID", reqmsg.get("fromUserName"))==null)
		{
			TextMessage tm = new TextMessage(reqmsg.get("fromUserName"),reqmsg.get("toUserName"),
					Long.toString(System.currentTimeMillis()),"text","你尚未绑定用户，无法使用该功能，请输入200绑定","0");
			return tm.toXML();
		}
		
		ComplexMessage cm = new ComplexMessage();
		cm.setMessageType("news");
		cm.setCreateTime(Long.toString(System.currentTimeMillis()));
		cm.setToUserName(reqmsg.get("fromUserName"));  
		cm.setFromUserName(reqmsg.get("toUserName"));
		cm.setFuncFlag("0");
		cm.setArticleCount(3);
		
		ArrayList<ComplexMessageItem> list = new ArrayList<ComplexMessageItem>();
		ComplexMessageItem item = new ComplexMessageItem();
		item.setTitle("这是一个功能DEMO。所以，不要管你看到了什么，接受他就好了！");
		item.setDescription("测试，没有内容怎么才能让人喜欢。");
		item.setPicUrl("http://tp4.sinaimg.cn/1904972835/180/40046812621/1");
		item.setUrl("http://weibo.com/feelingstyle");
		list.add(item);
		
		item = new ComplexMessageItem();
		item.setTitle("test1");
		item.setDescription("test1");
		item.setPicUrl("http://bcs.duapp.com/yishi-music/111.jpg?sign=MBO:97068c69ccb2ab230a497c59d528dcce:hmzcBYxgI4yUaTd9GvahO1GvE%2BA%3D");
		item.setUrl("http://baike.baidu.com/view/6300265.htm");
		list.add(item);
		item = new ComplexMessageItem();
		item.setTitle("test2");
		item.setDescription("test2");
		item.setPicUrl("http://bcs.duapp.com/yishi-music/111.jpg?sign=MBO:97068c69ccb2ab230a497c59d528dcce:hmzcBYxgI4yUaTd9GvahO1GvE%2BA%3D");
		item.setUrl("http://baike.baidu.com/view/6300265.htm");
		list.add(item);
		
		cm.setArticles(list);
		return cm.toXML();
	}

	@Override
	public String call(HashMap<String,String> reqmsg, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return call(reqmsg);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
