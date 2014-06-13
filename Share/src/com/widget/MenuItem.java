package com.widget;

import java.util.*;

import com.db.*;

public class MenuItem {

	private int id;
	private int parentid;
	private String title;
	private String url;
	private String urlpara;
	private int menuorder;
	private int status;
	private int authfrom;
	
	public MenuItem() {
		// TODO Auto-generated constructor stub
	}

	public MenuItem(int id, int parentid, String title, String url,
			String urlpara, int menuorder, int status, int authfrom) {
		this.id = id;
		this.parentid = parentid;
		this.title = title;
		this.url = url;
		this.urlpara = urlpara;
		this.menuorder = menuorder;
		this.status = status;
		this.authfrom = authfrom;
	}

	public MenuItem(Vector<String> v)
	{
		this.id = Integer.parseInt(v.elementAt(0));
		this.parentid = Integer.parseInt(v.elementAt(1));
		this.title = v.elementAt(2);
		this.url = v.elementAt(3);
		this.urlpara = v.elementAt(4);
		this.menuorder = Integer.parseInt(v.elementAt(5));
		this.status = Integer.parseInt(v.elementAt(6));
		this.authfrom = Integer.parseInt(v.elementAt(7));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public int getMenuorder() {
		return menuorder;
	}

	public void setMenuorder(int menuorder) {
		this.menuorder = menuorder;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getAuthfrom() {
		return authfrom;
	}

	public void setAuthfrom(int authfrom) {
		this.authfrom = authfrom;
	}

	public static MenuItem get(int id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,PARENTID,TITLE,URL,URLPARA,MENUORDER,STATUS,AUTHFROM " +
						"from SHARE14_MENU where ID="+id+" and STATUS=1";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new MenuItem(v.elementAt(0));
	}
	
	public static ArrayList<MenuItem> getList(int parentid,int authfrom)
	{
		ArrayList<MenuItem> milist = new ArrayList<MenuItem>();
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,PARENTID,TITLE,URL,URLPARA,MENUORDER,STATUS,AUTHFROM " +
						"from SHARE14_MENU where PARENTID="+parentid+" and STATUS=1 order by MENUORDER";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		for (int i=0;i<v.size();i++)
		{
			MenuItem mi = new MenuItem(v.elementAt(i));
			milist.add(mi);
		}
		
		return milist;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
