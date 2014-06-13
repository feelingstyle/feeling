package com.wechat;

import java.util.HashMap;

import org.json.JSONArray;

import com.wechat.cmd.CommandInterface;
import com.wechat.menu.MenuCmdItem;
import com.wechat.menu.MenuItem;
import com.wechat.menu.MenuItemManager;
import com.wechat.message.*;

public class MessageProcess {

	public MessageProcess() {
		// TODO Auto-generated constructor stub
	}

	public static String onTextRequest(Message reqMsg,HashMap<String,MenuItem> mapCMD)
	{
		String replyXml = "";
		String reqContent = ((RequestTextMessage)reqMsg).getContent().trim();
		//无状态交互会话进程控制
		Interaction interaction = Interaction.get(((RequestTextMessage)reqMsg).getFromUserName());
		
		if (reqContent.equals("exit"))
		{//若在途指令exit，则还原在途信息。
			if (interaction!=null)
			{
				interaction.remove();
				interaction = null;
			}
		}
		
		if (interaction==null)
		{//null,没有在途命令。默认请求为命令
			if (reqMsg!=null && reqContent.length()>0)
			{
				if (mapCMD.containsKey(reqContent))
				{//是否在菜单里
					MenuItem  miCMD = mapCMD.get(reqContent);
					TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text","","0");
					if (miCMD.getIsparent()==1)
					{//是父节点则返回显示其子菜单
						String mlstr = MenuItemManager.showSubMenuItemListStr(miCMD.getId());
						if (mlstr==null)
						{
							tm.setContent("该菜单暂无内容");
							replyXml = tm.toXML();
						}else{
							tm.setContent(mlstr);
							replyXml = tm.toXML();
						}
					}else{//是命令则校验其可实用性，并返回命令使用方法
						MenuCmdItem mci = MenuCmdItem.getFirst(miCMD.getId());
							
						if (mci!=null)
						{
							if (mci.getPara()==null || mci.getPara().length()<=0)
							{//无参调用
								try{	
									Class<?> cmdclass = Class.forName(mci.getClassname());
									CommandInterface cmd = (CommandInterface)cmdclass.newInstance();
									replyXml = cmd.call(((RequestTextMessage)reqMsg).toMap());
								}catch (Exception e)
								{
									e.printStackTrace();
									tm.setContent("调用异常:"+e.getMessage());	
									replyXml = tm.toXML();
								}
								
							}else{//有参调用，返回参数输入说明
								tm.setContent(getCommandTips(mci));	
								replyXml = tm.toXML();
								interaction = new Interaction(((RequestTextMessage)reqMsg).getFromUserName(), 
								miCMD.getId(), miCMD.getCmd(), mci.getPara(), mci.getClassorder());
								interaction.save();
							}
						}else{//找不到相关的菜单项命令详细
							tm.setContent("找不到相关的菜单项命令详细");	
							replyXml = tm.toXML();
						}
						
					}
					
				}else{
					String resContent = "欢迎使用OCNShare订阅号，请从以下菜单项中选择输入！\r\n" + MenuItemManager.showSubMenuItemListStr(0);
					TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
					replyXml = tm.toXML();
				}
			}else{
				String resContent = "输入的数据有问题，请从以下菜单项中选择输入！\r\n" + MenuItemManager.showSubMenuItemListStr(0);
				TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
				replyXml = tm.toXML();
			}
		}else{//存在在途命令，默认请求为在途参数，并需执行调用
			MenuCmdItem mci = MenuCmdItem.get(interaction.getCmdid(),interaction.getClassorder());
			TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text","","0");
			String resContent = "";
			
			if (mci!=null)
			{
				HashMap<String,String> attrmap = parseCommandAttribute(mci,reqContent);
				if (attrmap==null)
				{
					resContent += "你输入的参数和所需参数不符，请重新输入或输入exit重新选择指令";
					tm.setContent(resContent);
					replyXml = tm.toXML();
				}else{
					try{	
						Class<?> cmdclass = Class.forName(mci.getClassname());
						CommandInterface cmd = (CommandInterface)cmdclass.newInstance();
						replyXml = cmd.call(((RequestTextMessage)reqMsg).toMap(),attrmap);	
					}catch (Exception e)
					{
						resContent += e.getMessage();
						tm.setContent(resContent);
						replyXml = tm.toXML();
					}
					interaction.remove();
				}
			}else{//找不到相关的菜单项命令详细
				resContent += "找不到相关的菜单项命令详细";
				tm.setContent(resContent);
				replyXml = tm.toXML();
				interaction.remove();
			}
		}
		
		return replyXml;
	}
	
	public static String onMenuClick(Message reqMsg,HashMap<String,MenuItem> mapCMD)
	{
		String replyXml = "";
		String menuid = ((RequestClickEventMessage)reqMsg).getEventKey();
		
		TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text","","0");
		MenuCmdItem mci = MenuCmdItem.getFirst(Integer.parseInt(menuid));
		
		if (mci!=null)
		{
			try{	
				Class<?> cmdclass = Class.forName(mci.getClassname());
				CommandInterface cmd = (CommandInterface)cmdclass.newInstance();
				replyXml = cmd.call(((RequestClickEventMessage)reqMsg).toMap());
			}catch (Exception e)
			{
				e.printStackTrace();
				tm.setContent("调用异常:"+e.getMessage());	
				replyXml = tm.toXML();
			}
		}else{//找不到相关的菜单项命令详细
			tm.setContent("找不到相关的菜单项命令详细");	
			replyXml = tm.toXML();
		}
		
		return replyXml;
	}
	
	private static String getCommandTips(MenuCmdItem mci)
	{
		String s = "";
		JSONArray jaCmdPara = null;
		try {
			jaCmdPara = new JSONArray(mci.getPara());
			s += "输入参数请用&分隔输入,输入exit退出该指令\r";
			for (int i=0;i<jaCmdPara.length();i++)
			{
				s += "    参数"+(i+1)+":" + jaCmdPara.getJSONObject(i).getString("key") + 
						"  ("+jaCmdPara.getJSONObject(i).getString("note")+")\r"; 
			}
		}catch (Exception e)
		{
			s = "[异常]命令参数有误";
		}
		
		return s;
	}
	
	private static HashMap<String,String> parseCommandAttribute(MenuCmdItem mci,String inputstr)
	{
		if (mci==null || mci.getPara()==null)
			return null;
		
		HashMap<String,String> map = new HashMap<String, String>();
		try {
			JSONArray jaPara = new JSONArray(mci.getPara());
			String[] inputstrlist = inputstr.split("&");
			
			if (jaPara.length()!=inputstrlist.length)
			{
				return null;
			}else{
				for (int i=0;i<jaPara.length();i++)
				{
					map.put(jaPara.getJSONObject(i).getString("key"), inputstrlist[i]);
				}
			}
		}catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
		return map;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
