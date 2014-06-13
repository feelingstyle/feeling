package com.operator;

import java.util.*;

import com.db.*;

public class OperatorManager {
	public OperatorManager() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<Operator> get(String filter)
	{
	
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LOGINNAME,DISPLAYNAME,AUTHFROM,to_char(MODIFYDATE,'yyyy-mm-dd HH24:mi'),STATUS,MAIL,PHONE,MOBILE,DN " +
						"from SHARE14_OPERATOR where LOGINNAME like '%"+filter+"%' or DISPLAYNAME like '%"+filter+"%' " +
						"or DN like '%"+filter+"%'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<Operator> ops = new ArrayList<Operator>();
		for (int i=0;i<v.size();i++)
		{
			ops.add(new Operator(v.elementAt(i)));
		}
		return ops;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
