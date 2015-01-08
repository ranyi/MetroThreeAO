package com.example.metrothreeao.entity.main;

public class Line {
	private String xlid;
	private String xlName;
	public Line() {
		super();
	}
	public Line(String xlid, String xlName) {
		super();
		this.xlid = xlid;
		this.xlName = xlName;
	}
	public String getXlid() {
		return xlid;
	}
	public void setXlid(String xlid) {
		this.xlid = xlid;
	}
	public String getXlName() {
		return xlName;
	}
	public void setXlName(String xlName) {
		this.xlName = xlName;
	}
	@Override
	public String toString() {
		return "Line [xlid=" + xlid + ", xlName=" + xlName + "]";
	}
	
	
}
