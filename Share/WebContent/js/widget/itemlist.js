function ItemList(obj){
	this.container = obj;
	this.itemshowed="name";
	this.itemlistClass="itemlist";
	this.enableHiddenField=true;
	this.itemlist_fieldindex = 0;
	this.itemlistobject = {};
	this.itemlist=[];
}

ItemList.prototype.init = function(para){
	if (para && !jQuery.isEmptyObject(para))
	{
		if (para.itemshowed) 
		{
			this.itemshowed = para.itemshowed;
		}
		if (para.itemlistClass) 
		{
			this.itemlistClass = para.itemlistClass;
		}
		if (para.enableHiddenField==false) 
		{
			this.enableHiddenField = para.enableHiddenField;
		}
	}
	
};

ItemList.prototype.add = function(item){
	if (this.container)
	{
		
	}else{
		alert("add item failed:no html object");
		return;
	}
	
	if (item && item[this.itemshowed])
	{
		this.itemlistobject[item[this.itemshowed]]=item;
		var showeditemid = this.container.attr("id")+"_"+this.itemlist_fieldindex;
		var str = "<div class='"+this.itemlistClass+"'>&nbsp;"+item[this.itemshowed]+"&nbsp;"+
					"<img src='/FileManager/pic/del.gif' align='absmiddle' style='cursor:hand' id='"+showeditemid+"_del'>&nbsp;</div>";
		if (this.enableHiddenField)
		{
			str += "<input type='hidden' id='"+showeditemid+"' name='list' value='"+item["id"]+"'>";
		}
		this.container.append(str);
		
		$("#"+showeditemid+"_del").bind("click",{itemlistobject:this.itemlistobject,item:item,itemshowed:this.itemshowed,showeditemid:showeditemid},function(event){
			delete event.data.itemlistobject[event.data.item[event.data.itemshowed]];
			$("#"+event.data.showeditemid).remove();
			$(this).parents("div").remove();
		});
		this.itemlist_fieldindex ++;
	}else{
		alert("add item failed:missing item attr");
		return;
	}
};

ItemList.prototype.getItemList = function(){
	this.itemlist = [];
	
	for (var key in this.itemlistobject)
	{
		this.itemlist.push(key);
	}
	
	return this.itemlist;
};

ItemList.prototype.getItemListObject = function(){
	return this.itemlistobject;
};