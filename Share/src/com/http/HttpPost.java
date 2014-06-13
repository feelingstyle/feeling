package com.http;

import java.io.*;
import java.util.*;

import org.json.*;
import org.apache.commons.httpclient.cookie.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpPost {

	private String url;
	private String contenttype;
	private String charset;
	private String str;
	
	public HttpPost() {
		// TODO Auto-generated constructor stub
		this.contenttype = "text/html";
		this.charset = "utf-8";
	}

	public HttpPost(String url, String contenttype, String charset, String str) {
		super();
		this.url = url;
		this.contenttype = contenttype;
		this.charset = charset;
		this.str = str;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getContenttype() {
		return contenttype;
	}

	public void setContenttype(String contenttype) {
		this.contenttype = contenttype;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getResponse()
	{
		if (url==null || url.length()<=0)
		{
			return "-1";
		}
		
		String sResTxt = "";
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod();
		pm.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		pm.getParams().setSoTimeout(10*1000);
		pm.setPath(url);
		BufferedReader br = null;
		
		try{
			pm.setRequestEntity(new StringRequestEntity(str,this.contenttype,this.charset));
			
			int iStatus = hc.executeMethod(pm);

			if (iStatus >= 200 && iStatus <300)
			{
				br = new BufferedReader(new InputStreamReader(pm.getResponseBodyAsStream(),"UTF-8"));
		        String readLine;
		        while(((readLine = br.readLine()) != null)) {
		        	sResTxt += readLine;
		        }

			}else{
				return "-2";
			}
			pm.releaseConnection();
		}catch (Exception e)
		{
			e.printStackTrace();
			return "-3";
		}finally {
			pm.releaseConnection();
		    if(br != null) try { br.close(); } catch (Exception fe) {fe.printStackTrace();return "-3";}
		}
		
		return sResTxt;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HttpPost httppost = new HttpPost();
		httppost.setContenttype("text/xml");
		httppost.setUrl("http://211.167.96.40/Share/WECHAT");
		String str = "<xml><ToUserName><![CDATA[gh_1db9c806b1d1]]></ToUserName>"+
							"<FromUserName><![CDATA[osu2Mt5aOuF-TqxnWgXY75kRX37g]]></FromUserName>"+
							"<CreateTime>1396251502</CreateTime>"+
							"<MsgType><![CDATA[text]]></MsgType>"+
							"<Content><![CDATA[200]]></Content>"+
							"<MsgId>5996854538281379599</MsgId>"+
							"</xml>";
		httppost.setStr(str);
		String res = httppost.getResponse();
		System.out.println(res);
	}

}
