package com.example.metrothreeao.entity.repairfault;

public class FaultFilterParams {

	private String KsTime="";
	private String JsTime="";
	private String Xl="";
	private String Zd="";
	private String Bxdw="";
	private String Jxdw="";
	private String Dj="";
	private String Flag="";
	private String Lh="";
	private String CxType="";
	private String TimeType="";
	private String Time="";
	private String KeyWord="";
	private String Sb="";
	public FaultFilterParams() {
		super();
	}
	
	public FaultFilterParams(String ksTime, String jsTime, String xl,
			String zd, String bxdw, String jxdw, String dj, String flag,
			String lh, String cxType, String timeType, String time,
			String keyWord, String sb) {
		super();
		KsTime = ksTime;
		JsTime = jsTime;
		Xl = xl;
		Zd = zd;
		Bxdw = bxdw;
		Jxdw = jxdw;
		Dj = dj;
		Flag = flag;
		Lh = lh;
		CxType = cxType;
		TimeType = timeType;
		Time = time;
		KeyWord = keyWord;
		Sb = sb;
	}

	public String getKsTime() {
		return KsTime;
	}
	public void setKsTime(String ksTime) {
		KsTime = ksTime;
	}
	public String getJsTime() {
		return JsTime;
	}
	public void setJsTime(String jsTime) {
		JsTime = jsTime;
	}
	public String getXl() {
		return Xl;
	}
	public void setXl(String xl) {
		Xl = xl;
	}
	public String getZd() {
		return Zd;
	}
	public void setZd(String zd) {
		Zd = zd;
	}
	public String getBxdw() {
		return Bxdw;
	}
	public void setBxdw(String bxdw) {
		Bxdw = bxdw;
	}
	public String getJxdw() {
		return Jxdw;
	}
	public void setJxdw(String jxdw) {
		Jxdw = jxdw;
	}
	public String getDj() {
		return Dj;
	}
	public void setDj(String dj) {
		Dj = dj;
	}
	public String getFlag() {
		return Flag;
	}
	public void setFlag(String flag) {
		Flag = flag;
	}
	public String getLh() {
		return Lh;
	}
	public void setLh(String lh) {
		Lh = lh;
	}
	public String getCxType() {
		return CxType;
	}
	public void setCxType(String cxType) {
		CxType = cxType;
	}
	public String getTimeType() {
		return TimeType;
	}
	public void setTimeType(String timeType) {
		TimeType = timeType;
	}
	public String getTime() {
		return Time;
	}
	public void setTime(String time) {
		Time = time;
	}
	public String getKeyWord() {
		return KeyWord;
	}
	public void setKeyWord(String keyWord) {
		KeyWord = keyWord;
	}
	
	public String getSb() {
		return Sb;
	}

	public void setSb(String sb) {
		Sb = sb;
	}

	@Override
	public String toString() {
		return "FaultFilterParams [KsTime=" + KsTime + ", JsTime=" + JsTime
				+ ", Xl=" + Xl + ", Zd=" + Zd + ", Bxdw=" + Bxdw + ", Jxdw="
				+ Jxdw + ", Dj=" + Dj + ", Flag=" + Flag + ", Lh=" + Lh
				+ ", CxType=" + CxType + ", TimeType=" + TimeType + ", Time="
				+ Time + ", KeyWord=" + KeyWord + ", Sb=" + Sb + "]";
	}

	
	
	
}
