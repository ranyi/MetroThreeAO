package com.example.metrothreeao.constant;

public interface HTTPConstant {
	String ERROR = "Error";
	String NAME_SPACE = "http://tempuri.org/";

	 String HOST = "http://192.168.11.102:8888/WebService/";//�޺�
	// String HOST = "http://10.83.0.134:9000/WebService/";
	// String HOST = "http://192.168.11.162:8080/WebService/"; //�Լ�
//	String HOST = "http://192.168.0.9:8080/WebService/";

	String LOGIN_URL = HOST + "ServicesLogin.asmx";
	// ��½
	String LOGIN = "Login";
	// GetXl() �����·
	String GET_XL = "GetXl";
	// GetZdGetByXlid(int xlid) ���վ��
	String GET_STATIONS = "GetZdGetByXlid";
	// GetSb() ����豸
	String GET_SB = "GetSb";
	// GetSbGetbySbid(string Code,string Flag) ����¼��豸
	String GET_SB_BY_SBID = "GetSbGetbySbid";
	// ��ӻ���±�����Ϣ
	// public string InsertGz(string Xl, string Zd, string Time, string ZhuanYe,
	// string Bxr, string TbTel, string Content, string Sblb, string Pp, string
	// Xh,
	// string Tbr, string TbTime, string Type, string Gzid)
	String INSERT_OR_UPDATE_FAULT = "InsertGz";

	// ��ѯ��վ�����б�
	// public string GetListByStation(string depid, string KsTime,
	// string JsTime, string Xl, string Zd, string Bxdw, string Jxdw,
	// string Dj, string Flag, string Lh, string CxType, string TimeType,
	// string Time, string KeyWord, string PageIndex, string PageSize)
	String GET_LIST_BY_STATION = "GetListByStation";

	// ��ȡ���޵�λ
	// public string GetBxdw()
	String GET_BXDW = "GetBxdw";

	// ��ù��ϵȼ�
	// public string GetDj()
	String GET_DJ = "GetDj";

	// ɾ��������Ϣ
	// public string DelByGzid(int gzid)
	String DELETE_GZ_BY_ID = "DelByGzid";

	// ��ȡ��������
	// public string GzInfo(string gz_id)
	String GET_GZINFO = "GzInfo";

	// ��ӽ�չ˵��
	// public string InsertJz(string gz_id, string txtgz_note, string dbren,
	// string tbadder, string tbTime, string lbl_xfTime
	String INSERT_JZ = "InsertJz";
	// ���޹���
	// public string jxgz(string gz_id, string jx_flag, string wxtime, string
	// txtaddtxt, string labbm, string truename, string jxnote, string jjjxnote)
	String TAKE_FAULT = "jxgz";
	// �������
	// public string clgz(string gz_id, string userid, string wxdd, string
	// addtxt, string clnote, string wxdw, string wxfzr, string cctime, string
	// gzyy, string xfid)
	String EXECUTE_FAULT = "clgz";
	// ȷ�Ϲ���
	// public string qrgz(string type, string gz_id, string adder, string
	// addertxt, string qr_note, string jjadder, string jjdbr, string
	// qr_jjnote,string dctime)
	String CONFIRM_REPAIR = "qrgz";
}
