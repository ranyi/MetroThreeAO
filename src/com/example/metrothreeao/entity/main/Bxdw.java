package com.example.metrothreeao.entity.main;

/**
 * ±¨ÐÞµ¥Î»
 * @author acer
 *
 */
public class Bxdw {
	private String co_id;
	private String company;
	public Bxdw() {
		super();
	}
	public Bxdw(String co_id, String company) {
		super();
		this.co_id = co_id;
		this.company = company;
	}
	public String getCo_id() {
		return co_id;
	}
	public void setCo_id(String co_id) {
		this.co_id = co_id;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Override
	public String toString() {
		return "Bxdw [co_id=" + co_id + ", company=" + company + "]";
	}
	
	
}
