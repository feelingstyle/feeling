package com.service.message;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class EocPCPortStatusReturnMessage {

	private String responsetime;
	private String responsestatus;
	private String responsenote;
	private EocPCPortStatusReturnMessageDetail responsedetail;
	
	public EocPCPortStatusReturnMessage() {
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

	public EocPCPortStatusReturnMessageDetail getDetail() {
		return responsedetail;
	}

	public void setDetail(EocPCPortStatusReturnMessageDetail responsedetail) {
		this.responsedetail = responsedetail;
	}

	public String toXML()
	{
		XStream xstream = new XStream(new DomDriver()); 
		xstream.alias("xml", EocPCPortStatusReturnMessage.class);
		xstream.aliasField("responsetime", EocPCPortStatusReturnMessage.class, "responsetime");
		xstream.aliasField("responsestatus", EocPCPortStatusReturnMessage.class, "responsestatus");
		xstream.aliasField("responsenote", EocPCPortStatusReturnMessage.class, "responsenote");

		xstream.alias("responsedetail", EocPCPortStatusReturnMessageDetail.class);
		xstream.aliasField("eocmac", EocPCPortStatusReturnMessageDetail.class, "eocmac");
		xstream.aliasField("pcportvlanstatus", EocPCPortStatusReturnMessageDetail.class, "pcportvlanstatus");
		String xml =xstream.toXML(this);
		
		ArrayList<String> attrnames = new ArrayList<String>();
		attrnames.add("responsenote");
		attrnames.add("pcportvlanstatus");
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
