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
public class FaxianBean {
		public static class Attr{
			public static final String TYPE_ID = "id";
			public static final String TYPE_NAME = "name";
			public static final String TYPE_ZUOBIAO = "luxian";
			public static final String TYPE_IMAGE = "image_url";
		
		}
		private String id;
		private String name;
		private String luxian;
		private String image_url;
		
		public FaxianBean() {
		}
		


		public FaxianBean(String id, String name, String luxian,
				String image_url) {
			super();
			this.id = id;
			this.name = name;
			this.luxian = luxian;
			this.image_url = image_url;
		}



		public static ArrayList<FaxianBean> newInstanceList(String jsonDatas){
			ArrayList<FaxianBean> AdvertDatas = new ArrayList<FaxianBean>();
			
			try {
				JSONArray arr = new JSONArray(jsonDatas);
				int size = null == arr ? 0 : arr.length();
				System.out.println("size-->" + size);
				for(int i = 0; i < size; i++){
					JSONObject obj = arr.getJSONObject(i);
					String type_id = obj.optString(Attr.TYPE_ID);
					String title = obj.optString(Attr.TYPE_NAME);
					String content = obj.optString(Attr.TYPE_ZUOBIAO);
					String type_image = obj.optString(Attr.TYPE_IMAGE);
					FaxianBean bean =new FaxianBean(type_id,title,content,type_image);
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



		public String getLuxian() {
			return luxian;
		}



		public void setLuxian(String luxian) {
			this.luxian = luxian;
		}



		public String getImage_url() {
			return image_url;
		}



		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}
















		

}
