package com.operator;

import org.json.JSONException;
import org.json.JSONObject;

import com.util.*;
import com.db.*;

import java.util.*;

import javax.servlet.http.HttpSession;

public class Operator {

	private long id;
	private String loginname;
	private String displayName;
	private int authfrom;
	private String modifydate;
	private int status;
	private String mail;
	private String phone;
	private String mobile;
	private String dn;
	
	public Operator()
	{
	}

	public Operator(long id, String loginname, String displayName,
			int authfrom, String modifydate, int status, String mail,
			String phone, String mobile, String dn) {
		this.id = id;
		this.loginname = loginname;
		this.displayName = displayName;
		this.authfrom = authfrom;
		this.modifydate = modifydate;
		this.status = status;
		this.mail = mail;
		this.phone = phone;
		this.mobile = mobile;
		this.dn = dn;
	}

	public Operator(Vector<String> v) {
		this.id = Long.parseLong(v.elementAt(0));
		this.loginname = v.elementAt(1);
		this.displayName = v.elementAt(2);
		this.authfrom = Integer.parseInt(v.elementAt(3));
		this.modifydate = v.elementAt(4);
		this.status = Integer.parseInt(v.elementAt(5));
		this.mail = v.elementAt(6);
		this.phone = v.elementAt(7);
		this.mobile = v.elementAt(8);
		this.dn = v.elementAt(9);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getAuthfrom() {
		return authfrom;
	}

	public void setAuthfrom(int authfrom) {
		this.authfrom = authfrom;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public static Operator get(long id)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LOGINNAME,DISPLAYNAME,AUTHFROM,to_char(MODIFYDATE,'yyyy-mm-dd HH24:mi'),STATUS,MAIL,PHONE,MOBILE,DN " +
						"from SHARE14_OPERATOR where ID="+id;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Operator(v.elementAt(0));
	}
	
	public static Operator get(String loginname,int authfrom)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select ID,LOGINNAME,DISPLAYNAME,AUTHFROM,to_char(MODIFYDATE,'yyyy-mm-dd HH24:mi'),STATUS,MAIL,PHONE,MOBILE,DN " +
						"from SHARE14_OPERATOR where LOGINNAME='"+loginname+"' and AUTHFROM="+authfrom;
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Operator(v.elementAt(0));
	}
	
	public static Operator checkSession(HttpSession session,String sessionName)
	{
		Object o = session.getAttribute(sessionName);
		if (o==null)
			return null;
		return (Operator)o;
	}
	
	public int saveNew()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select * from SHARE14_OPERATOR where LOGINNAME='"+this.getLoginname()+"' and AUTHFROM="+this.getAuthfrom();
		Vector<Vector<String>> v = db.querySql(sql);
		if (v!=null && v.size()>0)
		{
			sql = "update SHARE14_OPERATOR set DISPLAYNAME='"+this.getDisplayName()+"',MODIFYDATE=sysdate,MAIL='"+this.getMail()+"'," +
					"PHONE='"+this.getPhone()+"',MOBILE='"+this.getMobile()+"',DN='"+this.getDn()+"' " +
					"where LOGINNAME='"+this.getLoginname()+"' and AUTHFROM="+this.getAuthfrom();
		}else{
			sql = "insert into SHARE14_OPERATOR(ID,LOGINNAME,DISPLAYNAME,AUTHFROM,MODIFYDATE,STATUS,MAIL,PHONE,MOBILE,DN) " +
				"values("+System.currentTimeMillis()+",'"+this.getLoginname()+"','"+this.getDisplayName()+"',"+this.authfrom+"," +
				"sysdate,1,'"+this.getMail()+"','"+this.getPhone()+"','"+this.getMobile()+"','"+this.getDn()+"')";
		}
		int i = db.updateSql(sql);
		db.closeConn();
		
		return i;
	}

	public String toString() {
		return "Operator [用户标识=" + id + ", 域登录名=" + loginname
				+ ", 显示名=" + displayName + ", 授权方=" + authfrom
				+ ", 最后操作日期=" + modifydate + ", mail=" + mail + ", 组织结构=" + dn + "]";
	}

	


}
