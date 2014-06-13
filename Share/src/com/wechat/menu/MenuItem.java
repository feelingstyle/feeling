package com.wechat.menu;

import java.util.*;
import com.db.*;

public class MenuItem {
	private int id;
	private int parentid;
	private int isparent;
	private String cmd;
	private String cmdpara;
	private int menuorder;
	private int status;
	
	public MenuItem() {
		// TODO Auto-generated constructor stub
	}
	
	public MenuItem(int id, int parentid, int isparent, String cmd,
			String cmdpara, int menuorder, int status) {
		this.id = id;
		this.parentid = parentid;
		this.isparent = isparent;
		this.cmd = cmd;
		this.cmdpara = cmdpara;
		this.menuorder = menuorder;
		this.status = status;
	}

	public MenuItem(Vector<String> v) {
		this.id = Integer.parseInt(v.elementAt(0));
		this.parentid = Integer.parseInt(v.elementAt(1));
		this.isparent = Integer.parseInt(v.elementAt(2));
		this.cmd = v.elementAt(3);
		this.cmdpara = v.elementAt(4);
		this.menuorder = Integer.parseInt(v.elementAt(5));
		this.status = Integer.parseInt(v.elementAt(6));
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

	public int getIsparent() {
		return isparent;
	}

	public void setIsparent(int isparent) {
		this.isparent = isparent;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getCmdpara() {
		return cmdpara;
	}

	public void setCmdpara(String cmdpara) {
		this.cmdpara = cmdpara;
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

	public static MenuItem get(int id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,PARENTID,ISPARENT,CMD,CMDPARA,MENUORDER,STATUS from SHARE14_WECHAT_MENU " +
				"where ID="+id;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new MenuItem(v.elementAt(0));
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
