package com.example.metrothreeao.entity.main;

/**
 * π ’œµ»º∂
 * @author acer
 *
 */
public class Dj {
	private String DJ_ID;
	private String DJ_Name;
	public Dj() {
		super();
	}
	public Dj(String dJ_ID, String dJ_Name) {
		super();
		DJ_ID = dJ_ID;
		DJ_Name = dJ_Name;
	}
	public String getDJ_ID() {
		return DJ_ID;
	}
	public void setDJ_ID(String dJ_ID) {
		DJ_ID = dJ_ID;
	}
	public String getDJ_Name() {
		return DJ_Name;
	}
	public void setDJ_Name(String dJ_Name) {
		DJ_Name = dJ_Name;
	}
	@Override
	public String toString() {
		return "Dj [DJ_ID=" + DJ_ID + ", DJ_Name=" + DJ_Name + "]";
	}
	
}
