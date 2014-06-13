package com.service.message;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class EocRFStatusReturnMessage {

	private String responsetime;
	private String responsestatus;
	private String responsenote;
	private EocRFStatusReturnMessageDetail responsedetail;
	
	public EocRFStatusReturnMessage() {
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

	public EocRFStatusReturnMessageDetail getDetail() {
		return responsedetail;
	}

	public void setDetail(EocRFStatusReturnMessageDetail responsedetail) {
		this.responsedetail = responsedetail;
	}

	public String toXML()
	{
		XStream xstream = new XStream(new DomDriver()); 
		xstream.alias("xml", EocRFStatusReturnMessage.class);
		xstream.aliasField("responsetime", EocRFStatusReturnMessage.class, "responsetime");
		xstream.aliasField("responsestatus", EocRFStatusReturnMessage.class, "responsestatus");
		xstream.aliasField("responsenote", EocRFStatusReturnMessage.class, "responsenote");

		xstream.alias("responsedetail", EocRFStatusReturnMessageDetail.class);
		xstream.aliasField("eocmac", EocRFStatusReturnMessageDetail.class, "eocmac");
		xstream.aliasField("rfstatus", EocRFStatusReturnMessageDetail.class, "rfstatus");
		String xml =xstream.toXML(this);
		
		ArrayList<String> attrnames = new ArrayList<String>();
		attrnames.add("responsenote");
		attrnames.add("rfstatus");
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
