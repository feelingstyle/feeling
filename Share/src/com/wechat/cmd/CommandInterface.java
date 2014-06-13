package com.wechat.cmd;

import java.util.*;

public interface CommandInterface {
	String call(HashMap<String,String> reqmsg);
	String call(HashMap<String,String> reqmsg,HashMap<String,String> map);
}
