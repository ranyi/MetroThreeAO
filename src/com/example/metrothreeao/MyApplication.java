package com.example.metrothreeao;

import com.example.metrothreeao.globals.Globals;

import android.app.Activity;
import android.app.Application;

public class MyApplication extends Application {
	
	@Override
	public void onCreate() {
		super.onCreate();
		Globals.getInstance().setApplication(this);
	}
	
	private void closeAllActivity(){
		for(Activity activity : Globals.allActivitys.values()){
			if(activity!=null){
				activity.finish();
			}
		}
	}
	
	/**
	 * �ر�Ӧ�ó���
	 */
	public void appExit(){
		closeAllActivity();
		System.exit(0);
	}

}
