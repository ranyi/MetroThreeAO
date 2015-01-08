package com.example.metrothreeao.entity.repairfault;

public class GzInfo {
	// ����id
	private String gz_id = "";
	// ���Ϸ���ʱ��
	private String gz_time = "";
	// ��·
	private String lx = "";
	// ��·id
	private String lxid = "";
	// վ��
	private String zd = "";
	// վ��id
	private String zdid = "";
	// ���
	private String bianhao = "";
	// ����רҵ ���һ�� ��һ��ȡǰ��λ �ڶ���ȡǰ��λ
	private String gz_zhuanye = "";
	// ����רҵ����
	private String gz_zhuanye_name = "";
	// ��������
	private String gz_content = "";
	// ����״̬
	private String gz_flag = "";
	// ����ʱ��
	private String yc_time = "";
	// ��Ӧʱ��
	private String xy_time = "";
	// ������
	private String Bxr = "";
	// ���޵绰
	private String BxTel = "";
	// Ʒ��
	private String Ph = "";
	// �ͺ�
	private String Xh = "";
	// �ʱ��
	private String TbTime = "";
	// ���
	private String Tbr = "";
	// ���ϵȼ�
	private String Dj = "";

	public GzInfo() {
		super();
	}

	public GzInfo(String gz_id, String gz_time, String lx, String lxid,
			String zd, String zdid, String bianhao, String gz_zhuanye,
			String gz_zhuanye_name, String gz_content, String gz_flag,
			String yc_time, String xy_time, String bxr, String bxTel,
			String ph, String xh, String tbTime, String tbr, String dj) {
		super();
		this.gz_id = gz_id;
		this.gz_time = gz_time;
		this.lx = lx;
		this.lxid = lxid;
		this.zd = zd;
		this.zdid = zdid;
		this.bianhao = bianhao;
		this.gz_zhuanye = gz_zhuanye;
		this.gz_zhuanye_name = gz_zhuanye_name;
		this.gz_content = gz_content;
		this.gz_flag = gz_flag;
		this.yc_time = yc_time;
		this.xy_time = xy_time;
		Bxr = bxr;
		BxTel = bxTel;
		Ph = ph;
		Xh = xh;
		TbTime = tbTime;
		Tbr = tbr;
		Dj = dj;
	}

	public String getGz_id() {
		return gz_id;
	}

	public void setGz_id(String gz_id) {
		this.gz_id = gz_id;
	}

	public String getGz_time() {
		return gz_time;
	}

	public void setGz_time(String gz_time) {
		this.gz_time = gz_time;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getLxid() {
		return lxid;
	}

	public void setLxid(String lxid) {
		this.lxid = lxid;
	}

	public String getZd() {
		return zd;
	}

	public void setZd(String zd) {
		this.zd = zd;
	}

	public String getZdid() {
		return zdid;
	}

	public void setZdid(String zdid) {
		this.zdid = zdid;
	}

	public String getBianhao() {
		return bianhao;
	}

	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}

	public String getGz_zhuanye() {
		return gz_zhuanye;
	}

	public void setGz_zhuanye(String gz_zhuanye) {
		this.gz_zhuanye = gz_zhuanye;
	}

	public String getGz_zhuanye_name() {
		return gz_zhuanye_name;
	}

	public void setGz_zhuanye_name(String gz_zhuanye_name) {
		this.gz_zhuanye_name = gz_zhuanye_name;
	}

	public String getGz_content() {
		return gz_content;
	}

	public void setGz_content(String gz_content) {
		this.gz_content = gz_content;
	}

	public String getGz_flag() {
		return gz_flag;
	}

	public void setGz_flag(String gz_flag) {
		this.gz_flag = gz_flag;
	}

	public String getYc_time() {
		return yc_time;
	}

	public void setYc_time(String yc_time) {
		this.yc_time = yc_time;
	}

	public String getXy_time() {
		return xy_time;
	}

	public void setXy_time(String xy_time) {
		this.xy_time = xy_time;
	}

	public String getBxr() {
		return Bxr;
	}

	public void setBxr(String bxr) {
		Bxr = bxr;
	}

	public String getBxTel() {
		return BxTel;
	}

	public void setBxTel(String bxTel) {
		BxTel = bxTel;
	}

	public String getPh() {
		return Ph;
	}

	public void setPh(String ph) {
		Ph = ph;
	}

	public String getXh() {
		return Xh;
	}

	public void setXh(String xh) {
		Xh = xh;
	}

	public String getTbTime() {
		return TbTime;
	}

	public void setTbTime(String tbTime) {
		TbTime = tbTime;
	}

	public String getTbr() {
		return Tbr;
	}

	public void setTbr(String tbr) {
		Tbr = tbr;
	}

	public String getDj() {
		return Dj;
	}

	public void setDj(String dj) {
		Dj = dj;
	}

	@Override
	public String toString() {
		return "GzInfo [gz_id=" + gz_id + ", gz_time=" + gz_time + ", lx=" + lx
				+ ", lxid=" + lxid + ", zd=" + zd + ", zdid=" + zdid
				+ ", bianhao=" + bianhao + ", gz_zhuanye=" + gz_zhuanye
				+ ", gz_zhuanye_name=" + gz_zhuanye_name + ", gz_content="
				+ gz_content + ", gz_flag=" + gz_flag + ", yc_time=" + yc_time
				+ ", xy_time=" + xy_time + ", Bxr=" + Bxr + ", BxTel=" + BxTel
				+ ", Ph=" + Ph + ", Xh=" + Xh + ", TbTime=" + TbTime + ", Tbr="
				+ Tbr + ", Dj=" + Dj + "]";
	}

}
