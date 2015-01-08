package com.example.metrothreeao.entity.main;

public class Device {
	private String DevicesId;
	private String DevicesName;
	public Device() {
		super();
	}
	public Device(String devicesId, String devicesName) {
		super();
		DevicesId = devicesId;
		DevicesName = devicesName;
	}
	public String getDevicesId() {
		return DevicesId;
	}
	public void setDevicesId(String devicesId) {
		DevicesId = devicesId;
	}
	public String getDevicesName() {
		return DevicesName;
	}
	public void setDevicesName(String devicesName) {
		DevicesName = devicesName;
	}
	@Override
	public String toString() {
		return "Device [DevicesId=" + DevicesId + ", DevicesName="
				+ DevicesName + "]";
	}
	
	
}
