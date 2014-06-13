package com.wechat.message;

import java.io.*;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.*;

import com.http.HttpPara;
import com.http.HttpsAgent;

public class MessageUtil {

	public MessageUtil() {
		// TODO Auto-generated constructor stub
	}

	public static HashMap<String,String> toMap(String xml)
	{
		HashMap<String,String> m = new HashMap<String, String>();

		try {
			Document document = DocumentHelper.parseText(xml);
			Element roote = document.getRootElement();
			Iterator<Element> itere = roote.elementIterator();
			while (itere.hasNext())
			{
				Element e = itere.next();
//				System.out.println(e.getName()+":"+e.getTextTrim());
				m.put(e.getName(), e.getTextTrim());
			}

		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return m;
	}
	
	public static String addCDataSection(String xml,ArrayList<String> attrnames)
	{
		if (attrnames==null || attrnames.size()<=0)
			return null;
		
		try {
			Document document = DocumentHelper.parseText(xml);
			Element roote = document.getRootElement();
			
			for (int i=0;i<attrnames.size();i++)
			{
				String attrname = attrnames.get(i);
				List<Element> liste = roote.selectNodes("//"+attrname);
				if (liste!=null && liste.size()>0)
				{
					for (int j=0;j<liste.size();j++)
					{
						Element e = liste.get(j);
						String txt = e.getText();
						txt.replaceAll("&lt;", "<");
						txt.replaceAll("&gt;", ">");
						txt.replaceAll("&amp", "&");
						txt.replaceAll("&apos;", "'");
						txt.replaceAll("&quot;", "\"");
						e.clearContent();
						e.addCDATA(txt);
					}
				}
			}
			
			StringWriter sw = new StringWriter();
			XMLWriter xmlw = new XMLWriter(sw);
			xmlw.write(document);
			xml = sw.getBuffer().toString();
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return xml;
	}
	
	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		String xml = "<xml>" +
				"<ToUserName>osu2Mt5aOuF-TqxnWgXY75kRX37g</ToUserName>" +
				"<FromUserName>gh_1db9c806b1d1</FromUserName>" +
				"<CreateTime>1396491612905</CreateTime>" +
				"<MsgType>news</MsgType>" +
				"<ArticleCount>1</ArticleCount>" +
				"<Articles>" +
					"<item>" +
					"<Title>Share信息分享系统微信用户绑定</Title>" +
					"<Description>&lt;xml&gt;test&amp;&amp;.&apos;;&quot; &lt;/xml&gt;</Description>" +
					"<PicUrl>http://211.167.96.40/Share/pic/title-whitecolar.png</PicUrl>" +
					"<Url>http://211.167.96.40/Share/wechat/domainbind.jsp?openid=osu2Mt5aOuF-TqxnWgXY75kRX37g</Url>" +
					"</item>" +
				"</Articles>" +
				"<FuncFlag>0</FuncFlag>" +
				"</xml>";
		
		ArrayList<String> attrnames = new ArrayList<String>();
		attrnames.add("ToUserName");
		attrnames.add("FromUserName");
		attrnames.add("MsgType");
		attrnames.add("Title");
		attrnames.add("Description");
		attrnames.add("PicUrl");
		attrnames.add("Url");
		xml = addCDataSection(xml, attrnames);
		System.out.println(xml);
	}*/
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<HttpPara> paras = new ArrayList<HttpPara>();
		
		HttpPara para = new HttpPara("access_token", "ElgypMAr40rPUEZGFwuQBi8G-TieHRIjbNH1SSH77wnl5gOjjn25jRKW8Tp30Fkl");
		paras.add(para);
		HttpsAgent httpsagent = new HttpsAgent("https://api.weixin.qq.com/cgi-bin/token",paras);
		String ee = "{\"touser\": \"rubyww\", \"msgtype\": \"text\", \"text\": {\"content\": \"Hello World\"}}";
		
	}

}
