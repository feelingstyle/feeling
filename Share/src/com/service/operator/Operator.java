package com.service.operator;

import java.util.*;

import com.db.DbConn;

public class Operator {

	private long id;
	private String name;
	private String appid;
	private String appsecret;
	private String createdate;
	private String modifydate;
	private String fromip;
	private String note;
	private int status;
	
	public Operator() {
		// TODO Auto-generated constructor stub
	}

	public Operator(long id, String name, String appid, String appsecret,
			String createdate, String modifydate, String fromip, String note,
			int status) {
		this.id = id;
		this.name = name;
		this.appid = appid;
		this.appsecret = appsecret;
		this.createdate = createdate;
		this.modifydate = modifydate;
		this.fromip = fromip;
		this.note = note;
		this.status = status;
	}

	public Operator(Vector<String> v) {		
		this.id = Long.parseLong(v.elementAt(0));
		this.name = v.elementAt(1);
		this.appid = v.elementAt(2);
		this.appsecret = v.elementAt(3);
		this.createdate = v.elementAt(4);
		this.modifydate = v.elementAt(5);
		this.fromip = v.elementAt(6);
		this.note = v.elementAt(7);
		this.status = Integer.parseInt(v.elementAt(8));
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public String getFromip() {
		return fromip;
	}

	public void setFromip(String fromip) {
		this.fromip = fromip;
	}

	public static Operator get(String name)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,NAME,APPID,APPSECRET,CREATEDATE,MODIFYDATE,FROMIP,NOTE,STATUS " +
				"from SHARE14_SERVICE_OPERATOR where NAME='"+name+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Operator(v.elementAt(0));
	}
	
	public static Operator getByAppID(String appid)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,NAME,APPID,APPSECRET,CREATEDATE,MODIFYDATE,FROMIP,NOTE,STATUS " +
				"from SHARE14_SERVICE_OPERATOR where APPID='"+appid+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Operator(v.elementAt(0));
	}
	
	public int save()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select * from SHARE14_SERVICE_OPERATOR where NAME='"+this.getName()+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		if (v!=null && v.size()>0)
		{
			sql = "update SHARE14_SERVICE_OPERATOR set APPSECRET='"+this.getAppsecret()+"',MODIFYDATE=sysdate,FROMIP='"+this.getFromip()+"'," +
					"NOTE='"+this.getNote()+"',STATUS="+this.getStatus()+" " +
					"where NAME='"+this.getName()+"'";
		}else{
			sql = "insert into SHARE14_SERVICE_OPERATOR(ID,NAME,APPID,APPSECRET,CREATEDATE,MODIFYDATE,FROMIP,NOTE,STATUS) " +
				"values("+System.currentTimeMillis()+",'"+this.getName()+"','"+this.getAppid()+"','"+this.getAppsecret()+"'," +
				"sysdate,sysdate,'"+this.getFromip()+"','"+this.getNote()+"',"+this.getStatus()+")";
		}
		int i = db.updateSql(sql);
		db.closeConn();
		
		return i;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
