package com.common.util;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class HttpUtil {
	public static final String URL_BASE = "http://192.168.1.3:8080/fox_car/";

	public static final String URL_LOGIN = URL_BASE + "user_login.action?";
	public static final String URL_REGISTER = URL_BASE + "user_reg.action?";
	public static final String URL_UPDATEPWD = URL_BASE + "user_update_pwd.action?";
	public static final String URL_UPDATE = URL_BASE + "user_modify.action?";
	public static final String URL_SAVEPROJ = URL_BASE + "biotech_saveproj.action?";
	public static final String URL_SAVECK = URL_BASE + "biotech_saveck.action?";
	public static final String URL_DUANZILIST = URL_BASE + "biotech_listjson0.action";
	public static final String URL_DUANZILIST0 = URL_BASE + "biotech_loadAllJson0_0.action";
	public static final String URL_DUANZILIST_TYPE = URL_BASE + "biotech_listbytype.action?type=";
	public static final String URL_LISTSHENHE0 = URL_BASE + "biotech_list_shenhe0.action";
	public static final String URL_LISTCUNCHE0 = URL_BASE + "fox_list1_.action";
	public static final String URL_LISTCUNCHE_MY = URL_BASE + "fox_listbyuser1.action?userid=";
	public static final String URL_LISTZuCHE_MY = URL_BASE + "fox_listbyuser2.action?userid=";

	public static final String URL_LIST_TYPE = URL_BASE + "biotech_listbytype.action?type=";


	public static final String URL_LISTSHENHE1 = URL_BASE + "biotech_list_shenhe1_daifenpei.action";
	public static final String URL_DUANZILIST1 = URL_BASE + "biotech_loadAllJson0_1.action?userid=";
	public static final String URL_DUANZILIST2 = URL_BASE + "biotech_loadAllJson0_2.action?userid=";
	public static final String URL_JIEDAN = URL_BASE + "biotech_udpate_jiedan.action?";
	public static final String URL_SHENHE = URL_BASE + "biotech_shenhe_client.action?";
	public static final String URL_JIESUAN_ = URL_BASE + "fox_jiesuan2_.action?zuche.id=";
	public static final String URL_JIESUAN1_ = URL_BASE + "fox_jiesuan1.action?cunche.id=";
	public static final String URL_DEL1_ = URL_BASE + "fox_del1_.action?cunche.id=";
	public static final String URL_DEL2_ = URL_BASE + "fox_del2_.action?zuche.id=";
	public static final String URL_WANCHENG = URL_BASE + "biotech_udpate_wancheng.action?";
	public static final String URL_JIESUAN = URL_BASE + "user_udpate_jiesuan.action?";
	public static final String URL_XINWENLIST = URL_BASE + "biotech_listjson1.action";
	public static final String URL_PROJLIST = URL_BASE + "biotech_listjson2.action";
	public static final String URL_CKLIST = URL_BASE + "biotech_listjson3.action";
	public static final String URL_MESSAGELIST = URL_BASE + "comments_listmsgjson?userid=";
	public static final String URL_USERLIST2 = URL_BASE + "user_listjson2.action";
	public static final String URL_USERLIST2_0 = URL_BASE + "user_listjson2_0.action";
	public static final String URL_FAXIANLIST = URL_BASE + "biotech_listjson1.action";
	public static final String URL_FRIENDSLIST = URL_BASE + "user_listjson.action";
	public static final String URL_DUANZILISTUSER = URL_BASE + "biotech_listjsonbyuser.action?";
	public static final String URL_DUANZILISTUSER0 = URL_BASE + "biotech_listjsonbyuser0.action?";
	public static final String URL_DUANZILISTUSER1 = URL_BASE + "biotech_listjsonbyuser1.action?";
	public static final String URL_DUANZILISTUSER2 = URL_BASE + "biotech_listjsonbyuser2.action?";
	public static final String URL_DUANZILISTUSER3 = URL_BASE + "biotech_listjsonbyuser3.action?";
	public static final String URL_UPDATEADDRESS = URL_BASE + "user_update_address.action?";
	public static final String URL_DUANZILISTUSERFOLDER = URL_BASE + "biotech_listjsonbyfolder.action?userid=";
	public static final String URL_BASEUPLOAD = URL_BASE + "upload/";
	public static final String URL_BIO_ADD = URL_BASE
			+ "biotech_uploadarticle.action?";
		public static final String URL_CUNCHE = URL_BASE
			+ "fox_save1_.action?";
		public static final String URL_ZUCHE = URL_BASE
			+ "fox_save2_.action?";
	public static final String URL_BIO_ADD1 = URL_BASE
			+ "biotech_uploadarticle1.action?";
	public static final String URL_PHOTO_ADD = URL_BASE
			+ "user_uploadphoto.action?";
	public static final String URL_DELFOLDER = URL_BASE + "biotech_delfolder.action?";

	public static final String URL_BIODETAIL = URL_BASE
			+ "biotech_detailjson.action?id=";
	public static final String URL_ZANDIAN = URL_BASE
			+ "biotech_dianzan.action?biotech.id=";
	public static final String URL_ZANDIAN_ = URL_BASE
			+ "biotech_dianzan_.action?biotech.id=";
	public static final String URL_DEL = URL_BASE
			+ "biotech_del.action?biotech.id=";
	public static final String URL_SHOUCANG = URL_BASE
			+ "biotech_addfolder.action?";
	public static final String URL_COMMENTSADD = URL_BASE
			+ "comments_save.action?";
	public static final String URL_CHONGZHI = URL_BASE
			+ "user_chongzhi_.action?";
	public static final String URL_TIXIAN = URL_BASE
			+ "user_tixian.action?";
	public static final String URL_MESSAGESADD = URL_BASE
			+ "comments_savemsg.action?";
	public static final String URL_DUANZICOMMENTS = URL_BASE
			+ "comments_listjson.action?luxianid=";
	public static final String URL_LOAD = URL_BASE + "user_load.action?";
	public static final String URL_LOADADDRESS = "http://api.map.baidu.com/geocoder?ak=RLXx7wEbmyzcFTYsuxOeXjK5Vsh7Fj1c&output=json&location=";
	// public static final String BASE_URL="http://192.168.0.164:8080/kycheck/";




	public static final String CartTypeLIST = URL_BASE + "type_list1_.action";
	// 获得Get请求对象request
	public static HttpGet getHttpGet(String url) {
		HttpGet request = new HttpGet(url);
		return request;
	}

	// 获得Post请求对象request
	public static HttpPost getHttpPost(String url) {
		HttpPost request = new HttpPost(url);
		return request;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpGet request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}

	// 根据请求获得响应对象response
	public static HttpResponse getHttpResponse(HttpPost request)
			throws ClientProtocolException, IOException {
		HttpResponse response = new DefaultHttpClient().execute(request);
		return response;
	}
	// 发送Post请求，获得响应查询结果
	public static String queryStringConnectForPost(String url) {
		// request method is POST

		String charset = "UTF-8";
		HttpURLConnection conn = null;
		//DataOutputStream wr;
		StringBuilder result = null;
		URL urlObj;
		JSONObject jObj = null;
		StringBuilder sbParams;
		String paramsString;


		try {
			urlObj = new URL(url);
			conn = (HttpURLConnection) urlObj.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Accept-Charset", charset);
			conn.setReadTimeout(10000);
			conn.setConnectTimeout(15000);
		    conn.connect();




			//wr = new DataOutputStream(conn.getOutputStream());

			//wr.writeBytes();

			//wr.flush();

			//wr.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			//Receive the response from the server
			InputStream in = new BufferedInputStream(conn.getInputStream());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
			Log.d("JSON Parser", "result: " + result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}



		conn.disconnect();



		return result.toString();
	}
	// 发送Post请求，获得响应查询结果
	public static String queryStringForPost(String url) {
		// 根据url获得HttpPost对象
		HttpPost request = HttpUtil.getHttpPost(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	// 获得响应查询结果
	public static String queryStringForPost(HttpPost request) {
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}

	// 发送Get请求，获得响应查询结果
	public static String queryStringForGet(String url) {
		// 获得HttpGet对象
		HttpGet request = HttpUtil.getHttpGet(url);
		String result = null;
		try {
			// 获得响应对象
			HttpResponse response = HttpUtil.getHttpResponse(request);
			// 判断是否请求成功
			if (response.getStatusLine().getStatusCode() == 200) {
				// 获得响应
				result = EntityUtils.toString(response.getEntity());
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			result = "网络异常！";
			return result;
		}
		return null;
	}
}
