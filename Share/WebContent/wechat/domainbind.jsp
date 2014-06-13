<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.action.*"%>
<%@ page import="com.operator.*"%>
<%@ page import="java.util.*"%>

<style type="text/css">
@import "../css/main.css";
</style> 

<script src="../js/jsonrpc.js"></script>
<script src="../js/jquery-1.9.1.js"></script>
<script src="../js/jquery.corners.js"></script>
<script src="../js/json2.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="domainbind" scope="session" class="com.action.DomainBind"/>
<jsp:useBean id="opobj" scope="session" class="com.operator.Operator"/>

<%
JSONRPCBridge.registerObject("domainbind",domainbind);
JSONRPCBridge.registerObject("opobj",opobj);

String openID = request.getParameter("openid");
if (openID==null || openID.length()<=0)
{
	out.println("绑定缺少参数，请重新请求绑定");
	return;
}
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
			bind(); 
		}      
	});
	
});

function bind()
{
	var opname = $("#opname").val();
	var oppwd = $("#oppwd").val();
	if (opname && oppwd && opname.length>0 && oppwd.length>0)
	{
		var op = jsonrpc.domainbind.bind(opname,oppwd,"<%=openID%>");
		
		if (op)
		{
			$("#opname").val("");
			$("#oppwd").val("");
			alert("绑定成功");
		}else{
			alert("登录不成功，请检查用户名和密码");
		}
	}else{
		alert("请填写域用户名及密码完成登录");
	}
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
		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span style="font-size:22;color:#9999ff">微信用户域用户名绑定</span><br><br>
		<br><br>
		<table width="70%" border="0" align="center">
		<tr>
			<td colspan="2">
			<input type="hidden" id="authfrom" value="1">
			</td>
		</tr>
		<tr>
			<td width='20%' style='color:#7777ff'>用户名:</td>
			<td width='80%'><input class='inputbox' type='text' id='opname' size='15'>
			<span style="font-size:16;color:#9999ff">@scn.com.cn</span></td>
		</tr>
		<tr>
			<td style='color:#7777ff'>密码:</td>
			<td><input class='inputbox' type='password' id='oppwd' size='15'></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2">
			<span class='func_btn_blue' style='width:80;text-align:center;cursor:hand' onclick='bind()'>绑定</span>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	</table>
	</td>
</tr>
</table>