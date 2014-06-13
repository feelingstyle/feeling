package com.wechat.menu;

import java.util.*;
import com.db.*;

public class MenuCmdItem {

	private int id;
	private int classorder;
	private String classname;
	private String para;
	private int status;
	private String returnclassname;
	
	public MenuCmdItem() {
		// TODO Auto-generated constructor stub
	}

	public MenuCmdItem(int id, int classorder, String classname, String para,
			int status,String returnclassname) {
		this.id = id;
		this.classorder = classorder;
		this.classname = classname;
		this.para = para;
		this.status = status;
		this.returnclassname = returnclassname;
	}

	public MenuCmdItem(Vector<String> v) {
		this.id = Integer.parseInt(v.elementAt(0));
		this.classorder = Integer.parseInt(v.elementAt(1));
		this.classname = v.elementAt(2);
		this.para = v.elementAt(3);
		this.status = Integer.parseInt(v.elementAt(4));
		this.returnclassname = v.elementAt(5);
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getClassorder() {
		return classorder;
	}

	public void setClassorder(int classorder) {
		this.classorder = classorder;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReturnclassname() {
		return returnclassname;
	}

	public void setReturnclassname(String returnclassname) {
		this.returnclassname = returnclassname;
	}

	public static MenuCmdItem getFirst(int id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,CLASSORDER,CLASSNAME,PARACONFIG,STATUS,RETURNMESSAGECLASSNAME from SHARE14_WECHAT_MENU_CMD " +
				"where ID="+id+" and STATUS=1 order by CLASSORDER";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new MenuCmdItem(v.elementAt(0));
	}
	
	public MenuCmdItem getNext()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,CLASSORDER,CLASSNAME,PARACONFIG,STATUS,RETURNMESSAGECLASSNAME from SHARE14_WECHAT_MENU_CMD " +
				"where ID="+this.id+" and CLASSORDER>"+this.classorder+" and STATUS=1 order by CLASSORDER";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new MenuCmdItem(v.elementAt(0));
	}
	
	public static MenuCmdItem get(int id,int classorder)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,CLASSORDER,CLASSNAME,PARACONFIG,STATUS,RETURNMESSAGECLASSNAME from SHARE14_WECHAT_MENU_CMD " +
				"where ID="+id+" and CLASSORDER="+classorder+" and STATUS=1";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new MenuCmdItem(v.elementAt(0));
	}
	
	public String toString() {
		return "MenuCmdItem [id=" + id + ", classorder=" + classorder
				+ ", classname=" + classname + ", para=" + para + ", status="
				+ status + ", returnclassname=" + returnclassname + "]";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
