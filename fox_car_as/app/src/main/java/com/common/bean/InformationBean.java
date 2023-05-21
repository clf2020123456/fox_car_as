package com.common.bean;
// default package


public class InformationBean implements java.io.Serializable {

	// Fields

	private Integer informationid;
	private String intitle;
	private String incontext;
	private String intime;
	public Integer getInformationid() {
		return informationid;
	}
	public void setInformationid(Integer informationid) {
		this.informationid = informationid;
	}
	public String getIntitle() {
		return intitle;
	}
	public void setIntitle(String intitle) {
		this.intitle = intitle;
	}
	public String getIncontext() {
		return incontext;
	}
	public void setIncontext(String incontext) {
		this.incontext = incontext;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	
	public InformationBean() {
		super();
	}

}