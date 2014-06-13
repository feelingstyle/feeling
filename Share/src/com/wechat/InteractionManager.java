package com.wechat;

import org.json.*;

import java.util.*;

import com.db.*;

public class InteractionManager {

	public InteractionManager() {
		// TODO Auto-generated constructor stub
	}

	public static JSONArray mergePara(String str1,String str2)
	{
		if (str1==null || str1.length()<=0)
		{
			str1 = "[]";
		}
		
		if (str2==null || str2.length()<=0)
		{
			str2 = "[]";
		}
		
		JSONArray ja1 = new JSONArray();
		JSONArray ja2 = new JSONArray();
		JSONArray ja = new JSONArray();
		try{
			ja1 = new JSONArray(str1);
			ja2 = new JSONArray(str2);
			
			HashMap<String,JSONObject> map = new HashMap<String, JSONObject>();
			for (int i=0;i<ja1.length();i++)
			{
				map.put(ja1.getJSONObject(i).getString("key"),ja1.getJSONObject(i));
			}
			for (int i=0;i<ja2.length();i++)
			{
				map.put(ja2.getJSONObject(i).getString("key"),ja2.getJSONObject(i));
			}
			
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext())
			{
				String key = iterator.next();
				ja.put(map.get(key));
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return ja;	
	}
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
