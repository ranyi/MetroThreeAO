package com.example.metrothreeao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.metrothreeao.R;
import com.example.metrothreeao.constant.TaskConstant;
import com.example.metrothreeao.face.IHttp;
import com.example.metrothreeao.server.GetAsynTask;

public class MainActivity extends Activity implements IHttp {

	private TextView lblMessage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lblMessage = (TextView) findViewById(R.id.lblMessage);
		GetAsynTask task = new GetAsynTask(this, "http://m.weather.com.cn/atad/101020100.html", TaskConstant.GET_WEATHER);
		task.execute();
	
	}
	@Override
	public void onCallBack(int flag, String result) {
		switch(flag){
		case TaskConstant.GET_WEATHER:
			lblMessage.setText(result);
			break;
		}
		
	}
	

}
