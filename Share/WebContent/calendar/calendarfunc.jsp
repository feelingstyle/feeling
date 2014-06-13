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
<script src="../js/jquery.ztree.all-3.5.js"></script>
<script src="../js/json2.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="opobj" scope="session" class="com.operator.Operator"/>
<jsp:useBean id="calendar" scope="session" class="com.widget.Calendar"/>
<jsp:useBean id="calendarmgr" scope="session" class="com.widget.CalendarManager"/>
<jsp:useBean id="calendaritem" scope="session" class="com.widget.CalendarItem"/>
<jsp:useBean id="calendaritemmgr" scope="session" class="com.widget.CalendarItemManager"/>
<jsp:useBean id="comm" scope="session" class="com.util.Comm"/>

<%
JSONRPCBridge.registerObject("opobj",opobj);
JSONRPCBridge.registerObject("calendar",calendar);
JSONRPCBridge.registerObject("calendarmgr",calendarmgr);
JSONRPCBridge.registerObject("calendaritem",calendaritem);
JSONRPCBridge.registerObject("calendaritemmgr",calendaritemmgr);
JSONRPCBridge.registerObject("comm",comm);

String year = request.getParameter("year");
String month = request.getParameter("month");
String date = request.getParameter("date");
if (date==null || month==null || year==null)
{
	out.println("参数缺失");
	return;
}
%>

<script >
var jsonrpc;

//calendar func tree
var calendarFuncTree;
var calendarFuncTreeSetting = {
	    callback : {
	    	onClick: onClick
		}
	};
	
var calendarFuncTreeNodes = [
                         {name:"添加日历项",id:1,icon:"../css/img/diy/4.png"},
                         {name:"发布任务",id:2,icon:"../css/img/diy/8.png"}
];

function onClick(event, treeId, treeNode){
	$(".divhandle").hide();
	
	switch (treeNode.id)
	{
		case 1: //日历
				$("#calendar_item_add").show();
				break;
		case 2: //任务
				$("#task_item_add").show();
				break;
	}
}

$(document).ready(function () { 
	jsonrpc = new JSONRpcClient("/Share/JSON-RPC");

	var treeObj = parent.$.fn.zTree.getZTreeObj("calendar_tree");
	var nodes = treeObj.getCheckedNodes(true);
	if (nodes)
	{
		for (var i=0;i<nodes.length;i++)
		{
			switch (nodes[i]["type"])
			{
				case 1000:
				case 1001:	$("#item2calendar").append("<option value='"+nodes[i].id+"'>"+nodes[i].name+"</option>");
							break;
			}
			
		}
	}
	
	$.fn.zTree.init($("#calendar_func_tree"), calendarFuncTreeSetting, calendarFuncTreeNodes);
	
	$("#calendar_item_add_btn").click(function(){	
		var itemtitle = $("#itemtitle").val();
		var itemnote = $("#itemnote").val();
		var item2calendar = $("#item2calendar").val();
		
		if (itemtitle.length<=0 || !item2calendar || item2calendar.length<=0)
		{
			alert("缺少日历参数");
			return;
		}
		
		$("#func_status").html("<img src='../pic/loading.gif' align='absmiddle'>&nbsp;loading ...");
		
		jsonrpc.calendaritemmgr.createCalendarItem(<%=date%>,itemtitle,item2calendar,function(result,exception,profile,param1,param2,param3){
			if(exception){
				$("#func_status").html(exception.message);
		    }else{
		    	createCalendarItemRes(result,param1,param2,param3);
		    }
		},item2calendar,itemtitle,itemnote,"","","",1,"<%=year%>","<%=month%>","<%=date%>","<%=(year+"-"+month+"-"+date)%>");
	});
	
});

function createCalendarItemRes(result,date,title,calendarid)
{
	if (result && result>0)
	{
		$("#itemtitle").val("");
		$("#itemnote").val("");
		$("#func_status").html("日历项创建成功");
		
		var tmpItem = {};
		tmpItem["id"] = result;
		tmpItem["showname"] = title;

		var calendarcssname = "calendaritem";
		if (parent.calendarBuffer && parent.calendarBuffer[calendarid]["cssname"])
			calendarcssname = parent.calendarBuffer[calendarid]["cssname"];
		
		if (parent.calendar)
		{
			parent.calendar.addItem(date,calendarcssname,tmpItem);
		}
		
	}else{
		$("#func_status").html("日历项创建失败");
	}
}

</script>

<table width="100%" border="0">
<tr>
	<td width="20%" valign="top">
	<ul id="calendar_func_tree" class="ztree" style="width:100%;height:100%;overflow:auto"></ul>
	</td>
	<td width="80%" valign="top" height="330">
		<div style="width:100%;height:100%;display:block" id="calendar_item_add" class="divhandle">
		<table width="100%">
		<tr>
			<td class='line120' style='color:#6A6AFF'>时间</td>
			<td><%=(year+"-"+month+"-"+date)%></td>
		</tr>
		<tr>
			<td width="25%" class='line120' style='color:#6A6AFF'>日历项</td>
			<td width="75%">
			<input type="text" class="inputbox" id="itemtitle" size="60" maxlength="100">
			</td>
		</tr>
		<tr>
			<td colspan="2" class='line120' style='color:#6A6AFF'>日历项备注</td>
		</tr>
		<tr>
			<td colspan="2">
			<textarea class="inputbox" id="itemnote" cols="85" rows="12"></textarea>
			</td>
		</tr>
		<tr>
			<td class='line120' style='color:#6A6AFF'>添加到日历</td>
			<td>
			<select id="item2calendar">
			</select>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<div id="func_status" style="color:#ff9911;">&nbsp;</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
			<div class="bluebtn" id="calendar_item_add_btn" style="width:80;text-align:center;">提交</div>
			</td>
		</tr>
		</table>
		</div>
		<div style="width:100%;height:100%;display:none" id="task_item_add" class="divhandle">
		&nbsp;
		</div>
	</td>
	
</tr>
</table>
