<%@page import="com.service.operator.Operator"%>
<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.util.*"%>
<%@ page import="com.action.*"%>
<%@ page import="com.operator.*"%>
<%@ page import="com.service.operator.*"%>
<%@ page import="java.util.*"%>

<style type="text/css">
@import "../../css/main.css";
</style> 

<script src="../../js/jsonrpc.js"></script>
<script src="../../js/jquery-1.9.1.js"></script>
<script src="../../js/json2.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="domainlogin" scope="session" class="com.action.DomainLogin"/>
<jsp:useBean id="opobj" scope="session" class="com.operator.Operator"/>
<jsp:useBean id="commdatamgr" scope="session" class="com.util.CommonDataManager"/>
<jsp:useBean id="serviceop" scope="session" class="com.service.operator.Operator"/>
<jsp:useBean id="serviceopmgr" scope="session" class="com.service.operator.OperatorManager"/>

<%
JSONRPCBridge.registerObject("domainlogin",domainlogin);
JSONRPCBridge.registerObject("opobj",opobj);
JSONRPCBridge.registerObject("commdatamgr",commdatamgr);
JSONRPCBridge.registerObject("serviceop",serviceop);
JSONRPCBridge.registerObject("serviceopmgr",serviceopmgr);

String client_id = request.getParameter("client_id");
String redirect_uri = request.getParameter("redirect_uri");
String state = request.getParameter("state");
String response_type = request.getParameter("response_type");
String scope = request.getParameter("scope");

if (client_id==null || redirect_uri==null || response_type==null || scope==null)
{
	out.println("请求方参数缺失");
	return;
}

Operator servop = Operator.getByAppID(client_id);
if (servop==null)
{
	out.println("请求Client不存在");
	return;
}

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
	});
	

});

function login()
{
	var opname = $("#opname").val();
	var oppwd = $("#oppwd").val();
	if (opname && oppwd && opname.length>0 && oppwd.length>0)
	{
		var authcode="";
		
		switch($("#authfrom").val())
		{
			case "1": 	authcode = jsonrpc.domainlogin.authorize(opname,oppwd,"opsession","<%=client_id%>","<%=scope%>");
						break;
			case "2": 	
						break;
		}
		
		if (authcode)
		{
			location.href = "<%=redirect_uri%>?code="+authcode+"&state=<%=state%>";
		}else{
			alert("授权码获取失败!");
			return;
		}
	}else{
		alert("请正确输入信息完成登录");
	}
}

</script>

<br>
<table width="100%" border="0">
<tr>
	<td style="border-bottom:#f3f3f3;">&nbsp;&nbsp;&nbsp;&nbsp;
	<img src="../../pic/ocn.png" align="absmiddle"></td>
</tr>
</table>
<br>
<table width="100%" border="0">
<tr>
	<td height="430" style="border-top:1 solid #f3f3f3;background:url('../../pic/indexbg.jpg')">
	<table width="100%" border="0">
	<tr>
		<td width="10%">&nbsp;</td>
		<td width="55%" valign="top">
		<br>
		<span style="font-size:25;color:#838383"><b>东方有线信息分享系统</b></span>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<span style="font-size:22;color:#9999ff">开放平台认证登录</span><br><br>
		<br><br>
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<img src="../../pic/title-whitecolar.png">
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