package com.common.bean;

public class ExchangeBean implements java.io.Serializable {

	// Fields

	private Integer exchangeid;
	private String userlogin;
	private String exchangecount;
	private String exchangetime;
	public Integer getExchangeid() {
		return exchangeid;
	}
	public void setExchangeid(Integer exchangeid) {
		this.exchangeid = exchangeid;
	}
	public String getUserlogin() {
		return userlogin;
	}
	public void setUserlogin(String userlogin) {
		this.userlogin = userlogin;
	}
	public String getExchangecount() {
		return exchangecount;
	}
	public void setExchangecount(String exchangecount) {
		this.exchangecount = exchangecount;
	}
	public String getExchangetime() {
		return exchangetime;
	}
	public void setExchangetime(String exchangetime) {
		this.exchangetime = exchangetime;
	}
	
}