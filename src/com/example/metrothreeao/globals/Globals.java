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
		gzStateMap.put("0", "�±���");
		gzStateMap.put("1", "�ڵ���");
		gzStateMap.put("2", "δ����");
		gzStateMap.put("3", "�ڽ���");
		gzStateMap.put("4", "�ڴ���");
		gzStateMap.put("5", "�ܾ�����");
		gzStateMap.put("8", "��ȷ��");
		gzStateMap.put("9", "��ȷ��");
		gzStateMap.put("10", "�ջ�");
		gzStateMap.put("11", "�ܾ��ջ�");
		gzStateMap.put("12", "�ر�");
		gzStateMap.put("13", "ֱ�ӱջ�");
		gzStateMap.put("14", "��Э������");
		gzStateMap.put("15", "��ɾ��");
		
		gzDjMap.put("1", "һ��");
		gzDjMap.put("5", "�ش�");
		gzDjMap.put("6", "����");
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
