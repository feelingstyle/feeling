function Calendar(obj)
{
	this.container = obj;
	this.items;
	this.itemhandleid = 0;
	this.year = (new Date()).getFullYear();
	this.month = (new Date()).getMonth();
	this.date = (new Date()).getDate();
	this.width = "600";
	this.height = "400";
	this.calendarheaderclass = "calendarheader";
	this.dateheaderclass = "dateheader";
	this.curdateheaderclass = "curdateheader";
	this.datebodyclass = "datebody";
	this.calendaritemclass="calendaritem";
	this.afterInit;
	this.onCellClick;
	this.onItemClick;
}

Calendar.prototype.init = function(para){
	if (para && !jQuery.isEmptyObject(para))
	{
		if (para.year && parsInt(para.year,10))
		{
			this.year = parsInt(para.year,10);
		}
		if (para.month && parsInt(para.month,10))
		{
			this.month = parsInt(para.month,10);
		}
		if (para.date && parsInt(para.date,10))
		{
			this.date = parsInt(para.date,10);
		}
		if (para.width)
		{
			this.width = para.width;
		}
		if (para.height)
		{
			this.height = para.height;
		}
		if (para.calendarheaderclass)
		{
			this.calendarheaderclass = para.calendarheaderclass;
		}
		if (para.dateheaderclass)
		{
			this.dateheaderclass = para.dateheaderclass;
		}
		if (para.datebodyclass)
		{
			this.datebodyclass = para.datebodyclass;
		}
		if (para.curdateheaderclass)
		{
			this.curdateheaderclass = para.curdateheaderclass;
		}
		if (para.calendaritemclass)
		{
			this.calendaritemclass = para.calendaritemclass;
		}
		if (para.afterInit)
		{
			this.afterInit = para.afterInit;
		}
		if (para.onCellClick)
		{
			this.onCellClick = para.onCellClick;
		}
		if (para.onItemClick)
		{
			this.onItemClick = para.onItemClick;
		}
	}
	
	if (this.container){
		var containerid = this.container.attr("id");
		var calendarheaderid = containerid + "_header";
		var calendardateheader = containerid + "_date_header";
		var calendardatebody = containerid + "_date_body";
		var calendarstatusid = containerid + "_status";
		
		//实际天数
		var tmpDate = 1;
		var beginDay = getDay(this.year,this.month,1);
		var lastingDay = getDay(this.year,this.month,1)+getDaysInMonth(this.year,this.month);
		
		this.items = new Array(lastingDay);
		
		var str = "<table width='"+this.width+"' border='0'>";
		str += "<tr>";
		str += "<td width='10%' id='preyear' class='calendarbtn'><<</td>"+
				"<td width='10%' id='premonth' class='calendarbtn'><</td>"+
				"<td width='30%' id='selectedyear' align='center' style='font-size:13;color:#ff9900'>"+this.year+"年&nbsp;"
				+(this.month+1)+"月</td>"+
				"<td width='30%' id='"+calendarstatusid+"' align='center' style='font-size:13;color:#ff9911'>&nbsp;</td>"+
				"<td width='10%' id='nextmonth' class='calendarbtn'>></td>"+
				"<td width='10%' id='nextyear' class='calendarbtn'>>></td>";
		str += "</tr>";
		str += "</table>";
		
		str += "<table width='"+this.width+"' border='0'>";
		str += "<tr>";
		str += "<td width='14.2%' class='"+this.calendarheaderclass+"' id='"+calendarheaderid+"_0'>周日</td>"+
				"<td width='14.2%' class='"+this.calendarheaderclass+"' id='"+calendarheaderid+"_1'>周一</td>"+
				"<td width='14.2%' class='"+this.calendarheaderclass+"' id='"+calendarheaderid+"_2'>周二</td>"+
				"<td width='14.2%' class='"+this.calendarheaderclass+"' id='"+calendarheaderid+"_3'>周三</td>"+
				"<td width='14.2%' class='"+this.calendarheaderclass+"' id='"+calendarheaderid+"_4'>周四</td>"+
				"<td width='14.2%' class='"+this.calendarheaderclass+"' id='"+calendarheaderid+"_5'>周五</td>"+
				"<td width='14.2%' class='"+this.calendarheaderclass+"' id='"+calendarheaderid+"_6'>周六</td>";
		str += "</tr>";
		str += "</table>";
		
		str += "<div style='width:"+this.width+";height:"+this.height+";overflow:auto;padding:2;border:2 solid #9999ff'>"+
				"<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
		
		for (var i=0;i<6;i++)
		{
			str += "<tr>";
			for (var j=0;j<7;j++)
			{
				var cellindex = i*7+j;
				
				if (cellindex>lastingDay-1 || cellindex<beginDay)
				{
					str += "<td width='14.2%'>&nbsp;</td>";
				}else{
					var stylestr = "border-right:1px solid #f1f1f1;border-bottom:1px solid #f1f1f1";
					if (cellindex==beginDay)
					{
						stylestr = "border:1 solid #f1f1f1";
					}else if (i==0)
					{
						stylestr = "border-right:1px solid #f1f1f1;border-bottom:1px solid #f1f1f1;border-top:1px solid #f1f1f1";
					}else if (j==0)
					{
						stylestr = "border-right:1px solid #f1f1f1;border-bottom:1px solid #f1f1f1;border-left:1px solid #f1f1f1";
					}
					
					str += "<td style='"+stylestr+"' valign='top' width='14.2%'>";
					str += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
					str += "<tr><td id='"+calendardateheader+"_"+cellindex+"' class='cellhandle'>&nbsp;</td></tr>";
					str += "<tr><td valign='top'>"+
							"<div style='height:120;overflow:auto;word-break:break-all' id='"+calendardatebody+"_"+cellindex+"'>" +
							"</div></td></tr>";
					str += "</table>";
					str += "</td>";
				}	
			}
			str += "</tr>";
		}
		
		str += "</table></div>";
		
		$("#"+containerid).html(str);
		
		for (i=beginDay;i<lastingDay;i++)
		{	
			var tmpcurDateIndex = getDay(this.year,this.month,1)+(new Date()).getDate()-1;
			if (tmpcurDateIndex==i && isCurrent(this.year,this.month))
			{
				$("#"+calendardateheader+"_"+i).addClass(this.curdateheaderclass).html(tmpDate);
			}else{
				$("#"+calendardateheader+"_"+i).addClass(this.dateheaderclass).html(tmpDate);
			}
			
			$("#"+calendardatebody+"_"+i).addClass(this.datebodyclass);
			tmpDate ++;
		}
		
		this.itemhandleid = 0;
		
		if (this.afterInit)
		{
			this.afterInit(this.year,this.month);
		}
		
		var that = this;
		
		$("#preyear").click(function(){that.preyear();});
		
		$("#premonth").click(function(){that.premonth();});
		
		$("#nextmonth").click(function(){that.nextmonth();});
		
		$("#nextyear").click(function(){that.nextyear();});
		
		$(".cellhandle").click(function(){
			var id = this.id;
			var cellindex = id.split("_")[id.split("_").length-1];
			if (onCellClick)
			{
				onCellClick(that.year,that.month,parseInt(cellindex,10)+1-getDay(that.year,that.month,1),that.items[cellindex]);
			}
		});

	}else{
		alert("no container");
		return;
	}
	
};

Calendar.prototype.preyear = function(){
	this.year--;
	this.init({});
};

Calendar.prototype.premonth = function(){
	this.month--;
	if (this.month<0)
	{
		this.year --;
		this.month = 11;
	}
	this.init({});
};

Calendar.prototype.nextmonth = function(){
	this.month++;
	if (this.month>11)
	{
		this.year ++;
		this.month = 0;
	}
		
	this.init({});
};

Calendar.prototype.nextyear = function(){
	this.year++;
	this.init({});
};

Calendar.prototype.status = function(str){
	if (this.container)
	{
		var containerid = this.container.attr("id");
		var calendarstatusid = containerid + "_status";
		
		if (str == "undifined" || !str)
		{
			return $("#"+calendarstatusid).html();
		}else{
			$("#"+calendarstatusid).html(str);
		}
		
	}
};

Calendar.prototype.addItem = function(date,calendarcssname,item)
{
	if (date>getDaysInMonth(this.year,this.month) || date<1)
		return;
	
	var containerid = this.container.attr("id");
	var calendardatebody = containerid + "_date_body";
	
	var tmp = new Date(this.year,this.month,date);
	var dateindex = getDay(this.year,this.month,1) + tmp.getDate()-1;
	
	var tmpItem = this.items[dateindex];
	if (!tmpItem)
	{
		tmpItem = [];
	}
	
	tmpItem.push(item);
	if ($("#"+calendardatebody+"_"+dateindex))
	{
		if (!calendarcssname || calendarcssname.length<=0)
		{
			calendarcssname = this.calendaritemclass;
		}
		var str = "<div class='"+calendarcssname+"' id='item_"+this.itemhandleid+"'>"+item["showname"]+"</div>";
		$("#"+calendardatebody+"_"+dateindex).append(str);		
		if (this.onItemClick)
		{
			var that = this;
			$("#item_"+this.itemhandleid).click(function(){
				that.onItemClick(that.year,that.month,tmp.getDate(),item);
			});
		}
		this.itemhandleid ++;
	}
	
	this.items[dateindex] = tmpItem;
	
};

//获取选择月有多少天
function getDaysInMonth(year,month){
    var tmp = new Date(year,month+1,0);
    return tmp.getDate();
}

//获取该日期是周几
function getDay(year,month,date)
{
	var tmp = new Date(year,month,date);
	var tmpDay = tmp.getDay();
	return tmpDay;
}

//是否当前月
function isCurrent(year,month)
{
	if (year==((new Date()).getFullYear()) && month==(new Date()).getMonth())
	{
		return true;
	}else{
		return false;
	}
}