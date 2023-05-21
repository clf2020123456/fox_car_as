package com.common.bean;

public class PicturesBean implements java.io.Serializable {

	// Fields

	private Integer pictureid;
	private String picturename;
	private String picturetime;
	public Integer getPictureid() {
		return pictureid;
	}
	public void setPictureid(Integer pictureid) {
		this.pictureid = pictureid;
	}
	public String getPicturename() {
		return picturename;
	}
	public void setPicturename(String picturename) {
		this.picturename = picturename;
	}
	public String getPicturetime() {
		return picturetime;
	}
	public void setPicturetime(String picturetime) {
		this.picturetime = picturetime;
	}
	
}