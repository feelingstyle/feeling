<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.operator.*"%>
<%@ page import="java.util.*"%>

<style type="text/css">
@import "../css/main.css";
</style> 

<script src="../js/jsonrpc.js"></script>
<script src="../js/jquery-1.9.1.js"></script>
<script src="../js/jquery.corners.js"></script>
<script src="../js/jquery.ztree-2.6.js"></script>
<script src="../js/json2.js"></script>
<script src="../js/widget/mainmenu.js"></script>
<script src="../js/widget/tab.js"></script>
<script src="../js/widget/tablayout.js"></script>
<script src="../js/widget/combobox.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="menuobj" scope="session" class="com.widget.MenuItem"/>
<jsp:useBean id="opobj" scope="session" class="com.operator.Operator"/>
<jsp:useBean id="comm" scope="session" class="com.util.Comm"/>

<%
JSONRPCBridge.registerObject("menuobj",menuobj);
JSONRPCBridge.registerObject("opobj",opobj);
JSONRPCBridge.registerObject("comm",comm);
%>

<script >
var jsonrpc;
var mainTabLayout;

$(document).ready(function () { 
	jsonrpc = new JSONRpcClient("/Share/JSON-RPC");

	mainTabLayout = new TabLayout($("#tab_container"));
	mainTabLayout.init({
		tabClick:clickTab,
		tab_layout:"left",
		tab_body_height : 500,
		tabClassName : "calendartab",
		curtabClassName : "curcalendartab"
	});

	var newTab = new Tab();
	newTab.tab_header_name = "日历查询";
	newTab.tab_body_url = "mycalendar.jsp";
	newTab.tab_body_url_para = "";
	var tmpTabIndex = mainTabLayout.createTab({tab:newTab});
	mainTabLayout.showTab(tmpTabIndex);
	
	newTab = new Tab();
	newTab.tab_header_name = "日历管理";
	newTab.tab_body_url = "calendarmgr.jsp";
	newTab.tab_body_url_para = "";
	tmpTabIndex = mainTabLayout.createTab({tab:newTab});
	
	newTab = new Tab();
	newTab.tab_header_name = "协同管理";
	newTab.tab_body_url = "cooperatemgr.jsp";
	newTab.tab_body_url_para = "";
	tmpTabIndex = mainTabLayout.createTab({tab:newTab});
	
});

//创建Tab click
function clickTab(event)
{
	var tab_index = event.data.tab_index;
	mainTabLayout.showTab(tab_index);
}
</script>

<body margin="0">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td id="tab_container" valign="top"></td>
</tr>
</table>
</body>