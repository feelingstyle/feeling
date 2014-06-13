package com.wechat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.util.*;
import java.util.*;
import java.io.*;
import com.wechat.menu.*;
import com.wechat.cmd.*;
import com.wechat.message.*;
import com.operator.*;
import org.apache.commons.io.*;
import org.json.JSONArray;

public class Entrance extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String token = "ocnshare";
	private static HashMap<String,MenuItem> mapCMD = null;
	
	public Entrance() {
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		mapCMD = MenuItemManager.getMenuItemMap();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// ΢�ż���ǩ��
        String signature = req.getParameter("signature");
        // ����ַ���
        String echostr = req.getParameter("echostr");
        // ʱ���
        String timestamp = req.getParameter("timestamp");
        // �����
        String nonce = req.getParameter("nonce");
        
        // ȷ������������΢��
        if (Comm.checkSignature(token, timestamp, nonce, signature)) {
            resp.getWriter().print(echostr);
        }
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter pw = resp.getWriter();
		String reqXML = IOUtils.toString(req.getInputStream(),"utf-8");
		Message reqMsg = null;
		String replyXml = "";
		System.out.println("request:\r\n"+reqXML);
		//����request
		HashMap<String,String> msgAttributeMap = MessageUtil.toMap(reqXML);
		String msgtype = msgAttributeMap.get("MsgType");
		
		if (msgtype!=null && msgtype.equals("text"))
		{//�ı���Ϣ
			try {
				reqMsg = RequestTextMessage.get(reqXML);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			replyXml = MessageProcess.onTextRequest(reqMsg, mapCMD);
	
		}
		
		if (msgtype!=null && msgtype.equals("event"))
		{//�¼����
			String event = msgAttributeMap.get("Event");
			if (event.equals("CLICK"))
			{//�˵����
				try {
					reqMsg = RequestClickEventMessage.get(reqXML);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				replyXml = MessageProcess.onMenuClick(reqMsg, mapCMD);
			}
		}
			
		System.out.println("response:\r\n"+replyXml);
		pw.println(replyXml);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
