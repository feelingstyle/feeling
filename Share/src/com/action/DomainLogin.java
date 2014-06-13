package com.action;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import org.json.*;

import com.operator.*;
import com.http.*;
import com.service.oauth.*;

public class DomainLogin {

	public DomainLogin() {
		// TODO Auto-generated constructor stub
	}

	public static String authorize(HttpSession session,String name,String password,String sessionname,String appid,String scope)
	{
		Operator op = login(session, name, password, sessionname);
		if (op==null)
			return null;
		
		return CodeManager.createAuthcode(appid,op.getId(),scope);
	}
	
	public static Operator login(HttpSession session,String name,String password,String sessionname)
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
			}catch (JSONException jsone)
			{
				jsone.printStackTrace();
				return null;
			}
			
			op = Operator.get(name, 1);
			session.setAttribute(sessionname, op);
		}else{
			System.out.println("input null");
			return null;
		}
		
		return op;
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
		if (args==null || args.length!=2)
		{
			System.out.println("RUN with TWO args");
			return;
		}
		
		Operator op = DomainLogin.login(new HttpSession() {
			@Override
			public void setMaxInactiveInterval(int arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void setAttribute(String arg0, Object arg1) {
				// TODO Auto-generated method stub
			}
			@Override
			public void removeValue(String arg0) {
				// TODO Auto-generated method stub
			}		
			@Override
			public void removeAttribute(String arg0) {
				// TODO Auto-generated method stub			
			}	
			@Override
			public void putValue(String arg0, Object arg1) {
				// TODO Auto-generated method stub			
			}		
			@Override
			public boolean isNew() {
				// TODO Auto-generated method stub
				return false;
			}			
			@Override
			public void invalidate() {
				// TODO Auto-generated method stub			
			}			
			@Override
			public String[] getValueNames() {
				// TODO Auto-generated method stub
				return null;
			}		
			@Override
			public Object getValue(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}			
			@Override
			public HttpSessionContext getSessionContext() {
				// TODO Auto-generated method stub
				return null;
			}			
			@Override
			public ServletContext getServletContext() {
				// TODO Auto-generated method stub
				return null;
			}			
			@Override
			public int getMaxInactiveInterval() {
				// TODO Auto-generated method stub
				return 0;
			}			
			@Override
			public long getLastAccessedTime() {
				// TODO Auto-generated method stub
				return 0;
			}			
			@Override
			public String getId() {
				// TODO Auto-generated method stub
				return null;
			}		
			@Override
			public long getCreationTime() {
				// TODO Auto-generated method stub
				return 0;
			}			
			@Override
			public Enumeration<String> getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}		
			@Override
			public Object getAttribute(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		}, args[0], args[1], "user");
		
		if (op!=null)
			System.out.println(op.toString());
	}

}
