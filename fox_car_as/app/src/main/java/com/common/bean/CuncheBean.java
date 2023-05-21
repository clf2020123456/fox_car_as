/**
 *
 * PackageName:net.shopnc.android.model
 * FileNmae:GoodsList.java
 */
package com.common.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author KingKong·HE
 */
public class CuncheBean {
	public static class Attr {
		/**
		 *
		 * TextView ck_name; TextView ck_phone; TextView ck_status; TextView
		 * ck_group; TextView ck_gws; TextView ck_content;
		 */
		public static final String TYPE_ID = "id";
		public static final String TYPE_NAME = "car_no";
		public static final String TYPE_STATUS = "status";
		public static final String TYPE_START = "cunche_start_date_str";
		public static final String TYPE_END = "cunche_end_date_str";
		public static final String TYPE_MONEY = "money";
		public static final String TYPE_MONEY2 = "money2";
		public static final String TYPE_AUTHOR = "username";
		public static final String TYPE_DAYS = "days";
		public static final String TYPE_IMAGE = "image_url";

	}

	private String id;
	private String car_no;
	private String cunche_start_date_str;
	private String cunche_end_date_str;
	private String status;
	private String username;
	private String money;
	private String money2;
	private String days;
	private String image_url;

	public CuncheBean() {
	}

	public CuncheBean(String id, String car_no,  String cunche_start_date_str, String cunche_end_date_str, String status, String username, String money, String days, String image_url, String money2) {
		this.id = id;
		this.car_no = car_no;
		this.cunche_start_date_str = cunche_start_date_str;
		this.cunche_end_date_str = cunche_end_date_str;
		this.status = status;
		this.username = username;
		this.money = money;
		this.days = days;
		this.image_url = image_url;
		this.money2 = money2;
	}

	public static ArrayList<CuncheBean> newInstanceList(String jsonDatas) {
		ArrayList<CuncheBean> AdvertDatas = new ArrayList<CuncheBean>();

		try {
			JSONArray arr = new JSONArray(jsonDatas);
			int size = null == arr ? 0 : arr.length();
			System.out.println("size-->" + size);
			for (int i = 0; i < size; i++) {
				JSONObject obj = arr.getJSONObject(i);
				String type_id = obj.optString(Attr.TYPE_ID);
				String car_no = obj.optString(Attr.TYPE_NAME);
				String cunche_start_date_str = obj.optString(Attr.TYPE_START);
				String cunche_end_date_str = obj.optString(Attr.TYPE_END);
				String status = obj.optString(Attr.TYPE_STATUS);
				String username = obj.optString(Attr.TYPE_AUTHOR);
				String money = obj.optString(Attr.TYPE_MONEY);
				String days = obj.optString(Attr.TYPE_DAYS);
				String image_url = obj.optString(Attr.TYPE_IMAGE);
				String money2 = obj.optString(Attr.TYPE_MONEY2);



				CuncheBean bean = new CuncheBean(type_id,car_no,cunche_start_date_str,cunche_end_date_str,status,username,money,days,image_url,money2);
				AdvertDatas.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return AdvertDatas;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCar_no() {
		return car_no;
	}

	public void setCar_no(String car_no) {
		this.car_no = car_no;
	}

	public String getCunche_start_date_str() {
		return cunche_start_date_str;
	}

	public void setCunche_start_date_str(String cunche_start_date_str) {
		this.cunche_start_date_str = cunche_start_date_str;
	}

	public String getCunche_end_date_str() {
		return cunche_end_date_str;
	}

	public void setCunche_end_date_str(String cunche_end_date_str) {
		this.cunche_end_date_str = cunche_end_date_str;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getMoney2() {
		return money2;
	}

	public void setMoney2(String money2) {
		this.money2 = money2;
	}
}
