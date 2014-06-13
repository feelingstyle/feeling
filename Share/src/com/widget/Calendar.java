package com.widget;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import com.db.*;

public class Calendar {

	private long id;
	private String loginname;
	private int authfrom;
	private String calendarname;
	private String cssname;
	private int type;
	private int status;
	
	public Calendar() {
		// TODO Auto-generated constructor stub
	}
	
	public Calendar(long id, String loginname, int authfrom,
			String calendarname, String cssname, int type, int status) {
		this.id = id;
		this.loginname = loginname;
		this.authfrom = authfrom;
		this.calendarname = calendarname;
		this.cssname = cssname;
		this.type = type;
		this.status = status;
	}

	public Calendar(Vector<String> v)
	{
		this.id = Long.parseLong(v.elementAt(0));
		this.loginname = v.elementAt(1);
		this.authfrom = Integer.parseInt(v.elementAt(2));
		this.calendarname = v.elementAt(3);
		this.cssname = v.elementAt(4);
		this.type = Integer.parseInt(v.elementAt(5));
		this.status = Integer.parseInt(v.elementAt(6));
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getCalendarname() {
		return calendarname;
	}

	public void setCalendarname(String calendarname) {
		this.calendarname = calendarname;
	}

	public String getCssname() {
		return cssname;
	}

	public void setCssname(String cssname) {
		this.cssname = cssname;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static Calendar get(long id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LOGINNAME,AUTHFROM,CALENDARNAME,CSSCLASSNAME,TYPE,STATUS " +
						"from SHARE14_OP_CALENDAR where ID="+id;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Calendar(v.elementAt(0));
	}
	
	public static Calendar get(String loginname,int authfrom,String calendarname)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LOGINNAME,AUTHFROM,CALENDARNAME,CSSCLASSNAME,TYPE,STATUS " +
						"from SHARE14_OP_CALENDAR where LOGINNAME='"+loginname+"' " +
						"and AUTHFROM="+authfrom+" and CALENDARNAME='"+calendarname+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Calendar(v.elementAt(0));
	}
	
	public int edit()
	{
		int i = 0;
		DbConn db = new DbConn();
		db.createConn();

		String sql = "update SHARE14_OP_CALENDAR set LOGINNAME=?,AUTHFROM=?,CALENDARNAME=?,CSSCLASSNAME=?,TYPE=?,STATUS=? " +
						"where ID=?";
		try{
			PreparedStatement ps = db.getConn().prepareStatement(sql);
			ps.setString(1,this.loginname);
			ps.setInt(2,this.authfrom);
			ps.setString(3, this.calendarname);
			ps.setString(4, this.cssname);
			ps.setInt(5, this.type);
			ps.setInt(6, this.status);
			ps.setLong(7,this.id);
			i = ps.executeUpdate();
		}catch (SQLException sqle)
		{
			sqle.printStackTrace();
			db.closeConn();
			return -1;
		}
		
		db.closeConn();
		
		return i;
	}
	
	public int add()
	{
		int i = 0;
		DbConn db = new DbConn();
		db.createConn();
		if (Calendar.get(this.loginname, this.authfrom, this.calendarname)==null)
		{
			String sql = "insert into SHARE14_OP_CALENDAR(ID,LOGINNAME,AUTHFROM,CALENDARNAME,CSSCLASSNAME,TYPE,STATUS) " +
							"values(?,?,?,?,?,?,?)";
			try{
				PreparedStatement ps = db.getConn().prepareStatement(sql);
				ps.setLong(1,this.id);
				ps.setString(2,this.loginname);
				ps.setInt(3,this.authfrom);
				ps.setString(4, this.calendarname);
				ps.setString(5, this.cssname);
				ps.setInt(6, this.type);
				ps.setInt(7, this.status);
				i = ps.executeUpdate();
			}catch (SQLException sqle)
			{
				sqle.printStackTrace();
				db.closeConn();
				return -1;
			}
		}else{
			
		}
		
		db.closeConn();
		
		return i;
	}
	
	public int remove()
	{
		int i = 0;
		DbConn db = new DbConn();
		db.createConn();

		String sql = "delete from SHARE14_OP_CALENDAR where ID=?";
		try{
			PreparedStatement ps = db.getConn().prepareStatement(sql);
			ps.setLong(1,this.id);
			i = ps.executeUpdate();
		}catch (SQLException sqle)
		{
			sqle.printStackTrace();
			db.closeConn();
			return -1;
		}
		
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
