/**
 *
 * PackageName:net.shopnc.android.model
 * FileNmae:Login.java
 */
package com.common.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author KingKong·HE
 * @Time 年10月17日 下午4:44:35
 */
public class LoginBean {
		public static class Attr{
			public static final String KEY = "key";
			public static final String USERNAME = "username";
			public static final String PWD = "password";
			public static final String ZAN_ = "zan_";
			public static final String STATUS = "status";
		}
		
		private String key;
		private String username;
		private String password;
		private String zan_;
		private String status;

		public LoginBean() {
		}


		public LoginBean(String key, String username, String password, String zan_, String status) {
			super();
			this.key = key;
			this.username = username;
			this.password = password;
			this.zan_ = zan_;
			this.status = status;
		}


		public static LoginBean  newInstanceList(String json){
			LoginBean bean = null;
			try {
				JSONObject obj = new JSONObject(json);
				String userstr = obj.optString("jsonString");
				JSONObject user = new JSONObject(userstr);
				
				
//				System.out.println(obj.optString("username"));
				if(obj.length()> 0){
					String username = user.optString("username");
					String key = user.optString("id");
					String password = user.optString("password");
					String zan_ = user.optString("zan_");
					String status = user.optString("status");
					 bean = new LoginBean(key, username,password,zan_,status);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return bean;
		}


		public String getKey() {
			return key;
		}


		public void setKey(String key) {
			this.key = key;
		}


		public String getUsername() {
			return username;
		}


		public void setUsername(String username) {
			this.username = username;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public String getZan_() {
			return zan_;
		}


		public void setZan_(String zan_) {
			this.zan_ = zan_;
		}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
		public String toString() {
			return "Login [key=" + key + ", username=" + username + "]";
		}
		
}
