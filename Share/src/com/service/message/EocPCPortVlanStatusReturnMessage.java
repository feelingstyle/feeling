package com.service.message;

import java.util.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class EocPCPortVlanStatusReturnMessage {

	private String responsetime;
	private String responsestatus;
	private String responsenote;
	private EocPCPortVlanStatusReturnMessageDetail responsedetail;
	
	public EocPCPortVlanStatusReturnMessage() {
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

	public EocPCPortVlanStatusReturnMessageDetail getDetail() {
		return responsedetail;
	}

	public void setDetail(EocPCPortVlanStatusReturnMessageDetail responsedetail) {
		this.responsedetail = responsedetail;
	}

	public String toXML()
	{
		XStream xstream = new XStream(new DomDriver()); 
		xstream.alias("xml",EocPCPortVlanStatusReturnMessage.class);
		xstream.aliasField("responsetime",EocPCPortVlanStatusReturnMessage.class, "responsetime");
		xstream.aliasField("responsestatus",EocPCPortVlanStatusReturnMessage.class, "responsestatus");
		xstream.aliasField("responsenote",EocPCPortVlanStatusReturnMessage.class, "responsenote");

		xstream.alias("responsedetail",EocPCPortVlanStatusReturnMessageDetail.class);
		xstream.aliasField("eocmac",EocPCPortVlanStatusReturnMessageDetail.class, "eocmac");
		xstream.aliasField("pcportvlanstatus",EocPCPortVlanStatusReturnMessageDetail.class, "pcportvlanstatus");
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
