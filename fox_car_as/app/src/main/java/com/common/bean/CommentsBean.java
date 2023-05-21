package com.common.bean;
// default package

public class CommentsBean implements java.io.Serializable {

	// Fields

	private Integer commentsid;
	private String commentscount;
	private String commentstime;
	public Integer getCommentsid() {
		return commentsid;
	}
	public void setCommentsid(Integer commentsid) {
		this.commentsid = commentsid;
	}
	public String getCommentscount() {
		return commentscount;
	}
	public void setCommentscount(String commentscount) {
		this.commentscount = commentscount;
	}
	public String getCommentstime() {
		return commentstime;
	}
	public void setCommentstime(String commentstime) {
		this.commentstime = commentstime;
	}

}