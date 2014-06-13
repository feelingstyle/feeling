package com.widget;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import com.db.*;
import java.text.*;

public class CalendarItem {

	private long id;
	private long calendarid;
	private String title;
	private String note;
	private String url;
	private String urlpara;
	private String target;
	private int status;
	private String year;
	private String month;
	private String date;
	private String fulldate;
	
	public CalendarItem() {
		// TODO Auto-generated constructor stub
	}
	
	public CalendarItem(long id, long calendarid, String title, String note,
			String url, String urlpara, String target, int status, String year,
			String month, String date, String fulldate) {
		this.id = id;
		this.calendarid = calendarid;
		this.title = title;
		this.note = note;
		this.url = url;
		this.urlpara = urlpara;
		this.target = target;
		this.status = status;
		this.year = year;
		this.month = month;
		this.date = date;
		this.fulldate = fulldate;
	}

	public CalendarItem(Vector<String> v)
	{
		this.id = Long.parseLong(v.elementAt(0));
		this.calendarid = Long.parseLong(v.elementAt(1));
		this.title = v.elementAt(2);
		this.note = v.elementAt(3);
		this.url = v.elementAt(4);
		this.urlpara = v.elementAt(5);
		this.target = v.elementAt(6);
		this.status = Integer.parseInt(v.elementAt(7));
		this.year = v.elementAt(8);
		this.month = v.elementAt(9);
		this.date = v.elementAt(10);
		this.fulldate = v.elementAt(11);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCalendarid() {
		return calendarid;
	}

	public void setCalendarid(long calendarid) {
		this.calendarid = calendarid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrlpara() {
		return urlpara;
	}

	public void setUrlpara(String urlpara) {
		this.urlpara = urlpara;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getFulldate() {
		return fulldate;
	}

	public void setFulldate(String fulldate) {
		this.fulldate = fulldate;
	}

	public static CalendarItem get(long id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,CALENDARID,TITLE,NOTE,LINKURL,LINKPARA,LINKTARGET,STATUS,YEAR,MONTH,CALENDARDATE,FULLDATE " +
						"from SHARE14_OP_CALENDAR_ITEM where ID="+id;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new CalendarItem(v.elementAt(0));
	}
	
	public int add()
	{
		int i = 0;
		DbConn db = new DbConn();
		db.createConn();

		String sql = "insert into SHARE14_OP_CALENDAR_ITEM(ID,CALENDARID,TITLE,NOTE,LINKURL,LINKPARA,LINKTARGET,STATUS,YEAR," +
							"MONTH,CALENDARDATE,FULLDATE) " +
							"values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try{
			if (this.fulldate.length()==10)
				this.fulldate += " 00:00:00";
			
			PreparedStatement ps = db.getConn().prepareStatement(sql);
			ps.setLong(1,this.id);
			ps.setLong(2,this.calendarid);
			ps.setString(3,this.title);
			ps.setString(4, this.note);
			ps.setString(5, this.url);
			ps.setString(6, this.urlpara);
			ps.setString(7, this.target);
			ps.setInt(8, this.status);
			ps.setString(9, this.year);
			ps.setString(10, this.month);
			ps.setString(11, this.date);
			ps.setTimestamp(12,Timestamp.valueOf(this.fulldate));
			i = ps.executeUpdate();
		}catch (Exception e)
		{
			e.printStackTrace();
			db.closeConn();
			return -1;
		}
		
		db.closeConn();
		
		return i;
	}
	
	public int edit()
	{
		int i = 0;
		DbConn db = new DbConn();
		db.createConn();

		String sql = "update SHARE14_OP_CALENDAR_ITEM set CALENDARID=?,TITLE=?,NOTE=?,LINKURL=?,LINKPARA=?,LINKTARGET=?,STATUS=?," +
							"YEAR=?,MONTH=?,CALENDARDATE=?,FULLDATE=? where ID=?";
		try{
			if (this.fulldate.length()==10)
				this.fulldate += " 00:00:00";
			
			PreparedStatement ps = db.getConn().prepareStatement(sql);
			ps.setLong(1,this.calendarid);
			ps.setString(2,this.title);
			ps.setString(3, this.note);
			ps.setString(4, this.url);
			ps.setString(5, this.urlpara);
			ps.setString(6, this.target);
			ps.setInt(7, this.status);
			ps.setString(8, this.year);
			ps.setString(9, this.month);
			ps.setString(10, this.date);
			ps.setTimestamp(11,Timestamp.valueOf(this.fulldate));
			ps.setLong(12,this.id);
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
	
	public static int remove(long id)
	{
		int i = 0;
		DbConn db = new DbConn();
		db.createConn();

		String sql = "delete from SHARE14_OP_CALENDAR_ITEM where ID=?";
		try{
			PreparedStatement ps = db.getConn().prepareStatement(sql);
			ps.setLong(1,id);
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
