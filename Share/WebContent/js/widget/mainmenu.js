function MainMenu(obj){
	this.container = obj;
	this.menu_width = 100;
	this.submenu_left = -10;
	this.submenu_top = 22;
	this.submenu_width = 150;
	this.menudata;
	this.mainmenuClick;
	this.submenuClick;
	this.mainmenuClass = "mainmenu";
	this.mainmenuClassOnMouseOver = "mainmenu_mouseover";
	this.submenuClass = "submenu";
	this.submenuClassOnMouseOver = "submenu_mouseover";
}

MainMenu.prototype.create = function(para){
	if (para && !jQuery.isEmptyObject(para))
	{
		if (para.menu_width && parseInt(para.menu_width,10))
		{
			this.menu_width = parseInt(para.menu_width,10);
		}
		if (para.submenu_left && parseInt(para.submenu_left,10))
		{
			this.submenu_left = parseInt(para.submenu_left,10);
		}
		if (para.submenu_top && parseInt(para.submenu_top,10))
		{
			this.submenu_top = parseInt(para.submenu_top,10);
		}
		if (para.submenu_width && parseInt(para.submenu_width,10))
		{
			this.submenu_width = parseInt(para.submenu_width,10);
		}
		if (para.menudata)
		{
			this.menudata = para.menudata;
		}
		if (para.mainmenuClick)
		{
			this.mainmenuClick = para.mainmenuClick;
		}
		if (para.submenuClick)
		{
			this.submenuClick = para.submenuClick;
		}
		if (para.mainmenuClass)
		{
			this.mainmenuClass = para.mainmenuClass;
		}
		if (para.mainmenuClassOnMouseOver)
		{
			this.mainmenuClassOnMouseOver = para.mainmenuClassOnMouseOver;
		}
		if (para.submenuClass)
		{
			this.submenuClass = para.submenuClass;
		}
		if (para.submenuClassOnMouseOver)
		{
			this.submenuClassOnMouseOver = para.submenuClassOnMouseOver;
		}
	}
	
	if (this.container){
		var containerid = this.container.attr("id");
		var menutableid = containerid + "_tr";
		
		var str = "<table width='100%' border='0'><tr id='"+menutableid+"'></tr></table>";
		$(this.container).html(str);
		
		if (this.menudata.list.length>0)
		{
			for (var i=0;i<this.menudata.list.length;i++)
			{
				var tmpmenuid = containerid + "_" +this.menudata.list[i]["id"]+"_mainmenu";
				var str = "<td width='"+this.menu_width+"' class='"+this.mainmenuClass+"' id='"+tmpmenuid+"'><div align='center'>"+
						this.menudata.list[i].title+"</div></td>";
				$("#"+menutableid).append(str);
				$("#"+tmpmenuid).bind("click",{id:this.menudata.list[i]["id"],url:this.menudata.list[i]["url"]},this.mainmenuClick);
				
				if (this.menudata.list[i]["submenu"] && this.menudata.list[i]["submenu"].list && this.menudata.list[i]["submenu"].list.length>0)
				{
					var tmpsubmenu = containerid + "_" +this.menudata.list[i]["id"]+"_submenu";
					var str = "<div style='position:absolute;width:0px;height:0px;overflow:visible;z-index:500'>"+
  							"<div id='"+tmpsubmenu+"' style='position:absolute;display:none;left:"+this.submenu_left+";" +
  							"top:"+this.submenu_top+";width:"+this.submenu_width+"px;border:1 solid #cccccc;background-color:white'>";
					str += "</div></div>";
					$("#"+tmpmenuid).html(str+$("#"+tmpmenuid).html());
					
					var str = "<table width='100%' border='0'>";
	    			for (var j=0;j<this.menudata.list[i]["submenu"].list.length;j++)	
				    {
	    				var tmpsubmenuid = tmpsubmenu + "_"+ this.menudata.list[i]["submenu"].list[j]["id"];
	    				str += "<tr><td class='submenuhandleclass "+this.submenuClass+"' id='"+tmpsubmenuid+"'>&nbsp;&nbsp;"+
	    						this.menudata.list[i]["submenu"].list[j]["title"]+"</td></tr>";
				    }
					str +=	"</table>";
					$("#"+tmpsubmenu).html(str);
					
					$("#"+tmpmenuid).bind("mouseover",{submenu:tmpsubmenu,mainmenuClassOnMouseOver:this.mainmenuClassOnMouseOver},openMenu);
					$("#"+tmpmenuid).bind("mouseout",{submenu:tmpsubmenu,mainmenuClass:this.mainmenuClass},closeMenu);
				}else{
					$("#"+tmpmenuid).bind("mouseover",{submenu:null,mainmenuClassOnMouseOver:this.mainmenuClassOnMouseOver},openMenu);
					$("#"+tmpmenuid).bind("mouseout",{submenu:null,mainmenuClass:this.mainmenuClass},closeMenu);
				}
			}
			
			$("#"+menutableid).append("<td>&nbsp;</td>");
			
			if (this.submenuClick)
			{
				$(".submenuhandleclass").bind("click",{},this.submenuClick);
			}
			
			$(".submenuhandleclass").bind("mouseover",{submenuClassOnMouseOver:this.submenuClassOnMouseOver,submenuClass:this.submenuClass},addSubMenuClass);
			$(".submenuhandleclass").bind("mouseout",{submenuClassOnMouseOver:this.submenuClassOnMouseOver,submenuClass:this.submenuClass},removeSubMenuClass);
		}
		
	}else{
		alert("no container");
		return;
	}
};

function openMenu(event)
{
	$(this).attr("class",event.data.mainmenuClassOnMouseOver);
	if (event.data.submenu)
	{
		$("#"+event.data.submenu).show();
	}
}

function closeMenu(event)
{
	$(this).attr("class",event.data.mainmenuClass);
	if (event.data.submenu)
	{
		$("#"+event.data.submenu).hide();
	}
}

function addSubMenuClass(event)
{
	$(this).removeClass(event.data.submenuClass).addClass(event.data.submenuClassOnMouseOver);
}

function removeSubMenuClass(event)
{
	$(this).removeClass(event.data.submenuClassOnMouseOver).addClass(event.data.submenuClass);
}
