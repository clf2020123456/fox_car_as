/**
 *
 * PackageName:net.shopnc.android.model
 * FileNmae:GoodsList.java
 */
package com.common.bean;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.TextView;

/**
 * @author KingKong·HE
 */
public class DuanziBean {
	public static class Attr {
		/**
		 * 
		 * TextView ck_name; TextView ck_phone; TextView ck_status; TextView
		 * ck_group; TextView ck_gws; TextView ck_content;
		 */
		public static final String TYPE_ID = "id";
		public static final String TYPE_NAME = "title";
		public static final String TYPE_STATUS = "status";
		public static final String TYPE_STATUS2 = "status2";
		public static final String TYPE_ZAN = "zan";
		public static final String TYPE_ZUOBIAO = "content";
		public static final String TYPE_AUTHOR = "author";
		public static final String TYPE_IMAGE = "image_url";
		public static final String TYPE_DESC = "pubdate";
		public static final String TYPE_TYPE2 = "type2";
		public static final String TYPE_PRICE = "price";
		public static final String TYPE_LONG = "longitude";
		public static final String TYPE_LATI = "latitude";
		public static final String TYPE_PHONE = "ck_phone";
		public static final String TYPE_GET = "get_username";
		public static final String TYPE_ADDUSERID = "add_userid";

		public static final String TYPE_ORDERNO = "order_no";
		public static final String TYPE_RECEIVER = "receiver";
		public static final String TYPE_SENDER = "sender";
		public static final String TYPE_FROM_ADDRESS = "from_address";
		public static final String TYPE_TO_ADDRESS = "to_address";

	}

	private String id;
	private String title;
	private String content;
	private String status;
	private String status2;
	private String zan;
	private String author;
	private String image_url;
	private String updatetime;
	private String type2;
	private String price;
	private String longitude;
	private String latitude;
	private String ck_phone;
	private String get_username;
	private String add_userid;

	private String order_no;
	private String receiver;
	private String sender;
	private String from_address;
	private String to_address;

	public DuanziBean() {
	}

	public DuanziBean(String id, String title, String content, String status,
			String zan, String author, String image_url, String updatetime,
			String type2, String price, String longitude, String latitude,String ck_phone,String get_username,String status2
			,String add_userid
			,String order_no
			,String receiver
			,String sender
			,String from_address
			,String to_address
	) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.status = status;
		this.zan = zan;
		this.author = author;
		this.image_url = image_url;
		this.updatetime = updatetime;
		this.type2 = type2;
		this.price = price;
		this.longitude = longitude;
		this.latitude = latitude;
		this.ck_phone = ck_phone;
		this.get_username = get_username;
		this.status2 = status2;
		this.add_userid = add_userid;

		this.order_no = order_no;
		this.receiver = receiver;
		this.sender = sender;
		this.from_address = from_address;
		this.to_address = to_address;
	}

	public static ArrayList<DuanziBean> newInstanceList(String jsonDatas) {
		ArrayList<DuanziBean> AdvertDatas = new ArrayList<DuanziBean>();

		try {
			JSONArray arr = new JSONArray(jsonDatas);
			int size = null == arr ? 0 : arr.length();
			System.out.println("size-->" + size);
			for (int i = 0; i < size; i++) {
				JSONObject obj = arr.getJSONObject(i);
				String type_id = obj.optString(Attr.TYPE_ID);
				String title = obj.optString(Attr.TYPE_NAME);
				String content = obj.optString(Attr.TYPE_ZUOBIAO);
				String type_image = obj.optString(Attr.TYPE_IMAGE);
				String updatetime = obj.optString(Attr.TYPE_DESC);
				String status = obj.optString(Attr.TYPE_STATUS);
				String zan = obj.optString(Attr.TYPE_ZAN);
				String author = obj.optString(Attr.TYPE_AUTHOR);

				String type2 = obj.optString(Attr.TYPE_TYPE2);
				String price = obj.optString(Attr.TYPE_PRICE);
				String longitude = obj.optString(Attr.TYPE_LONG);
				String latitude = obj.optString(Attr.TYPE_LATI);
				String ck_phone = obj.optString(Attr.TYPE_PHONE);
				String get_username = obj.optString(Attr.TYPE_GET);
				String status2 = obj.optString(Attr.TYPE_STATUS2);
				String add_userid = obj.optString(Attr.TYPE_ADDUSERID);

//				public static final String TYPE_ORDERNO = "order_no";
//				public static final String TYPE_RECEIVER = "receiver";
//				public static final String TYPE_SENDER = "sender";
//				public static final String TYPE_FROM_ADDRESS = "from_address";
//				public static final String TYPE_TO_ADDRESS = "to_address";

				String order_no = obj.optString(Attr.TYPE_ORDERNO);
				String receiver = obj.optString(Attr.TYPE_RECEIVER);
				String sender = obj.optString(Attr.TYPE_SENDER);
				String from_address = obj.optString(Attr.TYPE_FROM_ADDRESS);
				String to_address = obj.optString(Attr.TYPE_TO_ADDRESS);

				DuanziBean bean = new DuanziBean(type_id, title, content,
						status, zan, author, type_image, updatetime, type2,
						price, longitude, latitude,ck_phone,get_username,status2,add_userid,order_no,receiver,sender,from_address,to_address);
				AdvertDatas.add(bean);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return AdvertDatas;
	}

	public String getAdd_userid() {
		return add_userid;
	}

	public void setAdd_userid(String add_userid) {
		this.add_userid = add_userid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType2() {
		return type2;
	}

	public void setType2(String type2) {
		this.type2 = type2;
	}

	public String getPrice() {
		return price;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getZan() {
		return zan;
	}

	public void setZan(String zan) {
		this.zan = zan;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCk_phone() {
		return ck_phone;
	}

	public String getGet_username() {
		return get_username;
	}

	public void setGet_username(String get_username) {
		this.get_username = get_username;
	}

	public void setCk_phone(String ck_phone) {
		this.ck_phone = ck_phone;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getFrom_address() {
		return from_address;
	}

	public void setFrom_address(String from_address) {
		this.from_address = from_address;
	}

	public String getTo_address() {
		return to_address;
	}

	public void setTo_address(String to_address) {
		this.to_address = to_address;
	}
}
