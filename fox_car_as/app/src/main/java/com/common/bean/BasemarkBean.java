package com.common.bean;

public class BasemarkBean implements java.io.Serializable {

	// Fields

	private Integer basemarkid;
	private String gamename;
	private String basemarks;
	private String basemarktime;
	private String realize;
	public Integer getBasemarkid() {
		return basemarkid;
	}
	public void setBasemarkid(Integer basemarkid) {
		this.basemarkid = basemarkid;
	}
	public String getGamename() {
		return gamename;
	}
	public void setGamename(String gamename) {
		this.gamename = gamename;
	}
	public String getBasemarks() {
		return basemarks;
	}
	public void setBasemarks(String basemarks) {
		this.basemarks = basemarks;
	}
	public String getBasemarktime() {
		return basemarktime;
	}
	public void setBasemarktime(String basemarktime) {
		this.basemarktime = basemarktime;
	}
	public String getRealize() {
		return realize;
	}
	public void setRealize(String realize) {
		this.realize = realize;
	}

}