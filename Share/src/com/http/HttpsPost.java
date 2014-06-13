package com.http;

import java.io.*;
import java.util.*;

import org.json.*;
import org.apache.commons.httpclient.cookie.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpsPost {

	private String url;
	private String contenttype;
	private String charset;
	private String str;
	
	public HttpsPost() {
		this.contenttype = "text/html";
		this.charset = "utf-8";
	}

	public HttpsPost(String url, String contenttype, String charset, String str) {
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
		HttpsPost httpspost = new HttpsPost();
		httpspost.setContenttype("text/xml");

		/*
		httpspost.setUrl("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=bKS4YNSEi80eLVoDNHiz-DlWgWhQD2ms15kns0Z53dlTDdk0l294StIVUQXu9NNlsD5u09Kd0K3xVIUVpP6IkOcr49QvxU08AfD4kuaLEZGxIBapxUSPRF7Eebtu6PyezTp_WTGyq_JYCh_QxOAIOw");
		String str = "{\"button\":[" +
						"{\"name\":\"Service\",\"sub_button\":[" +
							"{\"type\":\"view\",\"name\":\"search\",\"url\":\"http://google.com\"},"+
							"{\"type\":\"view\",\"name\":\"weibo\",\"url\":\"http://weibo.com\"},"+
							"{\"type\":\"click\",\"name\":\"test\",\"key\":\"200\"}"+
							"]}," +
						"{\"type\":\"click\",\"name\":\"Share\",\"key\":\"101\"}," +
						"{\"type\":\"click\",\"name\":\"Count\",\"key\":\"102\"}" +
					"]}";
		*/
		
		httpspost.setUrl("https://211.167.96.40:8443/Share/service/eocOnlineStatus?accesstoken=1234567890");
//		httpspost.setUrl("https://127.0.0.1:8443/Share/service/eocOnlineStatus?accesstoken=1234567890");
		String str = "<xml>" +
						"<requestfrom><![CDATA[AppID]]></requestfrom>"+
						"<requesttime><![CDATA[请求timestamp]]></requesttime>"+
						"<eocmac><![CDATA[eoc mac]]></eocmac>"+
						"<userid><![CDATA[OCN BOSS用户证号]]></userid>"+
					"</xml>";
		System.out.println(str);
		httpspost.setStr(str);
		String res = httpspost.getResponse();
		System.out.println(res);
	}

}
