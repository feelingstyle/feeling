package com.wechat;

import java.util.ArrayList;

import com.util.*;
import org.json.*;
import com.http.HttpPara;
import com.http.HttpsAgent;
import com.http.HttpsPost;

public class Util {

	public Util() {
		// TODO Auto-generated constructor stub
	}

	public static String createWechatMenu(String menustr)
	{
		String accesstoken = getAccessToken();
		if (accesstoken==null)
			return "{\"errmsg\":\"获取access token失败\"}";
		
		HttpsPost httpspost = new HttpsPost();
		httpspost.setContenttype("application/json");
		httpspost.setUrl("https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accesstoken);
		httpspost.setStr(menustr);
		String res = httpspost.getResponse();
		return res;
	}
	
	public static String createCustServiceMessage(String msg)
	{
		String accesstoken = getAccessToken();
		if (accesstoken==null)
			return "{\"errmsg\":\"获取access token失败\"}";
		
		HttpsPost httpspost = new HttpsPost();
		httpspost.setContenttype("application/json");
		httpspost.setUrl("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+accesstoken);
		httpspost.setStr(msg);
		String res = httpspost.getResponse();
		return res;
	}
	
	public static String getAccessToken()
	{
		String accesstoken = "";
		String commondata = CommonDataManager.getValue("wechat", "BASIC", "access token expire time");
		if (commondata!=null)
		{
			long expiretime = Long.parseLong(commondata);
			if (expiretime <= System.currentTimeMillis())
			{
				accesstoken = renewAccessToken();
			}else{
				accesstoken = CommonDataManager.getValue("wechat", "BASIC", "access token");
			}
		}else{
			accesstoken = renewAccessToken();
		}
		
		return accesstoken;
	}
	
	public static String renewAccessToken()
	{
		ArrayList<HttpPara> paras = new ArrayList<HttpPara>();
		HttpPara para = new HttpPara("grant_type", "client_credential");
		paras.add(para);
		para = new HttpPara("appid", CommonDataManager.getValue("wechat", "BASIC", "appid"));
		paras.add(para);
		para = new HttpPara("secret", CommonDataManager.getValue("wechat", "BASIC", "secret"));
		paras.add(para);
		
		HttpsAgent httpsagent = new HttpsAgent("https://api.weixin.qq.com/cgi-bin/token",paras);
		String res = httpsagent.getResponse();
		
		String accesstoken = null;
		String expire_in = null;
		try{
			JSONObject jo = new JSONObject(res);
			if (jo.has("access_token") && jo.has("expires_in"))
			{
				accesstoken = jo.getString("access_token");
				expire_in = jo.getString("expires_in");
				
				CommonData cd = new CommonData("wechat", "BASIC", "access token", accesstoken, 1);
				cd.save();
				
				expire_in = Long.toString(Long.parseLong(expire_in)*1000+System.currentTimeMillis());
				cd = new CommonData("wechat", "BASIC", "access token expire time", expire_in, 1);
				cd.save();
			}else{
				return null;
			}
		}catch (JSONException jsone)
		{
			jsone.printStackTrace();
			return null;
		}
		
		return accesstoken;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String msg = "{\"touser\":\"OPENID\"," +
						"\"msgtype\":\"text\"," +
						"\"text\":{" +
							"\"content\":\"Hello World\"" +
						"}}";
		System.out.println(createCustServiceMessage(msg));
	}

}
