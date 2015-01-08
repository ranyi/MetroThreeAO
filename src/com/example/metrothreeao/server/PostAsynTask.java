package com.example.metrothreeao.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.example.metrothreeao.face.IHttp;
import android.os.AsyncTask;

public class PostAsynTask extends AsyncTask<Void, Integer, String> {

	private IHttp activity;
	private String url;
	private Map<String, String> data;
	private int flag;

	public PostAsynTask(IHttp activity, String url, Map<String, String> data,
			int flag) {
		this.activity = activity;
		this.url = url;
		this.data = data;
		this.flag = flag;
	}

	@Override
	protected String doInBackground(Void... params) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			// if (!HttpUrls_.staffName.trim().equals("")) {
			// post.setHeader("cookie", HttpUrls_.staffName);
			// }
			HttpParams httpParams = client.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 5000);
			List<NameValuePair> dataParam = new ArrayList<NameValuePair>();
			if (data != null && data.size() > 0) {
				for (String key : data.keySet()) {
					dataParam.add(new BasicNameValuePair(key, data.get(key)));
				}
			}

			// System.out.println("params==>" + params);
			post.setEntity(new UrlEncodedFormEntity(dataParam, "UTF-8"));

			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				String value = EntityUtils.toString(response.getEntity());
				// System.out.println("returnvalue===>" + value);
				return value;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			post.abort();
		}
		return "";
	}

	@Override
	protected void onPostExecute(String result) {
		if (activity != null) {
			activity.onCallBack(flag, result);
		}
	}

}
