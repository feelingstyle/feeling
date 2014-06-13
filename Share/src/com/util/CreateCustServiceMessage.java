package com.util;


import com.http.HttpsPost;
import com.wechat.Util;
import com.wechat.message.CustComplexMessage;
import com.wechat.message.CustMessage;

public class CreateCustServiceMessage {
	public static String SendMsg(CustMessage msg)
	{		
		HttpsPost httpspost = new HttpsPost();
		httpspost.setContenttype("text/xml");
		//String token="ez3-7lTm2GRNnmi40ufi6eDasK8EHoENqB0cbhyEENkE1aIdskzhpiB8HkEpcUr7";
		String token=Util.getAccessToken();
		httpspost.setUrl("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+token);
		httpspost.setStr(msg.toJson().toString());
		String res = httpspost.getResponse();
		return res;
	}
	
	public static String SendComplexMsg(CustComplexMessage cmsg)
	{		
		HttpsPost httpspost = new HttpsPost();
		httpspost.setContenttype("text/xml");
		//String token="ez3-7lTm2GRNnmi40ufi6eDasK8EHoENqB0cbhyEENkE1aIdskzhpiB8HkEpcUr7";
		String token=Util.getAccessToken();
		httpspost.setUrl("https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token="+token);
		httpspost.setStr(cmsg.toJson().toString());
		String res = httpspost.getResponse();
		return res;
	}
	

}
