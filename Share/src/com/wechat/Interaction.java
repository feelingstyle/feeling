package com.wechat;

import java.util.*;
import com.db.*;

public class Interaction {

	private String openID;
	private int cmdid;
	private String cmd;
	private String cmdpara;
	private int classorder;
	
	public Interaction() {
		// TODO Auto-generated constructor stub
	}
	
	public Interaction(String openID, int cmdid, String cmd, String cmdpara,
			int classorder) {
		this.openID = openID;
		this.cmdid = cmdid;
		this.cmd = cmd;
		this.cmdpara = cmdpara;
		this.classorder = classorder;
	}
	
	public Interaction(Vector<String> v) {
		this.openID = v.elementAt(0);
		this.cmdid = Integer.parseInt(v.elementAt(1));
		this.cmd = v.elementAt(2);
		this.cmdpara = v.elementAt(3);
		this.classorder = Integer.parseInt(v.elementAt(4));
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public int getCmdid() {
		return cmdid;
	}

	public void setCmdid(int cmdid) {
		this.cmdid = cmdid;
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

	public int getClassorder() {
		return classorder;
	}

	public void setClassorder(int classorder) {
		this.classorder = classorder;
	}

	public static Interaction get(String openID)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select OPENID,ID,CMD,CMDPARA,CLASSORDER from SHARE14_WECHAT_INTERACTION where OPENID='"+openID+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Interaction(v.elementAt(0));
	}
	
	public int save()
	{
		DbConn db = new DbConn();
		db.createConn();
		
		String sql = "";
		if (get(this.openID)==null)
		{
			sql = "insert into SHARE14_WECHAT_INTERACTION(OPENID,ID,CMD,CMDPARA,CLASSORDER) " +
					"values('"+this.openID+"',"+this.cmdid+",'"+this.cmd+"','"+this.cmdpara+"',"+this.classorder+")";
		}else{
			sql = "update SHARE14_WECHAT_INTERACTION set CMDPARA='"+this.cmdpara+"',CLASSORDER="+this.classorder+
					" where OPENID='"+this.openID+"'";
		}
		
		
		int iResult = db.updateSql(sql);
		db.closeConn();
		
		return iResult;
	}
	
	public int remove()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "delete from SHARE14_WECHAT_INTERACTION where OPENID='"+this.openID+"'";
		int iResult = db.updateSql(sql);
		db.closeConn();
		
		return iResult;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
