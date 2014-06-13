<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.action.*"%>
<%@ page import="com.operator.*"%>
<%@ page import="java.util.*"%>

<style type="text/css">
@import "css/main.css";
</style> 

<script src="js/jsonrpc.js"></script>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery.corners.js"></script>
<script src="js/json2.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="domainlogin" scope="session" class="com.action.DomainLogin"/>
<jsp:useBean id="calendar" scope="session" class="com.widget.Calendar"/>
<jsp:useBean id="calendarmgr" scope="session" class="com.widget.CalendarManager"/>
<jsp:useBean id="opobj" scope="session" class="com.operator.Operator"/>
<jsp:useBean id="commdatamgr" scope="session" class="com.util.CommonDataManager"/>

<%
JSONRPCBridge.registerObject("domainlogin",domainlogin);
JSONRPCBridge.registerObject("calendar",calendar);
JSONRPCBridge.registerObject("calendarmgr",calendarmgr);
JSONRPCBridge.registerObject("opobj",opobj);
JSONRPCBridge.registerObject("commdatamgr",commdatamgr);
%>

<script>
var jsonrpc;

$(document).ready(function () { 
	jsonrpc = new JSONRpcClient("/Share/JSON-RPC");

	var authfromlist = jsonrpc.commdatamgr.getList("authfrom","BASIC");
	if (authfromlist)
	{
		for (var i=0;i<authfromlist.list.length;i++)
		{
			$("#authfrom").append("<option value='"+authfromlist.list[i]["key"]+"'>"+authfromlist.list[i]["value"]+"</option>");
		}
	}
	
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
	
	$("#authfrom").change(function(){
		if ($(this).val()==1)
		{
			$("#domainSuffix").show();
		}else{
			$("#domainSuffix").hide();
		}
	})
	
	//检查登录情况
	checkLogin();

});

//检查登录情况
function checkLogin()
{
	var op = jsonrpc.opobj.checkSession("opsession");
	if (op)
	{
		window.location.href = "main.jsp";
		return;
	}
}

function login()
{
	var opname = $("#opname").val();
	var oppwd = $("#oppwd").val();
	if (opname && oppwd && opname.length>0 && oppwd.length>0)
	{
		var op=null;
		
		switch($("#authfrom").val())
		{
			case "1": 	op = jsonrpc.domainlogin.login(opname,oppwd,"opsession");
						break;
			case "2": 	
						break;
		}
		
		if (op)
		{
			jsonrpc.calendarmgr.initOPCalendar(op.loginname,op.authfrom);
			window.location.href = "main.jsp";
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
	<img src="pic/ocn.png" align="absmiddle"></td>
</tr>
</table>
<br>
<table width="100%" border="0">
<tr>
	<td height="430" style="border-top:1 solid #f3f3f3;background:url('pic/indexbg.jpg')">
	<table width="100%" border="0">
	<tr>
		<td width="10%">&nbsp;</td>
		<td width="55%" valign="top">
		<br>
		<span style="font-size:25;color:#838383"><b>东方有线信息分享系统</b></span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span style="font-size:22;color:#9999ff">Share&nbsp;V</span><br><br>
		<br><br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="pic/title-whitecolar.png">
		</td>
		<td width="35%">
		<table width="100%" border="0">
		<tr>
			<td style='color:#7777ff'>认证源:</td>
			<td>
			<select id="authfrom">
			</select>
			</td>
		</tr>
		<tr>
			<td width='20%' style='color:#7777ff'>用户名:</td>
			<td width='80%'><input class='inputbox' type='text' id='opname' size='15'>
			<span style="font-size:16;color:#9999ff" id='domainSuffix'>@scn.com.cn</span></td>
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
			<span class='func_btn_blue' style='width:80;text-align:center;cursor:hand' onclick='login()'>登录</span>
			</td>
		</tr>
		</table>
		</td>
	</tr>
	</table>
	</td>
</tr>
</table>