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
	
	$("#opname").bind('keyup',function(event) {   
		if(event.keyCode==13){   
			$("#oppwd").focus(); 
		}      
	});
	
	$("#oppwd").bind('keyup',function(event) {   
		if(event.keyCode==13){   
			login(); 
		}      
	});
	
});

function login()
{
	var opname = $("#opname").val();
	var oppwd = $("#oppwd").val();
	if (opname && oppwd && opname.length>0 && oppwd.length>0)
	{
		
	}else{
		alert("请填写域用户名及密码完成登录");
	}
}

function register()
{
	location.href = "register.jsp";
}

</script>

<br>
<table width="100%" border="0">
<tr>
	<td style="border-bottom:#f3f3f3;">&nbsp;&nbsp;&nbsp;&nbsp;
	<img src="../pic/ocn.png" align="absmiddle"></td>
</tr>
</table>
<br>
<table width="100%" border="0">
<tr>
	<td height="430" style="border-top:1 solid #f3f3f3;background:url('../pic/indexbg.jpg')">
	<table width="100%" border="0">
	<tr>
		<td width="10%">&nbsp;</td>
		<td width="55%" valign="top">
		<br>
		<span style="font-size:25;color:#838383"><b>东方有线信息分享系统&nbsp;|&nbsp;服务平台</b></span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span style="font-size:22;color:#9999ff">Share&nbsp;Service</span><br><br>
		<br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="../pic/title-whitecolar2.png">
		</td>
		<td width="35%">
		<table width="100%" border="0">
		<tr>
			<td width='20%' style='color:#7777ff'>用户名:</td>
			<td width='80%'><input class='inputbox' type='text' id='opname' size='20'>&nbsp;(注册邮箱&nbsp;or&nbsp;AppID)</td>
		</tr>
		<tr>
			<td style='color:#7777ff'>密码:</td>
			<td><input class='inputbox' type='password' id='oppwd' size='20'></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">
			<div class='func_btn_blue' style='width:80;text-align:center;cursor:hand;margin:5;font-size:16;' onclick='login()'>登录</div>
			<div class='func_btn_green' style='width:80;text-align:center;cursor:hand;margin:5;font-size:16;' onclick='register()'>注册</div>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	</table>
	</td>
</tr>
</table>