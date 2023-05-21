package com.common.bean;

public class FoundingfriendsBean implements java.io.Serializable {

	// Fields

	private Integer ffid;
	private String  userlogin;
	private String ffcount;
	private String fftime;
	public Integer getFfid() {
		return ffid;
	}
	public void setFfid(Integer ffid) {
		this.ffid = ffid;
	}
	public String getUserlogin() {
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}
	public String getFfcount() {
		return ffcount;
	}
	public void setFfcount(String ffcount) {
		this.ffcount = ffcount;
	}
	public String getFftime() {
		return fftime;
	}
	public void setFftime(String fftime) {
		this.fftime = fftime;
	}
	
}