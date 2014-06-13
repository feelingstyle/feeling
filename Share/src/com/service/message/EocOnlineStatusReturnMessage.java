package com.service.message;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class EocOnlineStatusReturnMessage {

	private String responsetime;
	private String responsestatus;
	private String responsenote;
	private EocOnlineStatusReturnMessageDetail responsedetail;
	
	public EocOnlineStatusReturnMessage() {
		// TODO Auto-generated constructor stub
	}
	
	public String getResponsetime() {
		return responsetime;
	}

	public void setResponsetime(String responsetime) {
		this.responsetime = responsetime;
	}

	public String getResponsestatus() {
		return responsestatus;
	}

	public void setResponsestatus(String responsestatus) {
		this.responsestatus = responsestatus;
	}

	public String getResponsenote() {
		return responsenote;
	}

	public void setResponsenote(String responsenote) {
		this.responsenote = responsenote;
	}

	public EocOnlineStatusReturnMessageDetail getDetail() {
		return responsedetail;
	}

	public void setDetail(EocOnlineStatusReturnMessageDetail responsedetail) {
		this.responsedetail = responsedetail;
	}

	public String toXML()
	{
		XStream xstream = new XStream(new DomDriver()); 
		xstream.alias("xml", EocOnlineStatusReturnMessage.class);
		xstream.aliasField("responsetime", EocOnlineStatusReturnMessage.class, "responsetime");
		xstream.aliasField("responsestatus", EocOnlineStatusReturnMessage.class, "responsestatus");
		xstream.aliasField("responsenote", EocOnlineStatusReturnMessage.class, "responsenote");

		xstream.alias("responsedetail", EocOnlineStatusReturnMessageDetail.class);
		xstream.aliasField("eocmac", EocOnlineStatusReturnMessageDetail.class, "eocmac");
		xstream.aliasField("eocstatus", EocOnlineStatusReturnMessageDetail.class, "eocstatus");
		String xml =xstream.toXML(this);
		
		ArrayList<String> attrnames = new ArrayList<String>();
		attrnames.add("responsenote");
		attrnames.add("eocstatus");
		xml = MessageUtil.addCDataSection(xml, attrnames);
		
		return xml;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
