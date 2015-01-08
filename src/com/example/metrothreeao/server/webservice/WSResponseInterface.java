package com.example.metrothreeao.server.webservice;

import android.R.integer;

/**
 * WebService接口回调
 * @author Zing
 *
 */
public interface WSResponseInterface {

	/**
	 * 回调方法
	 * @param result 回调结果
	 * @param responseMethod 请求类型，用于判断回调结果字符串是那一类的接口数据
	 */
	public void callBackResponse(String result, String responseMethod);
}
