<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="java.util.*"%>

<style type="text/css">
@import "../css/main.css";
</style> 

<script src="../js/jsonrpc.js"></script>
<script src="../js/jquery-1.9.1.js"></script>
<script src="../js/json2.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="serviceop" scope="session" class="com.service.operator.Operator"/>
<jsp:useBean id="serviceopmgr" scope="session" class="com.service.operator.OperatorManager"/>

<%
JSONRPCBridge.registerObject("serviceop",serviceop);
JSONRPCBridge.registerObject("serviceopmgr",serviceopmgr);

String remoteIP = request.getHeader("x-forwarded-for");
if (remoteIP==null)
	remoteIP = request.getRemoteAddr();
%>

<script>
var jsonrpc;

$(document).ready(function () { 
	jsonrpc = new JSONRpcClient("/Share/JSON-RPC");
	
});

function register()
{
	var opname = $("#opname").val();
	var oppwd = $("#oppwd").val();
	var oppwd2 = $("#oppwd2").val();
	if (opname && oppwd && opname.length>0 && oppwd.length>0 && oppwd2.length>0)
	{
		if (oppwd!=oppwd2)
		{
			alert("�����������벻��ͬ!");
			return;
		}
		
		var op = jsonrpc.serviceop.get(opname);
		if (op)
		{
			if (op.status==0)
			{
				confirm("�Ƿ���Ҫ�ط�ȷ���ʼ�?");
				return;
			}else{
				alert("�������ѱ�ע��!");
				return;
			}
		}else{
			var result = jsonrpc.serviceopmgr.createOperator(opname,oppwd,"<%=remoteIP%>",$("#note").val());
			if (result && result>0)
			{
				alert("ע��ɹ�!");
			}else{
				alert("ע��ʧ��!");
				return;
			}
		}
	}else{
		alert("����д���û������������ע��!");
	}	
}

</script>

<br>
<table width="100%" border="0">
<tr>
	<td style="border-bottom:#f3f3f3;">&nbsp;&nbsp;&nbsp;&nbsp;
	<img src="../pic/ocn.png" align="absmiddle" style="cursor:hand" onclick="location.href='index.jsp'"></td>
</tr>
</table>
<br>
<table width="100%" border="0">
<tr>
	<td height="430" style="border-top:1 solid #f3f3f3;background:url('../pic/indexbg.jpg')" valign="top">
	<br>
	<table width="60%" border="0" align="center">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td width="25%" style='color:#7777ff'>����</td>
		<td width="75%"><input class='inputbox' type='text' id='opname' size='30'></td>
	</tr>
	<tr>
		<td style='color:#7777ff'>����</td>
		<td><input class='inputbox' type='password' id='oppwd' size='30'></td>
	</tr>
	<tr>
		<td style='color:#7777ff'>ȷ������</td>
		<td><input class='inputbox' type='password' id='oppwd2' size='30'></td>
	</tr>
	<tr>
		<td style='color:#7777ff'>����</td>
		<td><input class='inputbox' type='text' id='note' size='30'></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td><div class='func_btn_green' style='width:80;text-align:center;cursor:hand;margin:5;font-size:16;' onclick='register()'>ע��</div></td>
	</tr>
	</table>
	</td>
</tr>
</table>