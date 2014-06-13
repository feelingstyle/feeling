<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.operator.*"%>
<%@ page import="com.widget.*"%>
<%@ page import="com.util.*"%>
<%@ page import="org.json.*"%>
<%@ page import="java.util.*"%>

<%
String id = request.getParameter("id");
if (id==null || id.length()<=0)
{
	out.println("请求参数不足");
	return;
}

Operator op = (Operator)session.getAttribute("opsession");
if (op==null)
{
	out.println(Comm.gotoIndexForSessionExpired());
	return;
}

try{
	MenuItem mi = MenuItem.get(Integer.parseInt(id));
		
	response.sendRedirect(mi.getUrl()+"?id="+id+"&"+mi.getUrlpara());
	return;
}catch (Exception e)
{
	e.printStackTrace();
	out.println("请求参数有误");
	return;
}
	

%>