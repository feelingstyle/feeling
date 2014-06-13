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
		//��״̬�����Ự���̿���
		Interaction interaction = Interaction.get(((RequestTextMessage)reqMsg).getFromUserName());
		
		if (reqContent.equals("exit"))
		{//����;ָ��exit����ԭ��;��Ϣ��
			if (interaction!=null)
			{
				interaction.remove();
				interaction = null;
			}
		}
		
		if (interaction==null)
		{//null,û����;���Ĭ������Ϊ����
			if (reqMsg!=null && reqContent.length()>0)
			{
				if (mapCMD.containsKey(reqContent))
				{//�Ƿ��ڲ˵���
					MenuItem  miCMD = mapCMD.get(reqContent);
					TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text","","0");
					if (miCMD.getIsparent()==1)
					{//�Ǹ��ڵ��򷵻���ʾ���Ӳ˵�
						String mlstr = MenuItemManager.showSubMenuItemListStr(miCMD.getId());
						if (mlstr==null)
						{
							tm.setContent("�ò˵���������");
							replyXml = tm.toXML();
						}else{
							tm.setContent(mlstr);
							replyXml = tm.toXML();
						}
					}else{//��������У�����ʵ���ԣ�����������ʹ�÷���
						MenuCmdItem mci = MenuCmdItem.getFirst(miCMD.getId());
							
						if (mci!=null)
						{
							if (mci.getPara()==null || mci.getPara().length()<=0)
							{//�޲ε���
								try{	
									Class<?> cmdclass = Class.forName(mci.getClassname());
									CommandInterface cmd = (CommandInterface)cmdclass.newInstance();
									replyXml = cmd.call(((RequestTextMessage)reqMsg).toMap());
								}catch (Exception e)
								{
									e.printStackTrace();
									tm.setContent("�����쳣:"+e.getMessage());	
									replyXml = tm.toXML();
								}
								
							}else{//�вε��ã����ز�������˵��
								tm.setContent(getCommandTips(mci));	
								replyXml = tm.toXML();
								interaction = new Interaction(((RequestTextMessage)reqMsg).getFromUserName(), 
								miCMD.getId(), miCMD.getCmd(), mci.getPara(), mci.getClassorder());
								interaction.save();
							}
						}else{//�Ҳ�����صĲ˵���������ϸ
							tm.setContent("�Ҳ�����صĲ˵���������ϸ");	
							replyXml = tm.toXML();
						}
						
					}
					
				}else{
					String resContent = "��ӭʹ��OCNShare���ĺţ�������²˵�����ѡ�����룡\r\n" + MenuItemManager.showSubMenuItemListStr(0);
					TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
					replyXml = tm.toXML();
				}
			}else{
				String resContent = "��������������⣬������²˵�����ѡ�����룡\r\n" + MenuItemManager.showSubMenuItemListStr(0);
				TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text",resContent,"0");
				replyXml = tm.toXML();
			}
		}else{//������;���Ĭ������Ϊ��;����������ִ�е���
			MenuCmdItem mci = MenuCmdItem.get(interaction.getCmdid(),interaction.getClassorder());
			TextMessage tm = new TextMessage(reqMsg.getFromUserName(),reqMsg.getToUserName(),Long.toString(System.currentTimeMillis()),"text","","0");
			String resContent = "";
			
			if (mci!=null)
			{
				HashMap<String,String> attrmap = parseCommandAttribute(mci,reqContent);
				if (attrmap==null)
				{
					resContent += "������Ĳ���������������������������������exit����ѡ��ָ��";
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
			}else{//�Ҳ�����صĲ˵���������ϸ
				resContent += "�Ҳ�����صĲ˵���������ϸ";
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
				tm.setContent("�����쳣:"+e.getMessage());	
				replyXml = tm.toXML();
			}
		}else{//�Ҳ�����صĲ˵���������ϸ
			tm.setContent("�Ҳ�����صĲ˵���������ϸ");	
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
			s += "�����������&�ָ�����,����exit�˳���ָ��\r";
			for (int i=0;i<jaCmdPara.length();i++)
			{
				s += "    ����"+(i+1)+":" + jaCmdPara.getJSONObject(i).getString("key") + 
						"  ("+jaCmdPara.getJSONObject(i).getString("note")+")\r"; 
			}
		}catch (Exception e)
		{
			s = "[�쳣]�����������";
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
