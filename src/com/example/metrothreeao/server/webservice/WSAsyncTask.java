package com.example.metrothreeao.server.webservice;

import android.util.Log;
import android.widget.Toast;

import com.example.metrothreeao.constant.HTTPConstant;


public class WSAsyncTask {

	private String TAG = "WSAsyncTask";
	private WSResponseInterface callback;
	private int requestCount ;
	
	public WSAsyncTask(WSResponseInterface callback) {
		this.callback = callback;
		requestCount = 0;
	}

	public void execute(WSRequestParams params) {
		Log.i(TAG, "request====>"+params.getSoapObj());
//		BehaviorFileUtil.getInstance().SaveBehaviorFile(""+params.getSoapObj(), 1);
//		BehaviorFileUtil.getInstance().SaveBehaviorFile(""+params.getMethodName(), 1);
		new WSRequestTask(params, this).execute();
	}

	public void callback(boolean isSucceed, String result, WSRequestParams params)
	{
		Log.i(TAG,"isSucceed==>"+isSucceed+"\nresult==>"+ result+"\nMethod===>"+params.getMethodName());
		if(isSucceed)
		{
			callback.callBackResponse(result, params.getMethodName());
		}else
		{
			callback.callBackResponse(HTTPConstant.ERROR, params.getMethodName());
//			Log.i("zing", "重新请求，次数 = " + requestCount);
//			requestCount++;
//			if(requestCount >= 3)
//			{
//				callback.callBackResponse(RequestConstant.ERROR, params.getMethodName());
//			}else
//			{
//				Log.i("zing", "重新请求，次数 = " + requestCount);
//				
//				/*try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				new WSRequestTask(params, this).execute();*/
//			}
		}
	}
}
