package com.widget;

import java.util.*;

import com.db.*;

public class CalendarManager {

	public CalendarManager() {
		// TODO Auto-generated constructor stub
	}

	public static int createCalendar(String loginname,int authfrom,String calendarname,String cssname,int type,int status)
	{
		Calendar c = new Calendar(System.currentTimeMillis(), loginname, authfrom, calendarname, cssname, type, status);
		return c.add();
	}
	
	public static int editCalendar(long id,String loginname,int authfrom,String calendarname,String cssname,int type,int status)
	{
		Calendar c = Calendar.get(id);
		c.setLoginname(loginname);
		c.setAuthfrom(authfrom);
		c.setCalendarname(calendarname);
		c.setCssname(cssname);
		c.setType(type);
		c.setStatus(status);
		return c.edit();
	}
	
	public static int removeCalendar(String loginname,int authfrom,String calendarname)
	{
		Calendar c = Calendar.get(loginname, authfrom, calendarname);
		return c.remove();
	}
	
	public static int editCalendarCss(String loginname,int authfrom,String calendarname,String cssname)
	{
		Calendar c = Calendar.get(loginname, authfrom, calendarname);
		c.setCssname(cssname);
		return c.edit();
	}
	
	public static ArrayList<Calendar> getList(String loginname,int authfrom,int status)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LOGINNAME,AUTHFROM,CALENDARNAME,CSSCLASSNAME,TYPE,STATUS " +
						"from SHARE14_OP_CALENDAR where  LOGINNAME='"+loginname+"' " +
						"and AUTHFROM="+authfrom+" and STATUS="+status+" order by TYPE,ID";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<Calendar> l = new ArrayList<Calendar>();
		for (int i=0;i<v.size();i++)
		{
			l.add(new Calendar(v.elementAt(i)));
		}
		
		return l;
	}
	
	public static ArrayList<Calendar> getLinkedList(String loginname,int authfrom,int status)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LOGINNAME,AUTHFROM,CALENDARNAME,CSSCLASSNAME,TYPE,STATUS " +
						"from SHARE14_OP_CALENDAR where STATUS="+status+" and " +
						"ID in (select LINKCALENDARID from SHARE14_OP_CALENDAR_LINK " +
						"where LOGINNAME='"+loginname+"' and AUTHFROM="+authfrom+" and REQUESTSTATUS=1)";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<Calendar> l = new ArrayList<Calendar>();
		for (int i=0;i<v.size();i++)
		{
			l.add(new Calendar(v.elementAt(i)));
		}
		
		return l;
	}
	
	public static void initOPCalendar(String loginname,int authfrom)
	{
		if (Calendar.get(loginname, authfrom, "我的日历")==null)
			createCalendar(loginname,authfrom,"我的日历","calendaritem",1000,1);
		if (Calendar.get(loginname, authfrom, "我的消息")==null)
			createCalendar(loginname,authfrom,"我的消息","calendaritem_orange",2000,1);
		if (Calendar.get(loginname, authfrom, "我的任务")==null)
			createCalendar(loginname,authfrom,"我的任务","calendaritem_green",2001,1);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
