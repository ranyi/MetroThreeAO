package com.example.metrothreeao.entity.main;

public class Station {

	private String stationid;
	private String stationName;
	
	
	public Station() {
		super();
	}


	public Station(String stationid, String stationName) {
		super();
		this.stationid = stationid;
		this.stationName = stationName;
	}


	public String getStationid() {
		return stationid;
	}


	public void setStationid(String stationid) {
		this.stationid = stationid;
	}


	public String getStationName() {
		return stationName;
	}


	public void setStationName(String stationName) {
		this.stationName = stationName;
	}


	@Override
	public String toString() {
		return "Station [stationid=" + stationid + ", stationName="
				+ stationName + "]";
	}
	
	
	
}
