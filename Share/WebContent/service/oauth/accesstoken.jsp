<%@page import="com.operator.Operator"%>
<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html;charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.action.*"%>
<%@ page import="com.operator.*"%>
<%@ page import="com.service.operator.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="com.util.*"%>
<%@ page import="com.service.oauth.*"%>

<%
String client_id = request.getParameter("client_id");
String client_secret = request.getParameter("client_secret");
String grant_type = request.getParameter("grant_type");
String code = request.getParameter("code");
String redirect_uri = request.getParameter("redirect_uri");
String state  = request.getParameter("state");

if (client_id==null || client_secret==null || code==null)
{
	out.println("{'responsecode':-4,'responsenote':'�������ȱʧ'}");
	return;
}

com.service.operator.Operator servop = com.service.operator.Operator.getByAppID(client_id);
if (servop==null)
{
	out.println("{'responsecode':-1,'responsenote':'Ӧ��У��ʧ��'}");
	return;
}

Code authcode = Code.getByAuthcode(code);
if (authcode==null)
{
	out.println("{'responsecode':-2,'responsenote':'��֤���ȡ��Ϣʧ��'}");
	return;
}

Operator op = Operator.get(authcode.getOpid());
if (op==null)
{
	out.println("{'responsecode':-3,'responsenote':'�޷���ȡ��Ȩ���û���Ϣ'}");
	return;
}

//������֤�˻�����Ϣ
JSONObject jores = new JSONObject();
jores.put("displayname", op.getDisplayName());
jores.put("mail", op.getMail());
jores.put("dn", op.getDn());

if (authcode.getAccesstoken()!=null && authcode.getAccesstoken().length()>0)
{
	authcode.setExpirein((int)(authcode.getAccessexpiretimestamp()-System.currentTimeMillis()));
	authcode.setAccesstimestamp(System.currentTimeMillis());
}else{
	authcode.setAccesstimestamp(System.currentTimeMillis());
	authcode.setAccesstoken(Comm.stringToSHA1("access"+Long.toString(authcode.getOpid())+Long.toString(System.currentTimeMillis())));
	authcode.setRefreshtoken(Comm.stringToSHA1("refresh"+Long.toString(authcode.getOpid())+Long.toString(System.currentTimeMillis())));
	authcode.setAccessexpiretimestamp(authcode.getAccesstimestamp()+authcode.getExpirein());
}

int i = authcode.save();
if (i<=0)
{
	out.println("{'responsecode':-2,'responsenote':'��֤���ȡ��Ϣʧ��'}");
	return;
}

//������֤����Ȩ��Ϣ
jores.put("accesstoken", authcode.getAccesstoken());
jores.put("refreshtoken", authcode.getRefreshtoken());
jores.put("expire_in",authcode.getExpirein());
jores.put("responsecode", 1);
jores.put("responsenote", "��Ȩ��Ϣ");

out.println(jores.toString());
%>