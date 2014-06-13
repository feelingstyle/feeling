package com.util;

import java.security.MessageDigest;
import java.util.*;
import org.json.*;
import com.db.*;
import javax.servlet.http.*;

/**
 * @author red23
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Comm {

	public static int generateTableSerialId(String sTableName,String sIdCol)
	{
		DbConn db = new DbConn();
		String sql = "select NVL(max("+sIdCol+"),0) from " + sTableName;
		db.createConn();
		Vector<Vector<String>> vMaxId = db.querySql(sql);
		db.closeConn();
		
		return Integer.parseInt(vMaxId.elementAt(0).elementAt(0));
		
	}
	
	public static int generateTableSerialId(String sTableName,String sIdCol,String sCondition)
	{
		DbConn db = new DbConn();
		String sql = "select NVL(max("+sIdCol+"),0) from " + sTableName + sCondition;
		db.createConn();
		Vector<Vector<String>> vMaxId = db.querySql(sql);
		db.closeConn();
		
		return Integer.parseInt(vMaxId.elementAt(0).elementAt(0));
	}
	
	public static String gotoIndexForSessionExpired()
	{
		String s = "<script>";
		s+="alert('session expired!');";
		s+="top.location.href='/Share/index.jsp';";
		s+="</script>";
		
		return s;
	}
	
	public static String getDecode(String s)  
	{
		if (s==null)
			s ="";
		
		String sR="";
		try{
			sR = new String(s.getBytes("8859_1"),"gb2312");
		}catch(java.io.UnsupportedEncodingException e)
		{}
		return sR;
	}

	//get Password
	public static String getPassword(String pass)
	{
		byte[] bytearray=pass.getBytes();
		
		for(int i=0;i<bytearray.length;i++){
			bytearray[i]=(byte)(bytearray[i]^i);
		}
		String result=new String(bytearray);
		return result;
	}
		
	public static void removeSession(HttpSession session,String sessionName)
	{
		session.removeAttribute(sessionName);
	}
	
	public static boolean checkSignature(String token,String timestamp,String nonce,String signature)
	{
		String[] str = { token, timestamp, nonce };
        Arrays.sort(str); // ×ÖµäÐòÅÅÐò
        String reqStr = str[0] + str[1] + str[2];
        String digest = stringToSHA1(reqStr);
        if (digest.equals(signature)) {
            return true;
        }else{
        	return false;
        }
	}
	
	public static String stringToSHA1(String input)
	{
		String s = "";
		
		try{
			MessageDigest md = MessageDigest.getInstance("SHA1");
			md.update(input.getBytes()); 
	      	byte[] output = md.digest();
	      	s = bytesToHex(output);
		}catch (Exception e)
		{
			e.printStackTrace();
			return "";
		}
		
		return s;
	}
	
	public static String bytesToHex(byte[] b) {
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
	                         '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		StringBuffer buf = new StringBuffer();
	    for (int j=0; j<b.length; j++) {
	    	buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
	        buf.append(hexDigit[b[j] & 0x0f]);
	    }
	    return buf.toString();
	}
}
