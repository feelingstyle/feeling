package com.widget;

import java.util.ArrayList;
import java.util.Vector;
import org.json.*;

import com.db.DbConn;

public class CalendarItemManager {

	public CalendarItemManager() {
		// TODO Auto-generated constructor stub
	}

	public static long createCalendarItem(long calendarid,String title,String note,String url,String urlpara,String target,int status,
			String year,String month,String date,String fulldate)
	{
		long id = System.currentTimeMillis();
		CalendarItem ci = new CalendarItem(id,calendarid, title, note, url, urlpara, target, status,
				year,month,date,fulldate);
		
		if (ci.add()>0)
			return id;
		return 0;
	}
	
	public static int editCalendarItem(long id,long calendarid,String title,String note,String url,String urlpara,String target,int status,
			String year,String month,String date,String fulldate)
	{
		CalendarItem ci = CalendarItem.get(id);
		if (calendarid!=0)
		{
			ci.setCalendarid(calendarid);
		}
		if (title!=null)
		{
			ci.setTitle(title);
		}
		if (note!=null)
		{
			ci.setNote(note);
		}
		if (url!=null)
		{
			ci.setUrl(url);
		}
		if (urlpara!=null)
		{
			ci.setUrlpara(urlpara);
		}
		if (target!=null)
		{
			ci.setTarget(target);
		}
		if (status!=0)
		{
			ci.setStatus(status);
		}
		if (year!=null)
		{
			ci.setYear(year);
		}
		if (month!=null)
		{
			ci.setMonth(month);
		}
		if (date!=null)
		{
			ci.setDate(date);
		}
		if (fulldate!=null)
		{
			ci.setFulldate(fulldate);
		}

		return ci.edit();
	}
	
	public static ArrayList<CalendarItem> getList(String calendaridlist,String year,String month)
	{
		String condition = "";
		
		try{
			JSONArray ja = new JSONArray(calendaridlist);
		
			for (int i=0;i<ja.length();i++)
			{
				if (i==0)
				{
					condition += ja.getString(i);
				}else{
					condition += "," + ja.getString(i);
				}
			}
		}catch (JSONException jsone)
		{
			jsone.printStackTrace();
			return null;
		}
		
		if (condition.equals(""))
			return null;
		
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,CALENDARID,TITLE,NOTE,LINKURL,LINKPARA,LINKTARGET,STATUS,YEAR,MONTH,CALENDARDATE,FULLDATE " +
						"from SHARE14_OP_CALENDAR_ITEM where YEAR='"+year+"' and MONTH='"+month+"' and CALENDARID in ("+condition+")";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<CalendarItem> l = new ArrayList<CalendarItem>();
		for (int i=0;i<v.size();i++)
		{
			l.add(new CalendarItem(v.elementAt(i)));
		}
		
		return l;
	}
	
	public static ArrayList<CalendarItem> getList(long calendarid,String year,String month)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,CALENDARID,TITLE,NOTE,LINKURL,LINKPARA,LINKTARGET,STATUS,YEAR,MONTH,CALENDARDATE,FULLDATE " +
						"from SHARE14_OP_CALENDAR_ITEM where YEAR='"+year+"' and MONTH='"+month+"' and CALENDARID="+calendarid+"";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<CalendarItem> l = new ArrayList<CalendarItem>();
		for (int i=0;i<v.size();i++)
		{
			l.add(new CalendarItem(v.elementAt(i)));
		}
		
		return l;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
