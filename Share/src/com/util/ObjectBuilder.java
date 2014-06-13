package com.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.json.*;
import java.util.*;

public class ObjectBuilder {

	private Class<?> objectClass;
	
	public ObjectBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	public ObjectBuilder(Class objectClass) {
		this.objectClass = objectClass;
	}
	
	public ObjectBuilder(String className) {
		try{
			this.objectClass = java.lang.Class.forName(className);
		}catch (ClassNotFoundException cnfe)
		{
			this.objectClass = null;
		}
	}

	public Object buildObject(JSONObject jo)
	{
		if (this.objectClass==null)
		{
			System.out.println("class is null");
			return null;
		}
		
		if (jo==null)
		{
			System.out.println("JSONObject is null");
			return null;
		}
		
		Object o = null;
		
		try {
			o = this.objectClass.newInstance();
			
			Iterator<String> iterKeys = jo.keys();
			while (iterKeys.hasNext())
			{
				String tmpKey = iterKeys.next();
				String tmpValue = jo.getString(tmpKey);
				Method method = getSetMethod(tmpKey);
				Class<?> para = method.getParameterTypes()[0];		
				if (para.getName().equals("java.lang.String"))
				{
					method.invoke(o,tmpValue);
				}else if (para.getName().equals("int"))
				{
					method.invoke(o,Integer.parseInt(tmpValue));
				}
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return o;
	}
	
	public Method getGetMethod(String fieldName) throws SecurityException, NoSuchMethodException{
	    StringBuffer sb=new StringBuffer();
	    sb.append("get");
	    sb.append(fieldName.substring(0,1).toUpperCase());
	    sb.append(fieldName.substring(1));
	    Method method=objectClass.getMethod(sb.toString(), null);
	    return method;
	}

	public Method getSetMethod(String fieldName) throws SecurityException, NoSuchMethodException, NoSuchFieldException{
	    Class[] parameterTypes=new Class[1];
	    Field field=objectClass.getDeclaredField(fieldName);
	    parameterTypes[0]=field.getType();
	    StringBuffer sb=new StringBuffer();
	    sb.append("set");
	    sb.append(fieldName.substring(0,1).toUpperCase());
	    sb.append(fieldName.substring(1));
	    Method method=objectClass.getMethod(sb.toString(), parameterTypes);
	    return method;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
