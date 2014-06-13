package com.http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.httpclient.protocol.*;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpsAgent {

	private String url;
	private ArrayList<HttpPara> parameters;
	
	public HttpsAgent() {
		// TODO Auto-generated constructor stub
	}

	public HttpsAgent(String url, ArrayList<HttpPara> parameters) {
		this.url = url;
		this.parameters = parameters;
	}
	
	public HttpsAgent(String url, String parameters) {
		this.url = url;
		ArrayList<HttpPara> tmpParas = new ArrayList<HttpPara>();
		
		try{
			JSONObject joParas = new JSONObject(parameters);
			Iterator<String> iterKeys = joParas.keys();
			while (iterKeys.hasNext())
			{
				HttpPara tmpPara = new HttpPara();
				String tmpKey = iterKeys.next();
				tmpPara.setKey(tmpKey);
				tmpPara.setValue(joParas.getString(tmpKey));
				tmpParas.add(tmpPara);
			}
			this.parameters = tmpParas;
		}catch (JSONException jsone)
		{
			this.parameters = null;
		}
	}
		
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<HttpPara> getParameters() {
		return parameters;
	}

	public void setParameters(ArrayList<HttpPara> parameters) {
		this.parameters = parameters;
	}

	public String getResponse()
	{
		if (url==null || url.length()<=0)
		{
			return "-1";
		}
		
		String sResTxt = "";
		Protocol https = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", https);
		
		HttpClient hc = new HttpClient();
		PostMethod pm = new PostMethod();
		pm.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
		pm.getParams().setHttpElementCharset("GBK");
		pm.getParams().setCredentialCharset("GBK");
		pm.getParams().setContentCharset("GBK");

		pm.setPath(url);
		BufferedReader br = null;
		
		if (this.parameters!=null && this.parameters.size()>0)
		{
			for (int i=0;i<this.parameters.size();i++)
			{
				pm.addParameter(this.parameters.get(i).getKey(),this.parameters.get(i).getValue());
			}
		}
		
		try{
			int iStatus = hc.executeMethod(pm);

			if (iStatus >= 200 && iStatus <300)
			{
				br = new BufferedReader(new InputStreamReader(pm.getResponseBodyAsStream()));
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
		// TODO Auto-generated method stub
		ArrayList<HttpPara> paras = new ArrayList<HttpPara>();
		HttpPara para = new HttpPara("grant_type", "client_credential");
		paras.add(para);
		para = new HttpPara("appid", "wx33074026a31fc318");
		paras.add(para);
		para = new HttpPara("secret", "77487e353867bfadfc877c59bf97995b");
		paras.add(para);
		
		HttpsAgent httpsagent = new HttpsAgent("https://api.weixin.qq.com/cgi-bin/token",paras);
		System.out.println(httpsagent.getResponse());
	}

}
