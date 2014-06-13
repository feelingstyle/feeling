function ComboBox(obj){
	this.inputbox = obj;
	this.left = 0;
	this.top = 24;
	this.width = 230;
	this.height = 90;
	this.keyshowed="name";
	this.keyshowedFilter;
	this.keyselected="name";
	this.getPairData;
	this.getPairDataOnFirstClick;
	this.onItemReturn;
	this.onItemClick;
	this.inputBuffer="";
	this.itemmenuClass = "submenu";
	this.itemmenuClassOnMouseOver = "submenu_mouseover";
}

ComboBox.prototype.init = function(para){
	if (para && !jQuery.isEmptyObject(para))
	{
		if (para.left && parseInt(para.left,10))
		{
			this.left = parseInt(para.left,10);
		}
		if (para.top && parseInt(para.top,10))
		{
			this.top = parseInt(para.top,10);
		}
		if (para.width && parseInt(para.width,10))
		{
			this.width = parseInt(para.width,10);
		}
		if (para.height && parseInt(para.height,10))
		{
			this.height = parseInt(para.height,10);
		}
		if (para.keyshowed)
		{
			this.keyshowed = para.keyshowed;
		}
		if (para.keyshowedFilter) 
		{
			this.keyshowedFilter = para.keyshowedFilter;
		}
		if (para.keyselected)
		{
			this.keyselected = para.keyselected;
		}
		if (para.getPairData) 
		{
			this.getPairData = para.getPairData;
		}
		if (para.getPairDataOnFirstClick==undefined)
		{
			this.getPairDataOnFirstClick = null;
		}else if (para.getPairDataOnFirstClick==true){
			this.getPairDataOnFirstClick = this.getPairData;
		}else{
			this.getPairDataOnFirstClick = para.getPairDataOnFirstClick;
		}
		if (para.onItemReturn) 
		{
			this.onItemReturn = para.onItemReturn;
		}
		if (para.onItemClick) 
		{
			this.onItemClick = para.onItemClick;
		}
	}
	
	if (this.inputbox)
	{
		this.listdivid = this.inputbox.attr("id") + "_Div";
		this.listid = this.inputbox.attr("id") + "_List";
		var liststr = "<div style='position:absolute;width:0px;height:0px;overflow:visible;z-index:902'>"+
						"<div id='"+this.listdivid+"' style='position:absolute;display:none;left:"+this.left+
						";top:"+this.top+";width:"+this.width+";border:1 solid #e1e1e1;background-color:white'>"+
						"<table width='100%' border='0'>"+
						"<tr><td>"+
						"<div style='width:100%;height:"+this.height+";overflow:auto'>"+
						"<table width='100%' border='0' id='"+this.listid+"'>"+
						"</table></div>"+
						"</td></tr></table></div></div>";

		$(liststr).insertBefore(this.inputbox);
	}else{
		alert("no object ");
		return;
	}

	var that = this;
	
	$(this.inputbox).click(function(){
		if ($("#"+that.listdivid).is(":visible")==false){
			var inputTxt = this.value;
			
			if (that.getPairDataOnFirstClick)
			{
				var items = that.getPairDataOnFirstClick(inputTxt);
				if (items && items.length>0)
				{
					if (that.onItemReturn)
					{
						that.onItemReturn(items);
					}
					var str = "";
					$("#"+that.listid).html("");
					for (var i=0;i<items.length;i++)
					{
						str = "<tr><td class='"+that.itemmenuClass+" itemclass' style='cursor:hand' value="+i+">";					
						if (items[i])
						{
							if (that.keyshowedFilter)
							{
								str += that.keyshowedFilter(items[i]);
							}else{
								if (items[i][that.keyshowed])
								{
									str += items[i][that.keyshowed];
								}else{
									str += "取值异常";
								}
							}
						}
						str += "</td></tr>";
						$("#"+that.listid).append(str);
					}
					
					var thatinthis = this;
					$(".itemclass").click(function(){
						if (items[$(this).attr("value")][that.keyselected])
						{
							$("#"+thatinthis.id).val(items[$(this).attr("value")][that.keyselected]);
							if (that.onItemClick)
							{
								that.onItemClick($(this).attr("value"),items);
							}
						}
						
						$("#"+that.listdivid).hide();
					});
					
					$(".itemclass").bind("mouseover",{itemmenuClassOnMouseOver:that.itemmenuClassOnMouseOver,itemmenuClass:that.itemmenuClass},addItemMenuClass);
					$(".itemclass").bind("mouseout",{itemmenuClassOnMouseOver:that.itemmenuClassOnMouseOver,itemmenuClass:that.itemmenuClass},removeItemMenuClass);
					
					$("#"+that.listdivid).show();
				}else{
					$("#"+that.listid).html("");
					$("#"+that.listdivid).hide();
					if (that.onItemReturn)
					{//没有搜索结果
						that.onItemReturn();
					}
				}
			}else{
				$("#"+that.listdivid).hide();
			}
		}else{
			$("#"+that.listdivid).hide();
		}
	});
	
	$(this.inputbox).keyup(function(){
		var inputTxt = this.value;
		var inputBuffer = that.inputBuffer;
		
		if (inputTxt==inputBuffer)
		{
			//未变更
			
		}else{
			if (inputTxt.length==0)
			{
				$("#"+that.listid).html("");
				$("#"+that.listdivid).hide();
				that.inputBuffer = inputTxt;
				return;
			}
			
			if (that.getPairData)
			{
				var items = that.getPairData(inputTxt);
				if (items && items.length>0)
				{
					if (that.onItemReturn)
					{
						that.onItemReturn(items);
					}
					var str = "";
					$("#"+that.listid).html("");
					for (var i=0;i<items.length;i++)
					{
						str = "<tr><td class='"+that.itemmenuClass+" itemclass' style='cursor:hand' value="+i+">";					
						if (items[i])
						{
							if (that.keyshowedFilter)
							{
								str += that.keyshowedFilter(items[i]);
							}else{
								if (items[i][that.keyshowed])
								{
									str += items[i][that.keyshowed];
								}else{
									str += "取值异常";
								}
							}
						}
						str += "</td></tr>";
						$("#"+that.listid).append(str);
					}
					
					var thatinthis = this;
					$(".itemclass").click(function(){
						if (items[$(this).attr("value")][that.keyselected])
						{
							$("#"+thatinthis.id).val(items[$(this).attr("value")][that.keyselected]);
							if (that.onItemClick)
							{
								that.onItemClick($(this).attr("value"),items);
							}
						}
						
						$("#"+that.listdivid).hide();
					});
					
					$(".itemclass").bind("mouseover",{itemmenuClassOnMouseOver:that.itemmenuClassOnMouseOver,itemmenuClass:that.itemmenuClass},addItemMenuClass);
					$(".itemclass").bind("mouseout",{itemmenuClassOnMouseOver:that.itemmenuClassOnMouseOver,itemmenuClass:that.itemmenuClass},removeItemMenuClass);
					
					$("#"+that.listdivid).show();
				}else{
					$("#"+that.listid).html("");
					$("#"+that.listdivid).hide();
					if (that.onItemReturn)
					{//没有搜索结果
						that.onItemReturn();
					}
				}
			}else{
				alert("没有事件处理输入文本");
				return;
			}
			
			that.inputBuffer = inputTxt;
			
		}
	});
	
};

ComboBox.prototype.clear= function(){
	$(this.inputbox).val("");
};

function addItemMenuClass(event)
{
	$(this).removeClass(event.data.itemmenuClass).addClass(event.data.itemmenuClassOnMouseOver);
}

function removeItemMenuClass(event)
{
	$(this).removeClass(event.data.itemmenuClassOnMouseOver).addClass(event.data.itemmenuClass);
}