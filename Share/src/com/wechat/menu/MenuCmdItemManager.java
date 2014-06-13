package com.wechat.menu;

import java.util.*;

import com.db.*;

public class MenuCmdItemManager {

	public MenuCmdItemManager() {
		// TODO Auto-generated constructor stub
	}

	public static ArrayList<MenuCmdItem> get(int id)
	{
		ArrayList<MenuCmdItem> list = new ArrayList<MenuCmdItem>();
		
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,CLASSORDER,CLASSNAME,PARACONFIG,STATUS,RETURNMESSAGECLASSNAME from SHARE14_WECHAT_MENU_CMD " +
				"where ID="+id+" and STATUS=1 order by CLASSORDER";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		for (int i=0;i<v.size();i++)
		{
			list.add(new MenuCmdItem(v.elementAt(i)));
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
