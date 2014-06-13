package com.service.maintain;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.service.message.*;


public class EocPCPortStatus extends HttpServlet {

	public EocPCPortStatus() {
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String accesstoken = req.getParameter("accesstoken");
		
		if (accesstoken==null)
		{
			resp.getWriter().println("test msg : access token error");
			return;
		}
		
		System.out.println(accesstoken);
		doPost(req,resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		String reqXML = IOUtils.toString(req.getInputStream(),"utf-8");
		String replyXml = "";
		System.out.println("request:\r\n"+reqXML);
		//����request
		HashMap<String,String> msgAttributeMap = MessageUtil.toMap(reqXML);
		if (msgAttributeMap==null)
		{
			pw.println("�����������");
			return;
		}
		
		EocPCPortStatusReturnMessage msg = new EocPCPortStatusReturnMessage();
		EocPCPortStatusReturnMessageDetail msgdetail = new EocPCPortStatusReturnMessageDetail();
		msgdetail.setEocmac("00:00:00:00:00:00");
		msgdetail.setPCPortstatus("����");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		msg.setResponsetime(time.toString());
		msg.setResponsestatus("1");
		msg.setResponsenote("���óɹ�");
		msg.setDetail(msgdetail);
		
		replyXml = msg.toXML();
		System.out.println("response:\r\n"+replyXml);
		pw.println(replyXml);
	}

	public void init() throws ServletException {
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
