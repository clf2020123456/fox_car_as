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

/**
 * @author KingKong·HE
 * @Time 年10月17日 下午4:44:35
 */
public class FriendBean {
		public static class Attr{
			public static final String TYPE_ID = "id";
			public static final String TYPE_NAME = "name";
			public static final String TYPE_QQ = "qqnum";
			public static final String TYPE_PHONE = "phone";
			public static final String TYPE_ADDRESS = "address";
			public static final String TYPE_STATUS = "status";
			public static final String TYPE_CAR_STATUS = "car_status";

		}
		private String id;
		private String name;
		private String qqnum;
		private String phone;
		private String address;
		private String status;
		private String car_status;

		public FriendBean() {
		}
		





		public FriendBean(String id, String name, String qqnum, String phone,String address,String status,String car_status) {
			super();
			this.id = id;
			this.name = name;
			this.qqnum = qqnum;
			this.phone = phone;
			this.address = address;
			this.status = status;
			this.car_status = car_status;
		}






		public static ArrayList<FriendBean> newInstanceList(String jsonDatas){
			ArrayList<FriendBean> AdvertDatas = new ArrayList<FriendBean>();
			
			try {
				JSONArray arr = new JSONArray(jsonDatas);
				int size = null == arr ? 0 : arr.length();
				System.out.println("size-->" + size);
				for(int i = 0; i < size; i++){
					JSONObject obj = arr.getJSONObject(i);
					String type_id = obj.optString(Attr.TYPE_ID);
					String name = obj.optString(Attr.TYPE_NAME);
					String qq = obj.optString(Attr.TYPE_QQ);
					String phone = obj.optString(Attr.TYPE_PHONE);
					String address = obj.optString(Attr.TYPE_ADDRESS);
					String status = obj.optString(Attr.TYPE_STATUS);
					String car_status = obj.optString(Attr.TYPE_CAR_STATUS);
					FriendBean bean =new FriendBean(type_id,name,qq,phone,address,status,car_status);
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






		public String getName() {
			return name;
		}






		public void setName(String name) {
			this.name = name;
		}






		public String getQqnum() {
			return qqnum;
		}






		public void setQqnum(String qqnum) {
			this.qqnum = qqnum;
		}






		public String getPhone() {
			return phone;
		}






		public void setPhone(String phone) {
			this.phone = phone;
		}






		public String getAddress() {
			return address;
		}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCar_status() {
		return car_status;
	}

	public void setCar_status(String car_status) {
		this.car_status = car_status;
	}

	public void setAddress(String address) {
			this.address = address;
		}



		

}
