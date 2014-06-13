<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>
<head><META HTTP-EQUIV="REFRESH" CONTENT=300></head>

<%@ page import="com.operator.*"%>
<%@ page import="com.util.*"%>
<%@ page import="java.util.*"%>

<%
Operator op = (Operator)session.getAttribute("opsession");
if (op==null)
{
	out.println(Comm.gotoIndexForSessionExpired());
	return;
}
%>