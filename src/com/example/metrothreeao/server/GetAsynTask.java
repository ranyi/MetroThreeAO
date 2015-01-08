package com.example.metrothreeao.server;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.metrothreeao.face.IHttp;

import android.os.AsyncTask;

public class GetAsynTask extends AsyncTask<Void, Integer, String> {

	private IHttp activity;
	private String url;
	private int flag;
	
	public GetAsynTask(IHttp activity,String url,int flag){
		this.activity = activity;
		this.url = url;
		this.flag = flag;
	}
	@Override
	protected String doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet( url);
		String value = "";
		try {
//				get.setHeader("cookie", HttpUrls_.staffName);

			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 5000);
			HttpResponse response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				value = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			get.abort();
		}
		return value;
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(activity!=null){
			activity.onCallBack(flag,result);
		}		
	}

}
