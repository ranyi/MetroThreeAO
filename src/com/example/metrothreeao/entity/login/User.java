package com.example.metrothreeao.entity.login;

public class User {
	private String UserID;
	private String UserName;
	private String TrueName;
	private String DepartmentID;
	private String Sex;
	private String Permissions;
	private String Xlid;
	private String Stationid;
	private String XlName;
	private String StationName;

	public User() {
		super();
	}

	public User(String userID, String userName, String trueName,
			String departmentID, String sex, String permissions, String xlid,
			String stationid, String xlName, String stationName) {
		super();
		UserID = userID;
		UserName = userName;
		TrueName = trueName;
		DepartmentID = departmentID;
		Sex = sex;
		Permissions = permissions;
		Xlid = xlid;
		Stationid = stationid;
		XlName = xlName;
		StationName = stationName;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getTrueName() {
		return TrueName;
	}

	public void setTrueName(String trueName) {
		TrueName = trueName;
	}

	public String getDepartmentID() {
		return DepartmentID;
	}

	public void setDepartmentID(String departmentID) {
		DepartmentID = departmentID;
	}

	public String getSex() {
		return Sex;
	}

	public void setSex(String sex) {
		Sex = sex;
	}

	public String getPermissions() {
		return Permissions;
	}

	public void setPermissions(String permissions) {
		Permissions = permissions;
	}

	public String getXlid() {
		return Xlid;
	}

	public void setXlid(String xlid) {
		Xlid = xlid;
	}

	public String getStationid() {
		return Stationid;
	}

	public void setStationid(String stationid) {
		Stationid = stationid;
	}

	public String getXlName() {
		return XlName;
	}

	public void setXlName(String xlName) {
		XlName = xlName;
	}

	public String getStationName() {
		return StationName;
	}

	public void setStationName(String stationName) {
		StationName = stationName;
	}

	@Override
	public String toString() {
		return "User [UserID=" + UserID + ", UserName=" + UserName
				+ ", TrueName=" + TrueName + ", DepartmentID=" + DepartmentID
				+ ", Sex=" + Sex + ", Permissions=" + Permissions + ", Xlid="
				+ Xlid + ", Stationid=" + Stationid + ", XlName=" + XlName
				+ ", StationName=" + StationName + "]";
	}

}
