function TabLayout(obj){
	this.container = obj;
	this.tab_layout = "top";
	this.tab_header_item_width;
	this.tab_body_height = 530;
	this.tab_index = 0;
	this.tab_map={};
	this.tabheaderid;
	this.tabbodyid;
	this.tabClick;
	this.tabDblClick;
	this.tabClassName = "tab";
	this.curtabClassName = "curtab";
}

TabLayout.prototype.init = function(para){
	if (para && !jQuery.isEmptyObject(para))
	{
		if (para.tab_header_item_width && parseInt(para.tab_header_item_width,10))
		{
			this.tab_header_item_width = parseInt(para.tab_header_item_width,10);
		}
		if (para.tab_body_height && parseInt(para.tab_body_height,10))
		{
			this.tab_body_height = parseInt(para.tab_body_height,10);
		}
		if (para.tab_layout)
		{
			this.tab_layout = para.tab_layout;
		}
		if (para.tabClick)
		{
			this.tabClick = para.tabClick;
		}
		if (para.tabDblClick)
		{
			this.tabDblClick = para.tabDblClick;
		}
		if (para.tabClassName)
		{
			this.tabClassName = para.tabClassName;
		}
		if (para.curtabClassName)
		{
			this.curtabClassName = para.curtabClassName;
		}
	}
	
	if (this.container){
		var containerid = this.container.attr("id");
		this.tabheaderid = containerid + "_header";
		this.tabbodyid = containerid + "_body"; 
		
		if (this.tab_layout)
		{
			switch (this.tab_layout)
			{
				case "top":	var str = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
								str += "<tr><td id='"+this.tabheaderid+"' style='border-bottom:1 solid #f1f1f1'></td></tr>"+
									   "<tr><td id='"+this.tabbodyid+"' style='width:100%;height:"+this.tab_body_height+"'></td></tr>"+
									   "</table>";
							$("#"+containerid).html(str);
							break;
				case "left":var str = "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
									str += "<tr><td id='"+this.tabheaderid+"' style='border-right:1 solid #f1f1f1' width='12%' valign='top'></td>"+
									   		"<td id='"+this.tabbodyid+"' width='88%' style='height:"+this.tab_body_height+"' valign='top'></td></tr>"+
									   "</table>";
							$("#"+containerid).html(str);
							break;
			}
		}
	}else{
		alert("no container");
		return;
	}
};

TabLayout.prototype.createTab = function(para){
	if (para && !jQuery.isEmptyObject(para))
	{
		if (para.tab)
		{
			if (this.tabheaderid && this.tabbodyid)
			{
				var tab = para.tab;
				tab.tab_index = this.tab_index;
				tab.tab_header_id = this.tabheaderid + "_" +this.tab_index;
				tab.tab_body_id = this.tabbodyid + "_" +this.tab_index;
				var tmpTabHeader = "<div id='"+tab.tab_header_id+"' class='"+this.tabClassName+"'>&nbsp;&nbsp;"+
									tab.tab_header_name+"&nbsp;&nbsp;</div>";
				var tmpTabBody = "<div id='"+tab.tab_body_id+"' class='tabsbody'><iframe style='width:100%;height:100%;' frameborder='0' "+
									"src='"+tab.tab_body_url+"?"+tab.tab_body_url_para+"'></iframe></div>";
				$("#"+this.tabheaderid).append(tmpTabHeader);
				$("#"+this.tabbodyid).append(tmpTabBody);
				if (this.tabClick)
				{
					$("#"+tab.tab_header_id).bind("click",{tab_index:tab.tab_index},this.tabClick);
				}
				if (this.tabDblClick)
				{
					$("#"+tab.tab_header_id).bind("dblclick",{tab_index:tab.tab_index},this.tabDblClick);
				}
				this.tab_map[tab.tab_index] = tab;
				
				this.tab_index++;
				return tab.tab_index;
			}else{
				alert("Tab layout is not init yet!");
			}
		}else{
			alert("no tab argument!");
		}
	}else{
		alert("no argument!");
	}
};

TabLayout.prototype.showTab = function(tab_index){
	if (this.tab_map && this.tab_map[tab_index])
	{
		var tab = this.tab_map[tab_index];
		switch (tab.tab_type)
		{
			case 1:	$("."+this.curtabClassName).removeClass(this.curtabClassName).addClass(this.tabClassName);
					$("#"+this.tabheaderid+"_"+tab_index).removeClass(this.tabClassName).addClass(this.curtabClassName);
					$(".tabsbody").hide();
					$("#"+this.tabbodyid+"_"+tab_index).show();
					break;
		}
	}
	
};

TabLayout.prototype.removeTab = function(tab_index){
	if (this.tab_map && this.tab_map[tab_index])
	{
		var tab = this.tab_map[tab_index];
		var delTab = $("#"+tab.tab_header_id); 
		var delIndex = $("#"+this.tabheaderid+" > div").index(delTab);
		var tmpIndex = 0;
		
		if (delIndex > 0)
		{
			//向前有Tab，找到前一个Tab
			tmpIndex = delIndex - 1;
		}else{
			//向前无Tab，找到下一个Tab
			tmpIndex = delIndex + 1;
		}
		
		if ($("#"+this.tabheaderid+" > div")[tmpIndex] && $("#"+this.tabheaderid+" > div")[tmpIndex].id)
		{
			var tabToShow = $("#"+this.tabheaderid+" > div")[tmpIndex];
			var tmpTabID = tabToShow.id.split("_");
			this.showTab(tmpTabID[tmpTabID.length-1]);
		}
		
		$("#"+tab.tab_header_id).remove();
		$("#"+tab.tab_body_id).remove();
		delete this.tab_map[tab_index];
	}

};

TabLayout.prototype.getTabIndex = function(url,para){
	if (this.tab_map)
	{
		for (var key in this.tab_map)
		{
			if (this.tab_map[key]["tab_body_url"]==url && this.tab_map[key]["tab_body_url_para"]==para)
			{
				return key;
			}
		}
	}
	
	return -1;
};