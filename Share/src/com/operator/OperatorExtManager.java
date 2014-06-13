package com.operator;

import java.util.*;

import com.db.*;

public class OperatorExtManager {

	public OperatorExtManager() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<OperatorExt> get(long id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,EXTKEY,EXTVALUE,EXTMEMO,STATUS from SHARE14_OPERATOR_EXT where ID="+id;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<OperatorExt> list = new ArrayList<OperatorExt>();
		for (int i=0;i<v.size();i++)
		{
			list.add(new OperatorExt(v.elementAt(i)));
		}
		
		return list;
	}
	
	public static ArrayList<OperatorExt> get(long id,String key)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,EXTKEY,EXTVALUE,EXTMEMO,STATUS from SHARE14_OPERATOR_EXT where EXTKEY='"+key+"' and ID="+id;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<OperatorExt> list = new ArrayList<OperatorExt>();
		for (int i=0;i<v.size();i++)
		{
			list.add(new OperatorExt(v.elementAt(i)));
		}
		
		return list;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
