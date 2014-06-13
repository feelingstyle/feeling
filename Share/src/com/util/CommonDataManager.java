package com.util;

import java.util.*;

import com.db.*;

public class CommonDataManager {

	public CommonDataManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static ArrayList<CommonData> getList(String name,String dataclass)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select NAME,CLASS,KEY,VALUE,STATUS from SHARE14_COMMONDATA " +
						"where NAME='"+name+"' and CLASS='"+dataclass+"' and STATUS=1 order by KEY";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<CommonData> list = new ArrayList<CommonData>();
		for (int i=0;i<v.size();i++)
		{
			list.add(new CommonData(v.elementAt(i)));
		}
		
		return list;
	}

	public static HashMap<String,CommonData> getMap(String name,String dataclass)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select NAME,CLASS,KEY,VALUE,STATUS from SHARE14_COMMONDATA " +
				"where NAME='"+name+"' and CLASS='"+dataclass+"' and STATUS=1";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		HashMap<String,CommonData> map = new HashMap<String, CommonData>();
		for (int i=0;i<v.size();i++)
		{
			CommonData d = new CommonData(v.elementAt(i));
			map.put(d.getKey(), d);
		}
		
		return map;
	}
	
	public static String getValue(String name,String dataclass,String key)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select VALUE from SHARE14_COMMONDATA " +
				"where NAME='"+name+"' and CLASS='"+dataclass+"' and KEY='"+key+"' and STATUS=1";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return v.elementAt(0).elementAt(0);
	}
	
	public static int saveValue(String name,String dataclass,String key,String value)
	{
		CommonData cdata = new CommonData(name, dataclass, key, value, 1);
		return cdata.save();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
