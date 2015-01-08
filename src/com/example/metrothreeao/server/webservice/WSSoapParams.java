package com.example.metrothreeao.server.webservice;

/**
 * Webservice Soap≤Œ ˝¿‡
 * @author zing
 *
 */
public class WSSoapParams {
	private String soapKey;
	private Object soapValue;

	public WSSoapParams(String soapKey, Object soapValue) {
		this.soapKey = soapKey;
		this.soapValue = soapValue;
	}

	public String getSoapKey() {
		return soapKey;
	}

	public void setSoapKey(String soapKey) {
		this.soapKey = soapKey;
	}

	public Object getSoapValue() {
		return soapValue;
	}

	public void setSoapValue(Object soapValue) {
		this.soapValue = soapValue;
	}

}
