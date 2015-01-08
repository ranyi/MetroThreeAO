package com.example.metrothreeao.globals;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.example.metrothreeao.MyApplication;
import com.example.metrothreeao.entity.login.User;




public class Globals {

	
	static private Globals global;
	public static Map<String, Activity> allActivitys = new HashMap<String, Activity>();
	private MyApplication application;
	private User user;
	private Map<String, String> gzStateMap = new HashMap<String, String>();
	private Map<String, String> gzDjMap = new HashMap<String, String>();
	
	private Globals (){
		gzStateMap.put("0", "新报修");
		gzStateMap.put("1", "在调度");
		gzStateMap.put("2", "未接修");
		gzStateMap.put("3", "在接修");
		gzStateMap.put("4", "在处理");
		gzStateMap.put("5", "拒绝接修");
		gzStateMap.put("8", "待确认");
		gzStateMap.put("9", "在确认");
		gzStateMap.put("10", "闭环");
		gzStateMap.put("11", "拒绝闭环");
		gzStateMap.put("12", "关闭");
		gzStateMap.put("13", "直接闭环");
		gzStateMap.put("14", "待协调故障");
		gzStateMap.put("15", "已删除");
		
		gzDjMap.put("1", "一般");
		gzDjMap.put("5", "重大");
		gzDjMap.put("6", "紧急");
	}
	
	public static Globals getInstance() {
		if (global == null) {
			global = new Globals(); 
		}
		return global;
	}

	public MyApplication getApplication() {
		return application;
	}

	public void setApplication(MyApplication application) {
		this.application = application;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<String, String> getGzStateMap() {
		return gzStateMap;
	}

	public void setGzStateMap(Map<String, String> gzStateMap) {
		this.gzStateMap = gzStateMap;
	}

	public Map<String, String> getGzDjMap() {
		return gzDjMap;
	}

	public void setGzDjMap(Map<String, String> gzDjMap) {
		this.gzDjMap = gzDjMap;
	}
	
	
	
}
