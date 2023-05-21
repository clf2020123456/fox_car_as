package com.common.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;

public class SendGETRequest {
	/**
	 * 提交数据到服务器 并且返回取出的值
	 * 
	 * @param path
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> sendGETRequest(String path, String params)
			throws Exception {
		Map<String, String> tempMap = null;

		boolean b = false;
		Log.v("url", path + params);
		//params=URLEncoder.encode(params,"UTF-8");
		HttpURLConnection conn = (HttpURLConnection) new URL(path + params)
				.openConnection();
		// conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "text/xml");
		conn.setRequestProperty("Charset", "UTF-8");
		// conn.setRequestProperty("Cookie", Constant.youxinCookie);
		conn.setConnectTimeout(20000);
		// 如果请求响应码是200，则表示成功
		if (conn.getResponseCode() == 200) {
			System.out.println(conn.getResponseCode());
			// 获得服务器响应的数据
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			// 数据
			String retData = null;
			String responseData = "";
			while ((retData = in.readLine()) != null) {
				responseData += retData;
			}
			in.close();
			System.out.println(responseData.toString());
			tempMap = JsonUtil.getJosn(responseData);
			return tempMap;
		}
		// tempMap.put("innerEorr", "服务器响应超时");
		return tempMap;
	}
	
	/**
	 * 提交数据到服务器 并且返回取出的值
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> sendGETRequest(String path)
			throws Exception {
		Map<String, String> tempMap = null;

		boolean b = false;
		HttpURLConnection conn = (HttpURLConnection) new URL(path)
				.openConnection();
		// conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "text/xml");
		conn.setRequestProperty("Charset", "UTF-8");
		// conn.setRequestProperty("Cookie", Constant.youxinCookie);
		conn.setConnectTimeout(20000);
		// 如果请求响应码是200，则表示成功
		if (conn.getResponseCode() == 200) {
			System.out.println(conn.getResponseCode());
			// 获得服务器响应的数据
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			// 数据
			String retData = null;
			String responseData = "";
			while ((retData = in.readLine()) != null) {
				responseData += retData;
			}
			in.close();
			System.out.println(responseData.toString());
			tempMap = JsonUtil.getJosn(responseData);
			return tempMap;
		}
		// tempMap.put("innerEorr", "服务器响应超时");
		return tempMap;
	}
	
}
