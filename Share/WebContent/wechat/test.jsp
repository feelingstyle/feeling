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
	
	var msg = "{\"touser\":\"okyK1jgG1_ycoGBYq2JrdinwXNos\"," +
					"\"msgtype\":\"text\"," +
					"\"text\":{" +
						"\"content\":\"你好，晚上有空出去遛弯吗！？\"" +
					"}}";
					
	var result = jsonrpc.wechatutil.createCustServiceMessage(msg);
	alert(result);
});
</script>