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
<script src="../js/widget/combobox.js"></script>

<jsp:useBean id="JSONRPCBridge" scope="session" class="org.jabsorb.JSONRPCBridge"/>
<jsp:useBean id="opobj" scope="session" class="com.operator.Operator"/>
<jsp:useBean id="opmgr" scope="session" class="com.operator.OperatorManager"/>
<jsp:useBean id="calendar" scope="session" class="com.widget.Calendar"/>
<jsp:useBean id="calendarmgr" scope="session" class="com.widget.CalendarManager"/>
<jsp:useBean id="commdata" scope="session" class="com.util.CommonData"/>
<jsp:useBean id="commdatamgr" scope="session" class="com.util.CommonDataManager"/>
<jsp:useBean id="calendarlink" scope="session" class="com.widget.CalendarLink"/>
<jsp:useBean id="calendarlinkmgr" scope="session" class="com.widget.CalendarLinkManager"/>

<%
JSONRPCBridge.registerObject("opobj",opobj);
JSONRPCBridge.registerObject("opmgr",opmgr);
JSONRPCBridge.registerObject("calendar",calendar);
JSONRPCBridge.registerObject("calendarmgr",calendarmgr);
JSONRPCBridge.registerObject("commdata",commdata);
JSONRPCBridge.registerObject("commdatamgr",commdatamgr);
JSONRPCBridge.registerObject("calendarlink",calendarlink);
JSONRPCBridge.registerObject("calendarlinkmgr",calendarlinkmgr);
%>

<script>
var jsonrpc;
var op;
var opcombobox;
var treeNodeSelected = {};
var calendarlinkops = [];
var authfrommap;
var requeststatusmap;

//calendar tree
var calendarTree;
var calendarTreeSetting = {
		edit: {
			enable: true,
			showRemoveBtn: false,
			showRenameBtn: false
		},
	    callback : {
	    	beforeRename: beforeRename,
	    	beforeRemove: beforeRemove,
	    	onRemove: onRemove,
	    	onClick: onClick
		}
	};
	
var calendarTreeNodes = [
                         {name:"日历",children:[],isParent:true,open:true,type:0}
                        ];

//normal func
function isLinkExist(loginname,authname,calendarid)
{
	var tmplink = jsonrpc.calendarlink.get(loginname,authname,calendarid);
	if (tmplink)
	{
		return true;
	}
	
	return false;
}

function setPermission(obj)
{
	var checkboxid = obj.id.split("_");
	var p = checkboxid[1];
	var clid = checkboxid[2];
	if (obj.checked)
	{
		jsonrpc.calendarlinkmgr.setCalendarLinkPermission(clid,p,1);
	}else{
		jsonrpc.calendarlinkmgr.setCalendarLinkPermission(clid,p,0);
	}
}

function showLinkList(calendarid)
{
	$("#coopoplist").html("");
	calendarlinkops = [];
	var tmplist = jsonrpc.calendarlinkmgr.getList(calendarid);
	if (tmplist && tmplist.list)
	{
		calendarlinkops = tmplist.list;
		for (var i=0;i<tmplist.list.length;i++)
		{
			var readchecked = "checked";
			var editchecked = "checked";
			var removechecked = "checked";
			if (tmplist.list[i].readable!=1)
			{
				readchecked = "";
			}
			if (tmplist.list[i].editable!=1)
			{
				editchecked = "";
			}
			if (tmplist.list[i].removable!=1)
			{
				removechecked = "";
			}
			
			var str = "<tr>";
			str += "<td width='15%' style='border-bottom:1 solid #f1f1f1'>"+tmplist.list[i].loginname+"</td>";
			str += "<td width='15%' style='border-bottom:1 solid #f1f1f1'>"+authfrommap.map[tmplist.list[i].authfrom].value+"</td>";
			str += "<td width='50%' style='border-bottom:1 solid #f1f1f1'>"+
						"<input type='checkbox' "+readchecked+" id='chk_read_"+tmplist.list[i].id+"' onclick='setPermission(this)'>读&nbsp;"+
						"<input type='checkbox' "+editchecked+" id='chk_edit_"+tmplist.list[i].id+"' onclick='setPermission(this)'>写&nbsp;"+
						"<input type='checkbox' "+removechecked+" id='chk_remove_"+tmplist.list[i].id+"' onclick='setPermission(this)'>删&nbsp;</td>";
			str += "<td width='15%' style='border-bottom:1 solid #f1f1f1'>"+requeststatusmap.map[tmplist.list[i].requeststatus].value+"</td>";
			str += "</tr>";
			$("#coopoplist").append(str);
		}
	}	
}

function showCalendarTree(op)
{
	var calendarlist = jsonrpc.calendarmgr.getList(op.loginname,op.authfrom,1);
	if (calendarlist)
	{
		calendarTreeNodes[0]["children"] = [];
		for (var i=0;i<calendarlist.list.length;i++)
		{
			if (calendarlist.list[i]["type"]>=1000 && calendarlist.list[i]["type"]<2000)
			{//日历
				calendarlist.list[i]["name"] = calendarlist.list[i]["calendarname"];
				switch (calendarlist.list[i]["type"])
				{
					case 1000:	calendarlist.list[i]["icon"] = "../css/img/diy/3.png";
								break;
					default:	calendarlist.list[i]["icon"] = "../css/img/diy/2.png";
								break;
				}
	
				calendarTreeNodes[0]["children"].push(calendarlist.list[i]);
			}
				
		}
		
		$.fn.zTree.init($("#calendar_tree"), calendarTreeSetting, calendarTreeNodes);
	}
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
	
	authfrommap = jsonrpc.commdatamgr.getMap("authfrom","BASIC");
	requeststatusmap = jsonrpc.commdatamgr.getMap("requeststatus","SHARE14_OP_CALENDAR_LINK");
	
	showCalendarTree(op);
	
	$("#calendar_add").click(function(){
		var zTree = $.fn.zTree.getZTreeObj("calendar_tree");
		var nodes = zTree.getNodes();
		var treeNode = nodes[0];
		if (treeNode) {
			var tmpNode = {};
			tmpNode["name"] = "新增日历";
			treeNode = zTree.addNodes(treeNode,tmpNode);
		} 

		if (treeNode) {
			zTree.editName(treeNode[0]);
		} 
	});
	
	$("#calendar_edit").click(function(){
		var zTree = $.fn.zTree.getZTreeObj("calendar_tree");
		var nodes = zTree.getSelectedNodes();
		var treeNode = nodes[0];
		if (nodes.length == 0) {
			alert("请先选择一个节点");
			return;
		}
		
		if (treeNode.type != 1001)
		{
			alert("此节点无法编辑");
			return;
		}
		
		zTree.editName(treeNode);
	});
	
	$("#calendar_remove").click(function(){
		var zTree = $.fn.zTree.getZTreeObj("calendar_tree");
		var nodes = zTree.getSelectedNodes();
		var treeNode = nodes[0];
		if (nodes.length == 0) {
			alert("请先选择一个节点");
			return;
		}
		
		if (treeNode.type !=1001)
		{
			alert("此节点无法删除");
			return;
		}
		
		zTree.removeNode(treeNode, true);
	});
	
	$("#setting_save").click(function(){
		var zTree = $.fn.zTree.getZTreeObj("calendar_tree");
		var nodes = zTree.getSelectedNodes();
		var treeNode = nodes[0];
		if (nodes.length == 0) {
			alert("请先选择一个节点");
			return;
		}
		
		var result = 0;
		var radios = $("input[type='radio'][name='itemclass']:checked");
		if (radios && radios.length==1)
		{
			result = jsonrpc.calendarmgr.editCalendarCss(op.loginname,op.authfrom,treeNode["name"],radios[0].value);
		}else{
			alert("请选择一个日历项样式");
			return;
		}
		
		if (result>0)
		{
			showCalendarTree(op);
		}else{
			alert("日历项样式未保存成功");
		}
	});
	
	
	opcombobox = new ComboBox($("#cooperateselect"));
	opcombobox.init({
		width:200,
		keyshowed:"displayName",
		keyselected:"displayName",
		getPairData:getPairData,
		onItemReturn:onItemReturn,
		onItemClick:onItemClick
	});
	
});

//tree func
function beforeRename(treeId, treeNode, newName) {	
	var zTree = $.fn.zTree.getZTreeObj("calendar_tree");
	if (newName.length == 0) {
		alert("节点名称不能为空.");
		setTimeout(function(){zTree.editName(treeNode);}, 10);
		return false;
	}
	
	var result = 0;
	if (!treeNode["id"])
	{
		result=jsonrpc.calendarmgr.createCalendar(op.loginname,op.authfrom,newName,"",1001,1);	
	}else{
		result=jsonrpc.calendarmgr.editCalendar(treeNode["id"],treeNode["loginname"],treeNode["authfrom"],newName,
				treeNode["cssname"],treeNode["type"],treeNode["status"]);
	}
	
	if (result==1)
	{

	}else{
		alert("更新失败");
		setTimeout(function(){zTree.editName(treeNode);}, 10);
		return false;
	}
	
	return true;
}

function beforeRemove(treeId, treeNode) {
	if (confirm("确认删除 节点 (" + treeNode.name + ")吗？"))
	{
		var result = jsonrpc.calendarmgr.removeCalendar(op.loginname,op.authfrom,treeNode.name);
		if (result>=1)
		{

		}else{
			alert("删除失败");
			return false;
		}
	}else{
		return false;
	}
	
	return true;
}
function onRemove(e, treeId, treeNode) {

}

function onClick(event, treeId, treeNode){
	treeNodeSelected = {};
	
	if (treeNode.type!=0)
	{
		treeNodeSelected = treeNode;
		var radios = $("input[type='radio'][name='itemclass']");
		if (!treeNode["cssname"] || treeNode["cssname"].length<=0)
		{
			treeNode["cssname"] = "calendaritem";
		}
		for (var i=0;i<radios.length;i++)
		{
			if (radios[i].value==treeNode["cssname"])
			{
				radios[i].checked=true;
			}
		}
		
		showLinkList(treeNode.id);
	}
}

//combobox func
function getPairData(filter)
{
	var ops = jsonrpc.opmgr.get(filter);
	if (ops && ops.list)
	{
		return ops.list;
	}
	return null;
}

function onItemReturn(items)
{

}

function onItemClick(itemindex,items)
{
	if (treeNodeSelected && !jQuery.isEmptyObject(treeNodeSelected))
	{
		if (items && items.length>0)
		{
			var item = items[itemindex];
			if (item)
			{
				if (isLinkExist(item.loginname,item.authfrom,treeNodeSelected.id))
				{
					
				}else{
					var result = jsonrpc.calendarlinkmgr.createCalendarLink(treeNodeSelected.id,item.loginname,item.authfrom,1,0,0,0);
					if (result>0)
					{
						showLinkList(treeNodeSelected.id);
					}
				}
			}else{
				alert("解析错误");
				return;
			}
		}else{
			alert("选择异常");
		}
	}else{
		alert("请选择日历");
		return;
	}

}
</script>

<table width="100%" border="0">
<tr>
	<td width="18%" valign="top" style="height:470;border-right:1 solid #f1f1f1">
	<table width="100%" border="0" cellpadding='0'>
	<tr>
		<td width="20%" id="calendar_add" class="calendarbtn">+</td>
		<td width="40%" id="calendar_edit" class="calendarbtn">编辑</td>
		<td width="40%" id="calendar_remove" class="calendarbtn">删除</td>
	</tr>
	</table>
	<ul id="calendar_tree" class="ztree" style="width:100%;height:100%;overflow:auto"></ul>
	</td>
	<td width="82%" valign="top">
	<table width="100%" border="0">
	<tr>
		<td colspan="2" class="line120" style="color:#6A6AFF">日历项背景</td>
	</tr>
	<tr>
		<td width="10%"><div class="calendaritem"><input type="radio" name="itemclass" value="calendaritem">默认</div></td>
		<td width="10%"><div class="calendaritem_orange"><input type="radio" name="itemclass" value="calendaritem_orange">&nbsp;橙1</div></td>
		<td width="10%"><div class="calendaritem_green"><input type="radio" name="itemclass" value="calendaritem_green">&nbsp;绿1</div></td>
		<td width="10%"><div class="calendaritem_blue"><input type="radio" name="itemclass" value="calendaritem_blue">&nbsp;蓝1</div></td>
		<td width="10%"><div class="calendaritem_pink"><input type="radio" name="itemclass" value="calendaritem_pink">&nbsp;粉1</div></td>
		<td width="10%"><div class="calendaritem_grey"><input type="radio" name="itemclass" value="calendaritem_grey">&nbsp;灰1</div></td>
		<td>&nbsp;</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td colspan="2" class="line120" style="color:#6A6AFF">日历协同</td>
	</tr>
	<tr>
		<td colspan="1">添加协作人</td>
		<td colspan="5" style="color:#ff9900">
		<input type="text" id="cooperateselect" size="30">&nbsp;(输入登录名、姓名、部门搜索)
		</td>
		<td id="act_status">&nbsp;</td>
	</tr>
	<tr>
		<td colspan="8" valign="top" style="border:2 solid #9999ff">
			<table width = "100%" border="0" cellspacing="0">
			<tr>
				<td width="15%" style="border-bottom:1 solid #9999ff;background:#f5f5f5">协作人</td>
				<td width="15%" style="border-bottom:1 solid #9999ff;background:#f5f5f5">来源</td>
				<td width="50%" style="border-bottom:1 solid #9999ff;background:#f5f5f5">权限</td>
				<td width="15%" style="border-bottom:1 solid #9999ff;background:#f5f5f5">状态</td>
			</tr>	
			<tr>
				<td colspan="5">
				<div style="width:100%;height:250;overflow:auto">
				<table width = "100%" border="0" id="coopoplist">
				
				</table>
				</div>
				</td>
			</tr>
			</table>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	</table>
	<table width="100%" border="0">
	<tr>
		<td></td>
	</tr>
	<tr>
		<td ><div class="bluebtn" id="setting_save" style="width:60;text-align:center">保存</div></td>
	</tr>
	</table>
	</td>
</tr>
</table>