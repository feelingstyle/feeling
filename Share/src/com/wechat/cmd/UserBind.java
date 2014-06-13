package com.wechat.cmd;

import com.wechat.message.*;

import java.util.*;

import org.json.*;

import com.operator.*;
import com.http.*;

public class UserBind extends Command implements CommandInterface  {

	public UserBind() {
		// TODO Auto-generated constructor stub
	}

	public String call(HashMap<String,String> reqmsg) {
		return "该指令没有无参调用";
	}

	public String call(HashMap<String,String> reqmsg,HashMap<String,String> attrmap) {
		String resContent = "";
		
		HttpAgent httpagent = 
				new HttpAgent("http://share.scn.com.cn/Woo/AD/getADUser.jsp", "{username:\""+attrmap.get("username")+"\",password:\""+attrmap.get("password")+"\"}");
		String returnstr = httpagent.getResponse();
			
		if (returnstr.equals("-1"))
		{
			System.out.println("URL is null");
			resContent = "URL is null";
			TextMessage tm = new TextMessage(reqmsg.get("fromUserName"),reqmsg.get("toUserName"),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
			return tm.toXML();
		}else if (returnstr.equals("-2"))
		{
			System.out.println("wrong http status");
			resContent = "请求绑定失败(wrong http status)";
			TextMessage tm = new TextMessage(reqmsg.get("fromUserName"),reqmsg.get("toUserName"),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
			return tm.toXML();
		}else if (returnstr.equals("-3"))
		{
			System.out.println("http call exception");
			resContent = "请求绑定失败(http call exception)";
			TextMessage tm = new TextMessage(reqmsg.get("fromUserName"),reqmsg.get("toUserName"),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
			return tm.toXML();
		} 
		
		try{
			JSONObject joOP = new JSONObject(returnstr);
			String status = joOP.getString("status");
			if (!status.equals("1"))
			{
				resContent = "域用户认证失败("+joOP.getString("statusnote")+")";
				TextMessage tm = new TextMessage(reqmsg.get("fromUserName"),reqmsg.get("toUserName"),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
				return tm.toXML();
			}
			
			Operator op = new Operator();
			op.setLoginname(attrmap.get("username"));
			op.setDisplayName(joOP.getString("displayName"));
			op.setMail(joOP.getString("mail"));
			op.setAuthfrom(1);
			op.setDn(transformDN(joOP.getString("distinguishedName")));
			op.saveNew();
			
			op = Operator.get(attrmap.get("username"), 1);
			if (op!=null)
			{
				OperatorExt.remove(op.getId(), "OpenID");
				OperatorExt opext = new OperatorExt(op.getId(), "OpenID", reqmsg.get("fromUserName"), "微信用户唯一标识", 1);
				int addresult = opext.add();
				if (addresult>0)
				{
					resContent += "绑定成功！\r\n"+
							"微信标识:"+reqmsg.get("fromUserName")+"\r"+
							"域用户:"+op.toString();
				}else{
					resContent = "生成微信绑定关系失败";
				}
			}else{
				resContent = "捕获域登录用户失败";
			}
		}catch (JSONException jsone)
		{
			jsone.printStackTrace();
			return "请求绑定失败(域用户解析异常)";
		}
		
		TextMessage tm = new TextMessage(reqmsg.get("fromUserName"),reqmsg.get("toUserName"),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
		return tm.toXML();
	}

	public static String transformDN(String s)
	{
		if (s==null || s.length()<=0)
			return "";
		
		s = s.replaceAll(",DC=scn,DC=com,DC=cn", "");
		String[] strs = s.split(",");
		
		JSONArray ja = new JSONArray();
		try{
			for (int i=strs.length-1;i>=0;i--)
			{
				String tmps = strs[i];
				JSONObject tmpjo = new JSONObject();
				int tmpindex = tmps.indexOf("=");
				if (tmpindex>=0)
				{
					tmpjo.put("nodetype", tmps.substring(0,tmpindex));
					tmpjo.put("name", tmps.substring(tmpindex+1));
				}else{
					tmpjo.put("nodetype", "unknown");
					tmpjo.put("name", tmps);
				}
				ja.put(tmpjo);
			}
		}catch (JSONException jsone)
		{
			jsone.printStackTrace();
		}
		return ja.toString();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
