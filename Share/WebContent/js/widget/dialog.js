function Dialog(name){
	this.name = name;
	this.title = name;
	this.left = 0;
	this.top = 0;
	this.width = 400;
	this.height = 300;
	this.url;
	this.url_para="";
	this.showOnCreate = false;
	this.picUrlBase = "/FileManager/pic/";
}

Dialog.prototype.init = function(para){
	if (para && !jQuery.isEmptyObject(para))
	{
		if (para.title)
		{
			this.title = para.title;
		}
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
		if (para.url)
		{
			this.url = para.url;
		}
		if (para.url_para)
		{
			this.url_para = para.url_para;
		}
		if (para.showOnCreate==true)
		{
			this.showOnCreate = true;
		}
	}
	
	var display = "none";
	if (this.showOnCreate)
	{
		display = "block";
	}
	
	$("#dialog_"+this.name).remove();
	
	var str = "<div id='dialog_"+this.name+"' style='position:absolute;display:"+display+";left:"+this.left+
					";top:"+this.top+";width:"+this.width+";border:1 solid #c1c1c1;background-color:white'>" +
					"<table width='100%' border='0'>" +
					"<tr><td width='97%' style='background:#9999FF;color:white'>&nbsp;&nbsp;"+this.title+"</td>" +
					"<td width='3%' align='right' style='background:#9999FF'>" +
					"<img src='"+this.picUrlBase+"remove.png' style='cursor:hand' id='dialog_"+this.name+"_closebtn'></td></tr>";
	str += "<tr><td colspan='2' id='diag_"+this.name+"_body' valign='top' height='"+(this.height-20)+"'>";
	if (this.url && this.url.length>0)
	{
		str += "<iframe style='width:100%;height:100%;' frameborder='0' id='diag_"+this.name+"_frame'"+
				"src=''></iframe>";				
	}
	str += "</td></tr>";
	str += "</table></div>";
	
	$(document.body).append(str);
	
	var diagobj = this;
	$("#dialog_"+this.name+"_closebtn").bind("click",{dialogobject:diagobj},function(event){
		$("#dialog_"+event.data.dialogobject.name).hide();
	});
	
};

Dialog.prototype.open = function(refreshflag){
	if ($("#diag_"+this.name+"_frame") && $("#diag_"+this.name+"_frame").attr("src")=="")
	{
		if (this.url)
		{
			$("#diag_"+this.name+"_frame").attr("src", this.url+"?"+this.url_para);
		}
	}
	
	$("#dialog_"+this.name).show();
	if (refreshflag && $("#diag_"+this.name+"_frame"))
	{
		$("#diag_"+this.name+"_frame").attr("src", this.url+"?"+this.url_para);
	}
};

Dialog.prototype.close = function(){
	$("#dialog_"+this.name).hide();

};

Dialog.prototype.html = function(str){
	
	if (this.name && this.name.length>0 && $("#diag_"+this.name+"_body"))
	{
		if (str == "undifined" || !str)
		{
			return $("#diag_"+this.name+"_body").html();
		}else{
			$("#diag_"+this.name+"_body").html(str);
		}
		
	}
};

Dialog.prototype.setUrl = function(url){
	this.url = url;
};

Dialog.prototype.setUrlPara = function(url_para){
	this.url_para = url_para;
};