package com.service.operator;

import com.util.*;

public class OperatorManager {

	public OperatorManager() {
		// TODO Auto-generated constructor stub
	}

	public static int createOperator(String name,String pwd,String fromip,String note)
	{
		Operator op = new Operator();
		op.setName(name);
		op.setAppid("share"+Long.toString(System.currentTimeMillis()));
		op.setAppsecret(Comm.stringToSHA1(pwd));
		op.setFromip(fromip);
		op.setNote(note);
		op.setStatus(0);
		
		return op.save();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
