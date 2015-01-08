package com.example.metrothreeao.entity.repairfault;

public class FaultDetail5 {
	private String bihuanxinxi;
	private String querenqingkuang;
	private String caozuoyonghu;
	private String caozuoshijian;
	private String dangbanren;

	public FaultDetail5() {
		super();
	}

	public FaultDetail5(String bihuanxinxi, String querenqingkuang,
			String caozuoyonghu, String caozuoshijian, String dangbanren) {
		super();
		this.bihuanxinxi = bihuanxinxi;
		this.querenqingkuang = querenqingkuang;
		this.caozuoyonghu = caozuoyonghu;
		this.caozuoshijian = caozuoshijian;
		this.dangbanren = dangbanren;
	}

	public String getBihuanxinxi() {
		return bihuanxinxi;
	}

	public void setBihuanxinxi(String bihuanxinxi) {
		this.bihuanxinxi = bihuanxinxi;
	}

	public String getQuerenqingkuang() {
		return querenqingkuang;
	}

	public void setQuerenqingkuang(String querenqingkuang) {
		this.querenqingkuang = querenqingkuang;
	}

	public String getCaozuoyonghu() {
		return caozuoyonghu;
	}

	public void setCaozuoyonghu(String caozuoyonghu) {
		this.caozuoyonghu = caozuoyonghu;
	}

	public String getCaozuoshijian() {
		return caozuoshijian;
	}

	public void setCaozuoshijian(String caozuoshijian) {
		this.caozuoshijian = caozuoshijian;
	}

	public String getDangbanren() {
		return dangbanren;
	}

	public void setDangbanren(String dangbanren) {
		this.dangbanren = dangbanren;
	}

	@Override
	public String toString() {
		return "FaultDetail5 [bihuanxinxi=" + bihuanxinxi
				+ ", querenqingkuang=" + querenqingkuang + ", caozuoyonghu="
				+ caozuoyonghu + ", caozuoshijian=" + caozuoshijian
				+ ", dangbanren=" + dangbanren + "]";
	}
	
	

}
