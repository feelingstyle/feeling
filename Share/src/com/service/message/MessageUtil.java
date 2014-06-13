package com.service.message;

import java.io.*;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.*;

public class MessageUtil {

	public MessageUtil() {
		// TODO Auto-generated constructor stub
	}
	public static HashMap<String,String> toMap(String xml)
	{
		HashMap<String,String> m = new HashMap<String, String>();
		System.out.println("xml="+xml);
		if (xml==null || xml.equals(""))
		{
			return null;
		}
		
		try {
			Document document = DocumentHelper.parseText(xml);
			if (!document.hasContent())
				return null;
			Element roote = document.getRootElement();
			Iterator<Element> itere = roote.elementIterator();
			while (itere.hasNext())
			{
				Element e = itere.next();
				m.put(e.getName(), e.getTextTrim());
			}

		}catch (Exception e)
		{
			System.out.println("xml parse error!");
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
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
