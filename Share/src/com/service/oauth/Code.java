package com.service.oauth;

import java.util.Vector;
import com.db.*;
import org.json.*;

public class Code {

	private String appid;
	private long opid;
	private String scope;
	private String authcode;
	private String createdate;
	private long accesstimestamp;
	private int expirein;
	private long accessexpiretimestamp;
	private String refreshtoken;
	private String accesstoken;
	private int status;
	
	public Code() {
		// TODO Auto-generated constructor stub
	}

	public Code(String appid, long opid, String scope, String authcode,
			String createdate, long accesstimestamp, int expirein,
			long accessexpiretimestamp, String refreshtoken,
			String accesstoken, int status) {
		this.appid = appid;
		this.opid = opid;
		this.scope = scope;
		this.authcode = authcode;
		this.createdate = createdate;
		this.accesstimestamp = accesstimestamp;
		this.expirein = expirein;
		this.accessexpiretimestamp = accessexpiretimestamp;
		this.refreshtoken = refreshtoken;
		this.accesstoken = accesstoken;
		this.status = status;
	}

	public Code(Vector<String> v) {
		this.appid = v.elementAt(0);
		this.opid = Long.parseLong(v.elementAt(1));
		this.scope = v.elementAt(2);
		this.authcode = v.elementAt(3);
		this.createdate = v.elementAt(4);
		this.accesstimestamp = Long.parseLong(v.elementAt(5));
		this.expirein = Integer.parseInt(v.elementAt(6));
		this.accessexpiretimestamp = Long.parseLong(v.elementAt(7));
		this.refreshtoken = v.elementAt(8);
		this.accesstoken = v.elementAt(9);
		this.status = Integer.parseInt(v.elementAt(10));
	}
	
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public long getOpid() {
		return opid;
	}

	public void setOpid(long opid) {
		this.opid = opid;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public long getAccesstimestamp() {
		return accesstimestamp;
	}

	public void setAccesstimestamp(long accesstimestamp) {
		this.accesstimestamp = accesstimestamp;
	}

	public int getExpirein() {
		return expirein;
	}

	public void setExpirein(int expirein) {
		this.expirein = expirein;
	}

	public long getAccessexpiretimestamp() {
		return accessexpiretimestamp;
	}

	public void setAccessexpiretimestamp(long accessexpiretimestamp) {
		this.accessexpiretimestamp = accessexpiretimestamp;
	}

	public String getRefreshtoken() {
		return refreshtoken;
	}

	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public static Code getByAuthcode(String authcode)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select APPID,OPID,SCOPE,AUTHCODE,CREATEDATE,ACCESSTIMESTAMP,EXPIRIN,ACCESSEXPIRETIMESTAMP," +
				"REFRESHTOKEN,ACCESSTOKEN,STATUS from SHARE14_OAUTH_CODE " +
				"where AUTHCODE='"+authcode+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Code(v.elementAt(0));
	}
	
	public static Code getByAccessToken(String accesstoken)
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select APPID,OPID,SCOPE,AUTHCODE,CREATEDATE,ACCESSTIMESTAMP,EXPIRIN,ACCESSEXPIRETIMESTAMP," +
				"REFRESHTOKEN,ACCESSTOKEN,STATUS from SHARE14_OAUTH_CODE " +
				"where ACCESSTOKEN='"+accesstoken+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		db.closeConn();
		
		if (v==null || v.size()<=0)
			return null;
		
		return new Code(v.elementAt(0));
	}
	
	public int save()
	{
		DbConn db = new DbConn();
		db.createConn();
		String sql = "select * from SHARE14_OAUTH_CODE where AUTHCODE='"+this.authcode+"'";
		Vector<Vector<String>> v = db.querySql(sql);
		if (v==null || v.size()<=0)
		{
			sql = "insert into SHARE14_OAUTH_CODE(APPID,OPID,SCOPE,AUTHCODE,CREATEDATE,ACCESSTIMESTAMP," +
					"EXPIRIN,ACCESSEXPIRETIMESTAMP,REFRESHTOKEN,ACCESSTOKEN,STATUS) values('"+this.appid+"'," +
					this.opid+",'"+this.scope+"','"+this.authcode+"',sysdate,"+this.accesstimestamp+","+
					this.expirein+","+this.accessexpiretimestamp+"," +
					"'"+this.refreshtoken+"','"+this.accesstoken+"',"+this.status+")";
		}else{
			sql = "update SHARE14_OAUTH_CODE set ACCESSTIMESTAMP="+this.accessexpiretimestamp+"," +
					"EXPIRIN="+this.expirein+",ACCESSEXPIRETIMESTAMP="+this.accessexpiretimestamp+"," +
					"REFRESHTOKEN='"+this.refreshtoken+"',ACCESSTOKEN='"+this.accesstoken+"'," +
					"STATUS="+this.status+" where AUTHCODE='"+this.authcode+"'";
		}
		
		int result = db.updateSql(sql);
		db.closeConn();
		
		return result;
	}

	public String toJson()
	{
		JSONObject jo = new JSONObject();
		try{
			jo.put("appid", this.appid);
			jo.put("opid", this.opid);
			jo.put("scope", this.scope);
			jo.put("authcode", this.authcode);
			jo.put("createdate", this.createdate);
			jo.put("accesstimestamp", this.accesstimestamp);
			jo.put("expirein", this.expirein);
			jo.put("accessexpiretimestamp", this.accessexpiretimestamp);
			jo.put("refreshtoken", this.refreshtoken);
			jo.put("accesstoken", this.accesstoken);
			jo.put("status", this.status);
		}catch (JSONException jsone)
		{
			jsone.printStackTrace();
			return null;
		}
		
		return jo.toString();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
