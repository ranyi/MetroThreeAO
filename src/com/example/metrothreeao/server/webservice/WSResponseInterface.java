package com.example.metrothreeao.server.webservice;

import android.R.integer;

/**
 * WebService�ӿڻص�
 * @author Zing
 *
 */
public interface WSResponseInterface {

	/**
	 * �ص�����
	 * @param result �ص����
	 * @param responseMethod �������ͣ������жϻص�����ַ�������һ��Ľӿ�����
	 */
	public void callBackResponse(String result, String responseMethod);
}
