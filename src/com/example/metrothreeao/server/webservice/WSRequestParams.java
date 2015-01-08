package com.example.metrothreeao.server.webservice;

import java.util.ArrayList;

import org.ksoap2.serialization.SoapObject;
import org.kxml2.kdom.Element;

import com.example.metrothreeao.constant.HTTPConstant;


/**
 * WebService参数类
 * 
 * @author Zing
 * 
 */
public class WSRequestParams {

	private SoapObject soapObj;
	private String methodName;
	private String requestUrl;
	private Element[] header;

	/**
	 * 构造函数
	 * 
	 * @param soapObj
	 *            请求参数
	 * @param methodName
	 *            请求方法
	 * @param requestUrl
	 *            请求的RUL
	 * @param header
	 *            头部信息
	 */
	public WSRequestParams(ArrayList<WSSoapParams> soapParams, String methodName, String requestUrl, Element[] header) {
		SoapObject soap = new SoapObject(HTTPConstant.NAME_SPACE, methodName);
		for(WSSoapParams param : soapParams)
		{
			soap.addProperty(param.getSoapKey(), param.getSoapValue());
		}
		this.soapObj = soap;
		this.methodName = methodName;
		this.requestUrl = requestUrl;
		this.header = header;
	}

	public SoapObject getSoapObj() {
		return soapObj;
	}

	public void setSoapObj(SoapObject soapObj) {
		this.soapObj = soapObj;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Element[] getHeader() {
		return header;
	}

	public void setHeader(Element[] header) {
		this.header = header;
	}

	
}
