package com.util;

import java.util.*;

import com.db.*;

public class CommonData {
	private String name;
	private String dataclass;
	private String key;
	private String value;
	private int status;
	
	public CommonData() {
		// TODO Auto-generated constructor stub
	}
	
	public CommonData(String name, String dataclass, String key, String value,
			int status) {
		this.name = name;
		this.dataclass = dataclass;
		this.key = key;
		this.value = value;
		this.status = status;
	}

	public CommonData(Vector<String> v) {
		this.name = v.elementAt(0);
		this.dataclass = v.elementAt(1);
		this.key = v.elementAt(2);
		this.value = v.elementAt(3);
		this.status = Integer.parseInt(v.elementAt(4));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDataclass() {
		return dataclass;
	}

	public void setDataclass(String dataclass) {
		this.dataclass = dataclass;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static CommonData getMap(String name,String dataclass,String key)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select NAME,CLASS,KEY,VALUE,STATUS from SHARE14_COMMONDATA where NAME='"+name+"' " +
						"and CLASS='"+dataclass+"' and KEY='"+key+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new CommonData(v.elementAt(0));
	}
	
	public int save()
	{
		int flag = 0;
		DbConn db = new DbConn();
		db.createConn();
		CommonData data = getMap(this.name, this.dataclass, this.key);
		String sql = "";
		if (data==null)
		{
			sql = "insert into SHARE14_COMMONDATA(NAME,CLASS,KEY,VALUE,STATUS) " +
					"values('"+this.name+"','"+this.dataclass+"','"+this.key+"','"+this.value+"',1)";
		}else{
			sql = "update SHARE14_COMMONDATA set VALUE='"+this.value+"' " +
					"where NAME='"+this.name+"' and CLASS='"+this.dataclass+"' and KEY='"+this.key+"' and STATUS=1";
		}
		flag = db.updateSql(sql);
		db.closeConn();
		
		return flag;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
