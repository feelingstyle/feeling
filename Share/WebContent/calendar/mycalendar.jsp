<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.operator.*"%>
<%@ page import="java.util.*"%>

<style type="text/css">
@import "../css/main.css";
@import "../css/zTreeStyle.css";
</style> 

<script src="../js/jsonrpc.js"></script>
<script src="../js/jquery-1.9.1.js"></script>
<script src="../js/jquery.corners.js"></script>
<script src="../js/jquery.ztree.all-3.5.js"></script>
<script src="../js/json2.js"></script>
<script src="../js/widget/calendar.js"></script>
<script src="../js/widget/dialog.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="opobj" scope="session" class="com.operator.Operator"/>
<jsp:useBean id="calendar" scope="session" class="com.widget.Calendar"/>
<jsp:useBean id="calendarmgr" scope="session" class="com.widget.CalendarManager"/>
<jsp:useBean id="calendaritem" scope="session" class="com.widget.CalendarItem"/>
<jsp:useBean id="calendaritemmgr" scope="session" class="com.widget.CalendarItemManager"/>
<jsp:useBean id="calendarlink" scope="session" class="com.widget.CalendarLink"/>
<jsp:useBean id="calendarlinkmgr" scope="session" class="com.widget.CalendarLinkManager"/>
<jsp:useBean id="comm" scope="session" class="com.util.Comm"/>

<%
JSONRPCBridge.registerObject("opobj",opobj);
JSONRPCBridge.registerObject("calendar",calendar);
JSONRPCBridge.registerObject("calendarmgr",calendarmgr);
JSONRPCBridge.registerObject("calendaritem",calendaritem);
JSONRPCBridge.registerObject("calendaritemmgr",calendaritemmgr);
JSONRPCBridge.registerObject("calendarlink",calendarlink);
JSONRPCBridge.registerObject("calendarlinkmgr",calendarlinkmgr);
JSONRPCBridge.registerObject("comm",comm);
%>

<script >
var jsonrpc;
var calendar;
var calendarBuffer = {};
var op;
var calendarheaderDialog;
var calendaritemDialog;

//calendar tree
var calendarTree;
var calendarTreeSetting = {
		check: {
			enable: true,
			nocheckInherit: false
		},
	    callback : {
	    	onClick: onClick,
	    	onCheck: onCheck
		}
	};
	
var calendarTreeNodes = [
                         {name:"本地日历",children:[],isParent:true,open:true,type:0,nocheck:true},
                         {name:"协同日历",children:[],isParent:true,open:true,type:0,nocheck:true},
                         {name:"工作事务",children:[],isParent:true,open:true,type:0,nocheck:true}
];

function showCalendarTree(op)
{
	calendarTreeNodes[0]["children"] = [];
	calendarTreeNodes[1]["children"] = [];
	calendarTreeNodes[2]["children"] = [];
	
	var calendarlist = jsonrpc.calendarmgr.getList(op.loginname,op.authfrom,1);
	if (calendarlist)
	{
		for (var i=0;i<calendarlist.list.length;i++)
		{
			calendarlist.list[i]["name"] = calendarlist.list[i]["calendarname"];
			switch (calendarlist.list[i]["type"])
			{
				case 1000:	//我的日历
							calendarlist.list[i]["icon"] = "../css/img/diy/3.png";
							calendarlist.list[i]["checked"] = true;
							calendarTreeNodes[0]["children"].push(calendarlist.list[i]);
							break;
				case 2000: 	//我的消息
							calendarlist.list[i]["icon"] = "../css/img/diy/7.png";
							calendarTreeNodes[2]["children"].push(calendarlist.list[i]);
							calendarlist.list[i]["nocheck"] = true;
							break;
				case 2001: 	//我的任务
							calendarlist.list[i]["icon"] = "../css/img/diy/8.png";
							calendarTreeNodes[2]["children"].push(calendarlist.list[i]);
							calendarlist.list[i]["nocheck"] = true;
							break;
				default:	calendarlist.list[i]["icon"] = "../css/img/diy/4.png";
							calendarTreeNodes[0]["children"].push(calendarlist.list[i]);
							break;
			}
			
		}
	}
	
	calendarlist = jsonrpc.calendarmgr.getLinkedList(op.loginname,op.authfrom,1);
	if (calendarlist)
	{
		for (var i=0;i<calendarlist.list.length;i++)
		{
			calendarlist.list[i]["name"] = calendarlist.list[i]["calendarname"]+"("+calendarlist.list[i]["loginname"]+")";
			calendarlist.list[i]["icon"] = "../pic/calendar_link.png";
			calendarlist.list[i]["nocheck"] = true;
			calendarlist.list[i]["type"] = 9000;
			calendarTreeNodes[1]["children"].push(calendarlist.list[i]);
		}
	}
	
	$.fn.zTree.init($("#calendar_tree"), calendarTreeSetting, calendarTreeNodes);
}

$(document).ready(function () { 
	jsonrpc = new JSONRpcClient("/Share/JSON-RPC");

	op = jsonrpc.opobj.checkSession("opsession");
	if (!op)
	{
		alert("Session已过期");
		top.location.href = "../index.jsp";
		return;
	}
	
	showCalendarTree(op);
	
	calendarheaderDialog = new Dialog("calendarheader_dlg");
	calendarheaderDialog.init({
		title:"日历功能",
		left:150,
		top:50,
		width:800,
		height:400,
		url:"calendarfunc.jsp"
	});
	
	calendaritemDialog = new Dialog("calendaritem_dlg");
	calendaritemDialog.init({
		title:"日历详情",
		left:150,
		top:50,
		width:650,
		height:380,
		url:"calendaritem.jsp"
	});
	
	calendar = new Calendar($("#calendar_container"));
	calendar.init({
		width:880,
		height:440,
		afterInit:afterInit,
		onCellClick:onCellClick,
		onItemClick:onItemClick
	});
	
	var tmpList = jsonrpc.calendarmgr.getList(op.loginname,op.authfrom,1);
	for (var i=0;i<tmpList.list.length;i++)
	{
		calendarBuffer[tmpList.list[i]["id"]] = tmpList.list[i];
	}
	
});

//calendar func
function afterInit(year,month)
{
	if (month+1<10)
	{
		month = "0" + (month+1);
	}

	var treeObj = $.fn.zTree.getZTreeObj("calendar_tree");
	var nodesChecked = treeObj.getCheckedNodes(true);
	var calendaridlist = [];
	var itemtype = 2;
	if (nodesChecked && nodesChecked.length>0)
	{//多选本地日历
		for (var i=0;i<nodesChecked.length;i++)
		{
			if (nodesChecked[i]["id"])
			{
				calendaridlist.push(nodesChecked[i]["id"]);
			}
		}
		
		if (calendaridlist.length<=0)
			return;
		
	}else{//功能日历
		var nodesSelected = treeObj.getSelectedNodes();
		if (nodesSelected && nodesSelected.length>0)
		{
			switch (nodesSelected[0].type)
			{
				case 1000:
				case 1001:	//本地日历
							break;
				default:	calendaridlist.push(nodesSelected[0]["id"]);
							break;
			}
			
		}else{
			return;
		}
		
		itemtype = 9;
	}
	
	calendar.status("<img src='../pic/loading.gif' align='absmiddle'>&nbsp;loading ...");

	jsonrpc.calendaritemmgr.getList(itemtype,function(result,exception,profile,param1){
		if(exception){
			calendar.status(exception.message);
	    }else{
	    	afterInitRes(result,param1);
	    }
	},JSON.stringify(calendaridlist),year,month);
	
}

function afterInitRes(result,itemtype)
{
	if (result && result.list.length>0)
	{
		calendar.status("&nbsp;");
		var itemlist = result.list;
		for (var i=0;i<itemlist.length;i++)
		{
			var item = itemlist[i];
			item["showname"] = item["title"];
			item["itemtype"] = itemtype;
			
			var calendarcssname = "calendaritem";
			if (calendarBuffer && calendarBuffer[item["calendarid"]]["cssname"])
				calendarcssname = calendarBuffer[item["calendarid"]]["cssname"];
			
			if (calendar)
			{
				calendar.addItem(item["date"],calendarcssname,item);
			}
		}
	}else{
		calendar.status("该月没有日历信息");
	}
}

function onCellClick(year,month,date,items)
{
	if (month+1<10)
	{
		month = "0" + (month+1);
	}
	
	if (date<10)
	{
		date = "0" + date;
	}
	
	calendarheaderDialog.setUrlPara("year="+year+"&month="+month+"&date="+date);
	calendarheaderDialog.open(true);
}

function onItemClick(year,month,date,item)
{
	if (month+1<10)
	{
		month = "0" + (month+1);
	}
	
	if (date<10)
	{
		date = "0" + date;
	}
	
	calendaritemDialog.setUrlPara("year="+year+"&month="+month+"&date="+date+"&itemtype="+
			item.itemtype+"&calendarid="+item.calendarid+"&itemid="+item.id);
	calendaritemDialog.open(true);
}

//tree func
function onClick(event, treeId, treeNode){
	var treeObj = $.fn.zTree.getZTreeObj("calendar_tree");
	
	switch (treeNode.type)
	{
		case 1000:
		case 1001:	//本地日历
					treeObj.checkNode(treeNode,null,false,true);
					break;
		default:    //其他显示
					treeObj.checkAllNodes(false);
					calendar.init({});
					break;
	}
}

function onCheck(event, treeId, treeNode) {
	calendar.init({});
}


</script>

<table width="100%" border="0">
<tr>
	<td width="20%" valign="top" style="border-left:1 dotted #f2f2f2;height:420">
	<ul id="calendar_tree" class="ztree" style="width:100%;height:100%;overflow:auto"></ul>
	</td>
	<td width="80%" valign="top" align="center" id="calendar_container"></td>
</tr>
</table>
