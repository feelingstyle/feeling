package com.wechat.menu;

import java.util.*;

import com.db.*;

public class MenuItemManager {

	public MenuItemManager() {
		// TODO Auto-generated constructor stub
	}

	public static HashMap<String,MenuItem> getMenuItemMap()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,PARENTID,ISPARENT,CMD,CMDPARA,MENUORDER,STATUS from SHARE14_WECHAT_MENU " +
				"where STATUS=1 order by ID";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		HashMap<String,MenuItem> map = new HashMap<String, MenuItem>();
		for (int i=0;i<v.size();i++)
		{
			map.put(v.elementAt(i).elementAt(0), new MenuItem(v.elementAt(i)));
		}
		
		return map;
	}
	
	public static ArrayList<MenuItem> getSubMenuItemList(int parentid)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,PARENTID,ISPARENT,CMD,CMDPARA,MENUORDER,STATUS from SHARE14_WECHAT_MENU " +
				"where STATUS=1 and PARENTID="+parentid+" order by ID";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		ArrayList<MenuItem> list = new ArrayList<MenuItem>();
		for (int i=0;i<v.size();i++)
		{
			list.add(new MenuItem(v.elementAt(i)));
		}
		
		return list;
	}
	
	public static String showSubMenuItemListStr(int parentid)
	{
		MenuItem mi = MenuItem.get(parentid);
		if (mi==null)
			return null;
		
		ArrayList<MenuItem> list = getSubMenuItemList(parentid);
		if (list==null)
			return null;
		String s = "> "+mi.getId()+" "+mi.getCmd()+"\r";
		for (int i=0;i<list.size();i++)
		{
			MenuItem tmpmi = list.get(i);
			if (tmpmi.getIsparent()==1)
			{
				s += "    + "+ tmpmi.getId()+ " " + tmpmi.getCmd() + "\r";
			}else{
				s += "    ¡ó "+ tmpmi.getId()+ " " + tmpmi.getCmd() + "\r";
			}
			 
		}
		
		return s;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
