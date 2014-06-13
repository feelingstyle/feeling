function Operators()
{
	this.oplist = [];
}

Operators.prototype.getOPList = function(filter){
	var tmp = jsonrpc.opmanager.getOperator(filter);
	if (tmp && tmp.list)
	{
		this.oplist = tmp.list;
	}else{
		this.oplist = [];
	}
}

Operators.prototype.getOPListByfilter = function(filter){
	var tmp = jsonrpc.opmanager.getOperatorByFilter(filter);

	if (tmp && tmp.list)
	{
		this.oplist = tmp.list;
	}else{
		this.oplist = [];
	}
}