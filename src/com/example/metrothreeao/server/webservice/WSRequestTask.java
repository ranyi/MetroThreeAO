package com.example.metrothreeao.server.webservice;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.metrothreeao.constant.HTTPConstant;


public class WSRequestTask extends AsyncTask<Void, Integer, String> {

	private WSRequestParams params;
	private WSAsyncTask asyncTask;
//	public static MyApplication application;

	public WSRequestTask(WSRequestParams params, WSAsyncTask asyncTask) {
		this.params = params;
		this.asyncTask = asyncTask;
	}

	@Override
	protected String doInBackground(Void... params) {

		String soapAction = HTTPConstant.NAME_SPACE + this.params.getMethodName();

//		Log.i("zing", "soapAction = " + soapAction);
		// ָ��WebService�������ռ�͵��õķ�����
		SoapObject soapObject = this.params.getSoapObj();
//		Log.e("TAG", this.params.getSoapObj()+"");
		// ���ɵ���WebService������SOAP������Ϣ,��ָ��SOAP�İ汾
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
		
		
		envelope.bodyOut = soapObject;
		// �����Ƿ���õ���dotNet������WebService
		envelope.dotNet = true;
		// �ȼ���envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(soapObject);
		// WebService��ͷ
		envelope.headerOut = this.params.getHeader();
//		Log.e("tag",this.params.getRequestUrl());
		HttpTransportSE transport = new HttpTransportSE(this.params.getRequestUrl());
//		MyAndroidHttpTransport transport = new MyAndroidHttpTransport(this.params.getRequestUrl(), 8000);
		String jsonData = "";
		try {
			// ����WebService
			transport.call(soapAction, envelope);
			Object result = envelope.getResponse();
			jsonData = result.toString();
		} catch (Exception e) {
			Log.e("zing", this.params.getMethodName() + " ���������ִ���" + e.getMessage() + ",\ntoString:" + e.toString());
			//��¼������־
//			ErrorFileUtil.getInstance().SaveErrorFile("method:"+this.params.getMethodName()+"\tMessage:"+e.getMessage()+"\te:"+e.toString());			
			return null;
		}
		return jsonData;

		// ��һ��д��
		// // ��ȡ���ص�����
		// SoapObject object = (SoapObject) envelope.bodyIn;
		//
		// if (object != null && object.getPropertyCount() > 0) {
		// // ��ȡ���صĽ��
		// jsonData = object.getProperty(0).toString();
		// Log.i("zing", "JSON = " + jsonData);
		// }else
		// {
		// Log.e("zing", this.responseMethod + "�޷�������");
		// }
	}

	@Override
	protected void onPostExecute(String jsonData) {
		if (jsonData == null) {
			asyncTask.callback(false, null, this.params);
		//	callback.callBackResponse(RequestConstant.ERROR, this.params.getMethodName());
		} else {
			try {
//				String zipStr = GZip.uncompress(jsonData);
//				String result=null;
//				if(Globals.getInstance().getUser().getLoginInfo().SessionID.length()>0){
//					result = DESUtil.decryptDES(zipStr,Globals.getInstance().getUser().getLoginInfo().SessionID.substring(0,8));
//				}
//				else{
//					result = DESUtil.decryptDES(zipStr, RequestConstant.DES_KEY);
//				}
//				Log.i("zing", this.params.getMethodName() + "���óɹ�" + result);
				asyncTask.callback(true, jsonData, this.params);
				//callback.callBackResponse(result, this.params.getMethodName());
				//��¼������־
//				try {
//					JSONObject jsonObject = new JSONObject(result);
//					if("0".equals(jsonObject.getString("IsSuccess"))){
//						ErrorFileUtil.getInstance().SaveErrorFile("method:"+this.params.getMethodName()+"\tMessage:"+result);
//					}
//					if("3".equals(jsonObject.getString("IsSuccess"))){
//							application.appExit();
//							Intent intent = new Intent(application, MainActivity.class);
//							intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							intent.putExtra("message", "�������������ط���½�������µ�½��");
//							application.startActivity(intent);
//							
//					}
//					
//				} catch (Exception e) {
//					e.printStackTrace();
////					ErrorFileUtil.getInstance().SaveErrorFile("method:"+this.params.getMethodName()+"\tMessage:"+result+"\nerror:"+e.toString());
//				}
				
				
				
//			} catch (IOException e) {
//				Log.e("zing", this.params.getMethodName() + " ��ѹ��ʧ��");
//				asyncTask.callback(false, null, this.params);
			//	callback.callBackResponse(RequestConstant.ERROR, this.params.getMethodName());
			} catch (Exception e) {
				pushException();
				e.printStackTrace();
			}
		}

	}

	private void pushException() {
		Log.e("zing", this.params.getMethodName() + " ����ʧ��");
		asyncTask.callback(false, null, this.params);
		//callback.callBackResponse(RequestConstant.ERROR, this.params.getMethodName());
	}
}
