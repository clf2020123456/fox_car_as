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
 */
public class CommentsList {
		public static class Attr{
			public static final String COMMENT_USERNAME = "username";
			public static final String COMMENT_DATE = "cdate";
			public static final String COMMENT_COMENT = "content";
		
		}
		private String username;
		private String commitdate;
		private String content;
		
		public CommentsList() {
		}
		














		public CommentsList(String username, String commitdate, String content) {
			super();
			this.username = username;
			this.commitdate = commitdate;
			this.content = content;
		}















		public static ArrayList<CommentsList> newInstanceList(String jsonDatas){
			ArrayList<CommentsList> AdvertDatas = new ArrayList<CommentsList>();
			
			try {
				JSONArray arr = new JSONArray(jsonDatas);
				int size = null == arr ? 0 : arr.length();
				System.out.println("size-->" + size);
				for(int i = 0; i < size; i++){
					JSONObject obj = arr.getJSONObject(i);
					String username = obj.optString(Attr.COMMENT_USERNAME);
					String commitdate = obj.optString(Attr.COMMENT_DATE);
					String content = obj.optString(Attr.COMMENT_COMENT);
					CommentsList bean =new CommentsList(username,commitdate,content);
					AdvertDatas.add(bean);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return AdvertDatas;
		}















		public String getUsername() {
			return username;
		}















		public void setUsername(String username) {
			this.username = username;
		}















		public String getCommitdate() {
			return commitdate;
		}















		public void setCommitdate(String commitdate) {
			this.commitdate = commitdate;
		}















		public String getContent() {
			return content;
		}















		public void setContent(String content) {
			this.content = content;
		}















		@Override
		public String toString() {
			return "CommentsList [username=" + username + ", commitdate="
					+ commitdate + ", content=" + content + "]";
		}















		

}
