package com.service.oauth;

import com.operator.Operator;
import com.util.*;

public class CodeManager {

	public CodeManager() {
		// TODO Auto-generated constructor stub
	}

	public static String createAuthcode(String appid,long opid,String scope)
	{
		String authcode = Comm.stringToSHA1("authcode"+Long.toString(opid)+Long.toString(System.currentTimeMillis()));
		Code code = new Code();
		code.setAppid(appid);
		code.setOpid(opid);
		code.setScope(scope);
		code.setAuthcode(authcode);
		code.setAccesstimestamp(0);
		String expirein = CommonDataManager.getValue("oauth", "BASIC", "expiretimestamp");
		code.setExpirein(Integer.parseInt(expirein));
		code.setAccessexpiretimestamp(0);
		code.setRefreshtoken("");
		code.setAccesstoken("");
		code.setStatus(1);
		int result = code.save();
		
		if (result>0)
			return authcode;
		
		return null;
	}
	
	public static String checkAccessToken(String accesstoken)
	{
		Code authcode = Code.getByAccessToken(accesstoken);
		if (System.currentTimeMillis()<authcode.getAccessexpiretimestamp())
		{
			return authcode.toJson();
		}
		
		return "{'responsecode':-1,'responsenote':'access token expired!'}";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
