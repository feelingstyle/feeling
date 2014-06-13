package com.action;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.http.HttpAgent;
import com.operator.Operator;
import com.operator.OperatorExt;
import com.wechat.message.RequestTextMessage;
import com.wechat.message.TextMessage;

public class DomainBind {

	public DomainBind() {
		// TODO Auto-generated constructor stub
	}

	public static Operator bind(String name,String password,String openID)
	{
		Operator op = new Operator();
		
		if (name!=null && password!=null && name.length()>0 && password.length()>0)
		{
			HttpAgent httpagent = 
				new HttpAgent("http://share.scn.com.cn/Woo/AD/getADUser.jsp", "{username:\""+name+"\",password:\""+password+"\"}");
			String returnstr = httpagent.getResponse();
			
			if (returnstr.equals("-1"))
			{
				System.out.println("URL is null");
				return null;
			}else if (returnstr.equals("-2"))
			{
				System.out.println("wrong http status");
				return null;
			}else if (returnstr.equals("-3"))
			{
				System.out.println("http call exception");
				return null;
			} 
			
			try{
				JSONObject joOP = new JSONObject(returnstr);
				String status = joOP.getString("status");
				if (!status.equals("1"))
				{
					System.out.println(status+":"+joOP.getString("statusnote"));
					return null;
				}
				
				op.setLoginname(name);
				op.setDisplayName(joOP.getString("displayName"));
				op.setMail(joOP.getString("mail"));
				op.setAuthfrom(1);
				op.setDn(transformDN(joOP.getString("distinguishedName")));
				op.saveNew();
				
				op = Operator.get(name, 1);
				if (op!=null)
				{
					OperatorExt.remove(op.getId(), "OpenID");
					OperatorExt opext = new OperatorExt(op.getId(), "OpenID", openID, "微信用户唯一标识", 1);
					int addresult = opext.add();
					if (addresult<=0)
					{
						return null;
					}
				}else{
					return null;
				}
			}catch (JSONException jsone)
			{
				jsone.printStackTrace();
				return null;
			}
			
		}else{
			System.out.println("input null");
			return null;
		}
		
		return op;
	}
	
	
	public String call(RequestTextMessage reqmsg,HashMap<String,String> attrmap) {
		String resContent = "";
		
		HttpAgent httpagent = 
				new HttpAgent("http://share.scn.com.cn/Woo/AD/getADUser.jsp", "{username:\""+attrmap.get("username")+"\",password:\""+attrmap.get("password")+"\"}");
		String returnstr = httpagent.getResponse();
			
		if (returnstr.equals("-1"))
		{
			System.out.println("URL is null");
			resContent = "URL is null";
			TextMessage tm = new TextMessage(reqmsg.getFromUserName(),reqmsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
			return tm.toXML();
		}else if (returnstr.equals("-2"))
		{
			System.out.println("wrong http status");
			resContent = "请求绑定失败(wrong http status)";
			TextMessage tm = new TextMessage(reqmsg.getFromUserName(),reqmsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
			return tm.toXML();
		}else if (returnstr.equals("-3"))
		{
			System.out.println("http call exception");
			resContent = "请求绑定失败(http call exception)";
			TextMessage tm = new TextMessage(reqmsg.getFromUserName(),reqmsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
			return tm.toXML();
		} 
		
		try{
			JSONObject joOP = new JSONObject(returnstr);
			String status = joOP.getString("status");
			if (!status.equals("1"))
			{
				resContent = "域用户认证失败("+joOP.getString("statusnote")+")";
				TextMessage tm = new TextMessage(reqmsg.getFromUserName(),reqmsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
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
				OperatorExt opext = new OperatorExt(op.getId(), "OpenID", reqmsg.getFromUserName(), "微信用户唯一标识", 1);
				int addresult = opext.add();
				if (addresult>0)
				{
					resContent += "绑定成功！\r\n"+
							"微信标识:"+reqmsg.getFromUserName()+"\r"+
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
		
		TextMessage tm = new TextMessage(reqmsg.getFromUserName(),reqmsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
