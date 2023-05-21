package com.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import android.text.TextUtils;

public class JsonUtil {

	/**
	 * 将map数据解析出来，并拼接成json字符串
	 * 
	 * @param map
	 * @return
	 */
	public static JSONObject setJosn(Map<String,String> map) throws Exception {
		JSONObject json = null;
		StringBuffer temp = new StringBuffer();
		if (!map.isEmpty()) {
			temp.append("{");

			// 遍历map
			Set set = map.entrySet();
			Iterator i = set.iterator();
			while (i.hasNext()) {
				Map.Entry entry = (Map.Entry) i.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				temp.append("'" + key + "':");
				temp.append("'" + value + "',");
			}
			if (temp.length() > 1) {
				temp = new StringBuffer(temp.substring(0, temp.length() - 1));
			}
			temp.append("}");
			json = new JSONObject(temp.toString());
		}
		return json;
	}

	/**
	 * 将json字符串解析，并放置到map中
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String,String> getJosn(String jsonStr) throws Exception {
		Map<String,String> map = null;
		if (!TextUtils.isEmpty(jsonStr)) {
			map = new HashMap<String,String>();
			JSONObject json = new JSONObject(jsonStr);
			Iterator i = json.keys();
			while (i.hasNext()) {
				String key = (String) i.next();
				String value = json.getString(key);
				map.put(key, value);
			}
		}
		return map;
	}
}
