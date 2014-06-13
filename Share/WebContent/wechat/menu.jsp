<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.wechat.*"%>

<style type="text/css">
@import "../css/main.css";
</style> 

<script src="../js/jsonrpc.js"></script>
<script src="../js/jquery-1.9.1.js"></script>
<script src="../js/json2.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="cdatamgr" scope="session" class="com.util.CommonDataManager"/>
<jsp:useBean id="wechatutil" scope="session" class="com.wechat.Util"/>

<%
JSONRPCBridge.registerObject("cdatamgr",cdatamgr);
JSONRPCBridge.registerObject("wechatutil",wechatutil);
%>

<script>
$(document).ready(function () { 
	jsonrpc = new JSONRpcClient("/Share/JSON-RPC");
	
	var menuobj = jsonrpc.cdatamgr.getValue("wechat","BASIC","menu");
	menustr = "";
	if (menuobj!=null)
	{
		menustr = menuobj;
	}
	
	$("#wechat_menu").val(menustr);
	
	$("#menu_save").click(function(){
		var menustr = $("#wechat_menu").val();
		if (menustr==null || menustr.length<=0)
		{
			alert("请正确输入菜单格式");
			return;
		}
		
		var result = jsonrpc.wechatutil.createWechatMenu(menustr);
		alert(result);
	});
});
</script>

<table width="100%" border="0">
<tr>
	<td class="line120" style="color:#6A6AFF" width="15%">微信菜单</td>
	<td>&nbsp;</td>
</tr>
<tr>
	<td colspan="2">
	<textarea id="wechat_menu" cols="160" rows="35"></textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
	<div class="bluebtn" id="menu_save" style="width:60;text-align:center">保存</div>
	</td>
</tr>
</table>