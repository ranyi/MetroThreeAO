package com.example.metrothreeao.entity.repairfault;

public class FaultItem {

	private String id;
	private String code;
	private String time;
	private String line;
	private String station;
	private String equipment;
	private String faultDisplay;
	private String state;
	private String useTime;
	private String responseTime;
	public FaultItem() {
		super();
	}
	public FaultItem(String id, String code, String time, String line,
			String station, String equipment, String faultDisplay,
			String state, String useTime, String responseTime) {
		super();
		this.id = id;
		this.code = code;
		this.time = time;
		this.line = line;
		this.station = station;
		this.equipment = equipment;
		this.faultDisplay = faultDisplay;
		this.state = state;
		this.useTime = useTime;
		this.responseTime = responseTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getFaultDisplay() {
		return faultDisplay;
	}
	public void setFaultDisplay(String faultDisplay) {
		this.faultDisplay = faultDisplay;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUseTime() {
		return useTime;
	}
	public void setUseTime(String useTime) {
		this.useTime = useTime;
	}
	public String getResponseTime() {
		return responseTime;
	}
	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}
	@Override
	public String toString() {
		return "FaultItem [id=" + id + ", code=" + code + ", time=" + time
				+ ", line=" + line + ", station=" + station + ", equipment="
				+ equipment + ", faultDisplay=" + faultDisplay + ", state="
				+ state + ", useTime=" + useTime + ", responseTime="
				+ responseTime + "]";
	}
		
}
