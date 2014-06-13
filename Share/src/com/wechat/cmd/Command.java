package com.wechat.cmd;

import com.db.*;

import java.util.*;

public class Command {
	private int id;
	private int classorder;
	private String classname;
	private String paraconfig;
	private int status;
	private String returnclassname;
	
	public Command() {
		// TODO Auto-generated constructor stub
	}

	public Command(int id, int classorder, String classname, String paraconfig,
			int status, String returnclassname) {
		this.id = id;
		this.classorder = classorder;
		this.classname = classname;
		this.paraconfig = paraconfig;
		this.status = status;
		this.returnclassname = returnclassname;
	}
	
	public Command(Vector<String> v) {
		this.id = Integer.parseInt(v.elementAt(0));
		this.classorder = Integer.parseInt(v.elementAt(1));
		this.classname = v.elementAt(2);
		this.paraconfig = v.elementAt(3);
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

	public String getParaconfig() {
		return paraconfig;
	}

	public void setParaconfig(String paraconfig) {
		this.paraconfig = paraconfig;
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

	public static Command get(int id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,CLASSORDER,CLASSNAME,PARACONFIG,STATUS,RETURNMESSAGECLASSNAME" +
				" from SHARE14_WECHAT_MENU_CMD where ID="+id;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Command(v.elementAt(0));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
