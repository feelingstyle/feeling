<%@ page pageEncoding="gb2312"%>
<%@ page contentType="text/html; charset=gb2312"%>
<%@ page buffer="32kb"%>

<%@ page import="com.operator.*"%>
<%@ page import="java.util.*"%>
<%@ page import="com.widget.*"%>

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

String year = request.getParameter("year");
String month = request.getParameter("month");
String date = request.getParameter("date");

int itemtype = 0;
long calendarid = 0;
long itemid = 0;

try {
	itemtype = Integer.parseInt(request.getParameter("itemtype"));
	calendarid = Long.parseLong(request.getParameter("calendarid"));
	itemid = Long.parseLong(request.getParameter("itemid"));	
}catch (Exception e)
{
	out.println("��������");
	return;
}

if (date==null || month==null || year==null)
{
	out.println("����ȱʧ");
	return;
}

CalendarItem ci = CalendarItem.get(itemid);
if (ci==null)
{
	out.println("��ϸ��Ϣ��ȡʧ��");
	return;
}

%>

<script>
var jsonrpc;
var readable = 1;
var editable = 1;
var removable = 1;

$(document).ready(function () { 
	jsonrpc = new JSONRpcClient("/Share/JSON-RPC");

	if (<%=itemtype%>==9)
	{
		var calendarlink = jsonrpc.calendarlink.get(parent.op.loginname,parent.op.authfrom,<%=calendarid%>);
		if (calendarlink)
		{
			readable = calendarlink.readable;
			editable = calendarlink.editable;
			removable = calendarlink.removable;
		}
	}
	
	if (readable==0)
	{
		alert("����Ȩ�鿴");
		parent.calendaritemDialog.close();
		return;
	}
	
	$("#itemtitle").val("<%=ci.getTitle()%>");
	$("#itemnote").val("<%=(ci.getNote()==null)?"":ci.getNote()%>");
	
	$("#calendar_item_save_btn").click(function(){	
		if (editable==1)
		{
			var itemtitle = $("#itemtitle").val();
			var itemnote = $("#itemnote").val();
			
			if (itemtitle.length<=0)
			{
				alert("���������");
				return;
			}
			
			$("#func_status").html("<img src='../pic/loading.gif' align='absmiddle'>&nbsp;loading ...");
			
			jsonrpc.calendaritemmgr.editCalendarItem(function(result,exception,profile){
				if(exception){
					$("#func_status").html(exception.message);
			    }else{
			    	editCalendarItemRes(result);
			    }
			},<%=itemid%>,<%=calendarid%>,itemtitle,itemnote,"","","",1,"<%=year%>","<%=month%>","<%=date%>","<%=(year+"-"+month+"-"+date)%>");
		}else{
			alert("����Ȩ�༭");
			return;
		}
		
	});
	
	$("#calendar_item_del_btn").click(function(){	
		if (removable==1)
		{
			if (confirm("ȷ��Ҫɾ����һ��������"))
			{
				$("#func_status").html("<img src='../pic/loading.gif' align='absmiddle'>&nbsp;loading ...");
				jsonrpc.calendaritem.remove(function(result,exception,profile){
					if(exception){
						$("#func_status").html(exception.message);
				    }else{
				    	removeCalendarItemRes(result);
				    }
				},<%=itemid%>);
			}else{
				return;
			}
		}else{
			alert("����Ȩɾ��");
			return;
		}
		

	});
	
});

function editCalendarItemRes(result)
{
	if (result && result>0)
	{
		$("#func_status").html("���³ɹ�");
		parent.calendaritemDialog.close();
		parent.calendar.init({});
	}else{
		$("#func_status").html("����ʧ��");
	}
}

function removeCalendarItemRes(result)
{
	if (result && result>0)
	{
		$("#func_status").html("ɾ���ɹ�");
		parent.calendaritemDialog.close();
		parent.calendar.init({});
	}else{
		$("#func_status").html("ɾ��ʧ��");
	}	
}
</script>

<table width="100%">
<tr>
	<td class='line120' style='color:#6A6AFF'>ʱ��</td>
	<td><%=(year+"-"+month+"-"+date)%></td>
</tr>
<tr>
	<td width="25%" class='line120' style='color:#6A6AFF'>������</td>
	<td width="75%">
	<input type="text" class="inputbox" id="itemtitle" size="60" maxlength="100">
	</td>
</tr>
<tr>
	<td colspan="2" class='line120' style='color:#6A6AFF'>�����ע</td>
</tr>
<tr>
	<td colspan="2">
	<textarea class="inputbox" id="itemnote" cols="85" rows="12"></textarea>
	</td>
</tr>
<tr>
	<td colspan="2">
	<div id="func_status" style="color:#ff9911;">&nbsp;</div>
	</td>
</tr>
<tr>
	<td colspan="2">
	<div class="bluebtn" id="calendar_item_save_btn" style="width:80;text-align:center;float:left;">����</div>
	<div class="bluebtn" id="calendar_item_del_btn" style="width:80;text-align:center;float:left;">ɾ��</div>
	</td>
</tr>
</table>