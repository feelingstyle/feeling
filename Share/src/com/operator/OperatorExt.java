package com.operator;

import java.util.Vector;

import com.db.DbConn;

public class OperatorExt {

	private long opid;
	private String key;
	private String value;
	private String memo;
	private int status;
	
	public OperatorExt() {
		// TODO Auto-generated constructor stub
	}

	public OperatorExt(long opid, String key, String value, String memo,
			int status) {
		this.opid = opid;
		this.key = key;
		this.value = value;
		this.memo = memo;
		this.status = status;
	}

	public OperatorExt(Vector<String> v) {
		this.opid = Long.parseLong(v.elementAt(0));
		this.key = v.elementAt(1);
		this.value = v.elementAt(2);
		this.memo = v.elementAt(3);
		this.status = Integer.parseInt(v.elementAt(4));
	}

	public long getOpid() {
		return opid;
	}

	public void setOpid(long opid) {
		this.opid = opid;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static OperatorExt get(String key,String value)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,EXTKEY,EXTVALUE,EXTMEMO,STATUS from SHARE14_OPERATOR_EXT where EXTKEY='"+key+"' and EXTVALUE='"+value+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new OperatorExt(v.elementAt(0));
	}
	
	public static OperatorExt get(long id,String key,String value)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,EXTKEY,EXTVALUE,EXTMEMO,STATUS from SHARE14_OPERATOR_EXT " +
				"where ID="+id+" and EXTKEY='"+key+"' and EXTVALUE='"+value+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new OperatorExt(v.elementAt(0));
	}
	
	public static int remove(long id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "delete from SHARE14_OPERATOR_EXT where ID=" + id;
		int iResult = db.updateSql(sql);
		db.closeConn();
		
		return iResult;
	}
	
	public static int remove(long id,String key)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "delete from SHARE14_OPERATOR_EXT where ID=" + id + " and EXTKEY='"+key+"'";
		int iResult = db.updateSql(sql);
		db.closeConn();
		
		return iResult;
	}
	
	public static int remove(long id,String key,String value)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "delete from SHARE14_OPERATOR_EXT where ID=" + id + " and EXTKEY='"+key+"' and EXTVALUE='"+value+"'";
		int iResult = db.updateSql(sql);
		db.closeConn();
		
		return iResult;
	}
	
	public int add()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "insert into SHARE14_OPERATOR_EXT(ID,EXTKEY,EXTVALUE,EXTMEMO,STATUS) " +
				"values("+this.opid+",'"+this.key+"','"+this.value+"','"+this.memo+"',"+this.status+")";
		int iResult = db.updateSql(sql);
		db.closeConn();
		
		return iResult;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
