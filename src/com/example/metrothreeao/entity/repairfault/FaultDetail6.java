package com.example.metrothreeao.entity.repairfault;

public class FaultDetail6 {
	private String xietiaojilu;
	private String xietiaoqingkuang;
	private String tianxieyonghu;
	private String tianxieshijian;
	private String dangbanren;

	public FaultDetail6() {
		super();
	}

	public FaultDetail6(String xietiaojilu, String xietiaoqingkuang,
			String tianxieyonghu, String tianxieshijian, String dangbanren) {
		super();
		this.xietiaojilu = xietiaojilu;
		this.xietiaoqingkuang = xietiaoqingkuang;
		this.tianxieyonghu = tianxieyonghu;
		this.tianxieshijian = tianxieshijian;
		this.dangbanren = dangbanren;
	}

	public String getXietiaojilu() {
		return xietiaojilu;
	}

	public void setXietiaojilu(String xietiaojilu) {
		this.xietiaojilu = xietiaojilu;
	}

	public String getXietiaoqingkuang() {
		return xietiaoqingkuang;
	}

	public void setXietiaoqingkuang(String xietiaoqingkuang) {
		this.xietiaoqingkuang = xietiaoqingkuang;
	}

	public String getTianxieyonghu() {
		return tianxieyonghu;
	}

	public void setTianxieyonghu(String tianxieyonghu) {
		this.tianxieyonghu = tianxieyonghu;
	}

	public String getTianxieshijian() {
		return tianxieshijian;
	}

	public void setTianxieshijian(String tianxieshijian) {
		this.tianxieshijian = tianxieshijian;
	}

	public String getDangbanren() {
		return dangbanren;
	}

	public void setDangbanren(String dangbanren) {
		this.dangbanren = dangbanren;
	}

	@Override
	public String toString() {
		return "FaultDetail6 [xietiaojilu=" + xietiaojilu
				+ ", xietiaoqingkuang=" + xietiaoqingkuang + ", tianxieyonghu="
				+ tianxieyonghu + ", tianxieshijian=" + tianxieshijian
				+ ", dangbanren=" + dangbanren + "]";
	}

}
