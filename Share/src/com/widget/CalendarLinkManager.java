package com.widget;

import java.util.*;

import com.db.*;

public class CalendarLinkManager {

	public CalendarLinkManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static int setCalendarLinkPermission(long id,String permissionname,int permission)
	{
		CalendarLink cl = CalendarLink.get(id);
		if (permissionname.equals("read"))
		{
			cl.setReadable(permission);
		}else if (permissionname.equals("edit"))
		{
			cl.setEditable(permission);
		}else if (permissionname.equals("remove"))
		{
			cl.setRemovable(permission);
		}
		
		return cl.edit();
	}
	
	public static long createCalendarLink(long calendarid,String loginname,int authfrom,int readable
			,int editable,int removable,int requeststatus)
	{
		long id = System.currentTimeMillis();
		CalendarLink cl = new CalendarLink(id, calendarid, loginname, authfrom, readable, 
				editable, removable, requeststatus);
		if (cl.add()>0)
		{
			return id;
		}
		
		return 0;
	}
	
	public static ArrayList<CalendarLink> getList(long linkcalendarid)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LINKCALENDARID,LOGINNAME,AUTHFROM,READ,EDIT,REMOVE,REQUESTSTATUS " +
				"from SHARE14_OP_CALENDAR_LINK where LINKCALENDARID="+linkcalendarid+" order by ID desc";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<CalendarLink> list = new ArrayList<CalendarLink>();
		for (int i=0;i<v.size();i++)
		{
			list.add(new CalendarLink(v.elementAt(i)));
		}
		
		return list;
	}

	public static ArrayList<CalendarLink> getList(String loginname,int authfrom,int requeststatus)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LINKCALENDARID,LOGINNAME,AUTHFROM,READ,EDIT,REMOVE,REQUESTSTATUS " +
				"from SHARE14_OP_CALENDAR_LINK where LOGINNAME='"+loginname+"' and AUTHFROM="+authfrom+
				" and REQUESTSTATUS="+requeststatus+" order by ID";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<CalendarLink> list = new ArrayList<CalendarLink>();
		for (int i=0;i<v.size();i++)
		{
			list.add(new CalendarLink(v.elementAt(i)));
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
