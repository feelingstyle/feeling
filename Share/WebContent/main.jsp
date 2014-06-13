<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.operator.*"%>
<%@ page import="java.util.*"%>

<style type="text/css">
@import "css/main.css";
</style> 

<script src="js/jsonrpc.js"></script>
<script src="js/jquery-1.9.1.js"></script>
<script src="js/jquery.corners.js"></script>
<script src="js/jquery.ztree-2.6.js"></script>
<script src="js/json2.js"></script>
<script src="js/widget/mainmenu.js"></script>
<script src="js/widget/tab.js"></script>
<script src="js/widget/tablayout.js"></script>
<script src="js/widget/combobox.js"></script>

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
var filecombobox;

$(document).ready(function () { 
	jsonrpc = new JSONRpcClient("/Share/JSON-RPC");

	checkLogin();

});

//检查登录情况
function checkLogin()
{
	var op = jsonrpc.opobj.checkSession("opsession");
	if (!op)
	{
		alert("Session已过期");
		window.location.href = "index.jsp";
		return;
	}else{
		var str = "<table width='100%' border='0'>";
		str += "<tr>";
		str += "<td colspan='5' align='right' style='background:#f8f8ff;border-bottom:1 solid f3f3f3'>"+op.displayName+
				"&nbsp;&nbsp;<span style='color:#9999ff;width:80;text-align:center;cursor:hand' onclick='logout()'>退出</span>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;</td>";
		str += "</tr>";
		str += "<tr>";
		str += "<td width='15%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
				"<span style='font-size:22;color:#9999ff'>Share&nbsp;V</span></td>";
		str += "<td width='45%' id='mainmenu'></td>";
		str += "<td width='40%'>&nbsp;</td>";
		str += "</tr></table>";
		$("#opinfo").html(str);
		
		//获取main menu data
		var menu = jsonrpc.menuobj.getList(0,op.authfrom);
		if (menu)
		{
			for (var i=0;i<menu.list.length;i++)
			{
				//获取sub menu data
				var submenu = jsonrpc.menuobj.getList(menu.list[i].id,op.authfrom);
				if (submenu)
				{
					menu.list[i]["submenu"] = submenu;
				}
			}
			
			var menu_widget = new MainMenu($("#mainmenu"));
			menu_widget.create({
				submenu_top:27,
				submenu_left:0,
				menudata:menu,
				mainmenuClick:clickMainMenu,
				submenuClick:clickSubMenu,
				mainmenuClass:"mainmenu_homepage",
				mainmenuClassOnMouseOver:"mainmenu_mouseover_homepage",
				submenuClass : "submenu_homepage",
				submenuClassOnMouseOver : "submenu_mouseover_homepage"
			});
		}
		
		mainTabLayout = new TabLayout($("#tab_container"));
		mainTabLayout.init({
			tabClick:clickTab,
			tabDblClick:dblclickTab,
			tab_body_height:520
		});
		
	}
}


//创建Main Menu点击事件
function clickMainMenu(event)
{
	var menuid = event.data.id;
	var url = event.data.url;
	if (!url || url.length<=0)
		return;
	
	var newTab = new Tab();
	newTab.tab_header_name = event.target.innerText;
	newTab.tab_body_url = "dispatch.jsp";
	newTab.tab_body_url_para = "id="+menuid;
	var tmpTabIndex = mainTabLayout.createTab({tab:newTab});
	mainTabLayout.showTab(tmpTabIndex);
}

function logout()
{
	jsonrpc.comm.removeSession("opsession");	
	window.location.href = "index.jsp";
}

//创建Sub Menu点击事件
function clickSubMenu(event)
{		
	var newTab = new Tab();
	newTab.tab_header_name = event.target.innerText;
	newTab.tab_body_url = "dispatch.jsp";
	var idlist = event.target.id.split("_");
	newTab.tab_body_url_para = "id="+idlist[idlist.length-1];
	var tmpTabIndex = mainTabLayout.createTab({tab:newTab});
	mainTabLayout.showTab(tmpTabIndex);
}

//创建Tab click
function clickTab(event)
{
	var tab_index = event.data.tab_index;
	mainTabLayout.showTab(tab_index);
}

//创建Tab dblclick
function dblclickTab(event)
{
	var tab_index = event.data.tab_index;
	mainTabLayout.removeTab(tab_index);	
}

</script>

<table width="100%" border="0">
<tr>
	<td id="opinfo" style="border-bottom:1 solid f3f3f3">&nbsp;</td>
</tr>
<tr>
	<td id="tab_container" valign="top"></td>
</tr>
</table>
<IFRAME ID="frmRefresh" width="0" height="0" FRAMEBORDER="0" SCROLLING="auto" SRC="refresh.jsp"></IFRAME>