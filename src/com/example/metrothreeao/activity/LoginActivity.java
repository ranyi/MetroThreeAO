package com.example.metrothreeao.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.metrothreeao.MyApplication;
import com.example.metrothreeao.R;
import com.example.metrothreeao.constant.HTTPConstant;
import com.example.metrothreeao.entity.login.User;
import com.example.metrothreeao.face.IHttp;
import com.example.metrothreeao.globals.Globals;
import com.example.metrothreeao.server.webservice.WSAsyncTask;
import com.example.metrothreeao.server.webservice.WSRequestParams;
import com.example.metrothreeao.server.webservice.WSResponseInterface;
import com.example.metrothreeao.server.webservice.WSSoapParams;
import com.example.metrothreeao.util.DialogUtil;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements WSResponseInterface {

	private String TAG = "LoginActivity";
	private EditText txtName,txtPassWord;
	private Button btnLogin;
	private ProgressDialog dialog;
	
	private Activity context = this;
	private long mOnBackTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		Globals.allActivitys.put("LoginActivity", this);
		if (!isNetwork()) {
			showSettingNetworkDialog();
		} 
		
		initView();
		initListener();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		cancleDialog();
		Globals.allActivitys.remove("LoginActivity");
	}
	
	@Override
	public void onBackPressed() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - mOnBackTime > 1200) {
			Toast.makeText(context, "请再按一次返回", Toast.LENGTH_LONG).show();
			mOnBackTime = currentTime;
			return;
		}
		
		Builder builder = new Builder(context);
		builder.setMessage("是否退出");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				((MyApplication)getApplication()).appExit();
			}
		});
		builder.setNegativeButton("取消", null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		if(!dialog.isShowing()){
			dialog.show();
		}
	}
	
	private void initView(){
		txtName = (EditText) findViewById(R.id.txtName);
		txtPassWord = (EditText) findViewById(R.id.txtPassWord);
		btnLogin = (Button) findViewById(R.id.btnLogin);
	}
	
	private void initListener(){
		btnLogin.setOnClickListener(btnLoginOnClickListener);
	}
	
	
	/**
	 *  判定网络
	 * @return
	 */
	private boolean isNetwork() {
		ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		NetworkInfo info = connectivity.getActiveNetworkInfo();
		if (info == null || !connectivity.getBackgroundDataSetting())
			return false;
		return info.isConnected();
	}
	
	/**
	 * 打开网络设置提示框
	 */
	private void showSettingNetworkDialog() {
		
		Builder builder = new Builder(context);
		builder.setTitle("提示");
		builder.setMessage("网络连接不可用,是否进行设置?");
		builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				Intent intent = null;
				// 判断手机系统的版本 即API大于10 就是3.0或以上版本
				if (android.os.Build.VERSION.SDK_INT > 10) {
					intent = new Intent(
							android.provider.Settings.ACTION_WIFI_SETTINGS);
				} else {
					intent = new Intent();
					ComponentName component = new ComponentName(
							"com.android.settings",
							"com.android.settings.WirelessSettings");
					intent.setComponent(component);
					intent.setAction("android.intent.action.VIEW");
				}
				context.startActivity(intent);
			}
		});
		
		builder.setNegativeButton("取消", null);
		AlertDialog dialog = builder.create();
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(true);
		if(!dialog.isShowing()){
			dialog.show();
		}
		
	}
	
	private void cancleDialog(){
		if(dialog!=null && dialog.isShowing()){
			dialog.cancel();
		}
	}
	
	private OnClickListener btnLoginOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ArrayList<WSSoapParams> soapParams = new ArrayList<WSSoapParams>();
			soapParams.add(new WSSoapParams("UserName", txtName.getText().toString().trim()));
			soapParams.add(new WSSoapParams("PassWord", txtPassWord.getText().toString().trim()));
			new WSAsyncTask(LoginActivity.this).execute(new WSRequestParams(soapParams, HTTPConstant.LOGIN, HTTPConstant.LOGIN_URL, null));
			dialog = DialogUtil.showProgressDialog(context, "正在登陆请稍后");
			dialog.show();
		}
	};

	@Override
	public void callBackResponse(String result, String responseMethod) {
		cancleDialog();
		Gson gson = new Gson();
		if(responseMethod.equals(HTTPConstant.LOGIN)){
			if (HTTPConstant.ERROR.equals(result)) {
				Toast.makeText(LoginActivity.this, "请求服务器数据失败,请检查网络是否正常", Toast.LENGTH_LONG).show();
				return;
			}
			
			Log.i(TAG, result);
			
			try {
				JSONObject jsonObject = new JSONObject(result);
				if ("1".equals(jsonObject.getString("isSuccess"))){
					User user = gson.fromJson(jsonObject.getString("Msg"), User.class);
					Globals.getInstance().setUser(user);
					Intent intent = new Intent(context,MainMenuActivity.class);
					startActivity(intent);
					context.finish();
				}else {
					Toast.makeText(LoginActivity.this, jsonObject.getString("Msg"), Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		

	}
	

}
