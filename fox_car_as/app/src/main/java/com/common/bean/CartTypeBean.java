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
public class CartTypeBean {

	private String id;
	private String type;
	private String pinpai;
	private String daxiao;

	public CartTypeBean(String id, String type, String pinpai, String daxiao) {
		this.id = id;
		this.type = type;
		this.pinpai = pinpai;
		this.daxiao = daxiao;
	}

	public static ArrayList<CartTypeBean> newInstanceList(String jsonDatas) {
		ArrayList<CartTypeBean> AdvertDatas = new ArrayList<CartTypeBean>();

		try {
			JSONArray arr = new JSONArray(jsonDatas);
			int size = null == arr ? 0 : arr.length();
			System.out.println("size-->" + size);
			for (int i = 0; i < size; i++) {
				JSONObject obj = arr.getJSONObject(i);
				String id = obj.optString("id");
				String type = obj.optString("type");
				String pinpai = obj.optString("pinpai");
				String daxiao = obj.optString("daxiao");




				CartTypeBean bean = new CartTypeBean(id,type,pinpai,daxiao);
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPinpai() {
		return pinpai;
	}

	public void setPinpai(String pinpai) {
		this.pinpai = pinpai;
	}

	public String getDaxiao() {
		return daxiao;
	}

	public void setDaxiao(String daxiao) {
		this.daxiao = daxiao;
	}
	@Override
	public String toString() {
		return pinpai;
	}

}
