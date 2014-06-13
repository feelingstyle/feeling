package com.widget;

import java.util.*;

import com.db.*;

public class CalendarLink {

	private long id;
	private long linkcalendarid;
	private String loginname;
	private int authfrom;
	private int readable;
	private int editable;
	private int removable;
	private int requeststatus;
	
	public CalendarLink() {
		// TODO Auto-generated constructor stub
	}

	public CalendarLink(long id, long linkcalendarid, String loginname,
			int authfrom, int readable, int editable, int removable,
			int requeststatus) {
		this.id = id;
		this.linkcalendarid = linkcalendarid;
		this.loginname = loginname;
		this.authfrom = authfrom;
		this.readable = readable;
		this.editable = editable;
		this.removable = removable;
		this.requeststatus = requeststatus;
	}

	public CalendarLink(Vector<String> v) {
		this.id = Long.parseLong(v.elementAt(0));
		this.linkcalendarid = Long.parseLong(v.elementAt(1));
		this.loginname = v.elementAt(2);
		this.authfrom = Integer.parseInt(v.elementAt(3));
		this.readable = Integer.parseInt(v.elementAt(4));
		this.editable = Integer.parseInt(v.elementAt(5));
		this.removable = Integer.parseInt(v.elementAt(6));
		this.requeststatus = Integer.parseInt(v.elementAt(7));
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getLinkcalendarid() {
		return linkcalendarid;
	}

	public void setLinkcalendarid(long linkcalendarid) {
		this.linkcalendarid = linkcalendarid;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public int getAuthfrom() {
		return authfrom;
	}

	public void setAuthfrom(int authfrom) {
		this.authfrom = authfrom;
	}

	public int getReadable() {
		return readable;
	}

	public void setReadable(int readable) {
		this.readable = readable;
	}

	public int getEditable() {
		return editable;
	}

	public void setEditable(int editable) {
		this.editable = editable;
	}

	public int getRemovable() {
		return removable;
	}

	public void setRemovable(int removable) {
		this.removable = removable;
	}

	public int getRequeststatus() {
		return requeststatus;
	}

	public void setRequeststatus(int requeststatus) {
		this.requeststatus = requeststatus;
	}

	public static CalendarLink get(long id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LINKCALENDARID,LOGINNAME,AUTHFROM,READ,EDIT,REMOVE,REQUESTSTATUS " +
						"from SHARE14_OP_CALENDAR_LINK where ID="+id;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new CalendarLink(v.elementAt(0));
	} 
	
	public static CalendarLink get(String loginname,int authfrom,long linkcalendarid)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LINKCALENDARID,LOGINNAME,AUTHFROM,READ,EDIT,REMOVE,REQUESTSTATUS " +
						"from SHARE14_OP_CALENDAR_LINK where LOGINNAME='"+loginname+"' " +
						"and AUTHFROM="+authfrom+" and LINKCALENDARID="+linkcalendarid;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new CalendarLink(v.elementAt(0));
	}
	
	public int add()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "insert into SHARE14_OP_CALENDAR_LINK(ID,LINKCALENDARID,LOGINNAME,AUTHFROM,READ,EDIT,REMOVE,REQUESTSTATUS) " +
						"values("+this.id+","+this.linkcalendarid+",'"+this.loginname+"',"+this.authfrom+"," +
						""+this.readable+","+this.editable+","+this.removable+","+this.requeststatus+")";
		int i = db.updateSql(sql);
		db.closeConn();
		
		return i;
	}
	
	public int edit()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "update SHARE14_OP_CALENDAR_LINK set LINKCALENDARID="+this.linkcalendarid+",LOGINNAME='"+this.loginname+"'" +
						",AUTHFROM="+this.authfrom+",READ="+this.readable+",EDIT="+this.editable+",REMOVE="+this.removable+"" +
						",REQUESTSTATUS="+this.requeststatus+" " +
						"where ID="+this.id;
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
