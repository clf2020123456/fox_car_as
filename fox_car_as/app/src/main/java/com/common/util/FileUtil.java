package com.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class FileUtil {
	public static File getFile() {
		File file = FileUtil.getFilePath("/sdcard/pic", "02.jpg");
		file = new File("/sdcard/pic/02.jpg");
		return file;
	}

	// 鍒涘缓鏂囦欢
	// FileUtil.getFilePath("/sdcard/pic", "01.jpg");
	// File file = new File("/sdcard/pic/01.jpg");//
	public static File getFilePath(String filePath, String fileName) {
		File file = null;
		makeRootDirectory(filePath);
		try {
			file = new File(filePath + fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return file;
	}

	// 鍒涘缓鏂囦欢
	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}

	// 浣跨敤绯荤粺褰撳墠鏃ユ湡鍔犱互璋冩暣浣滀负鐓х墖鐨勫悕绉?
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	// 瀛楯son鏁版嵁
	public static void putJson(String json, String path) throws IOException {
		FileUtil.getFilePath("/sdcard/pic", path + ".txt");
		File mFile = new File("/sdcard/pic/" + path + ".txt");
		BufferedOutputStream mBufferedOutputStream;
		mBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(
				mFile));
		mBufferedOutputStream.write(json.getBytes());
		mBufferedOutputStream.close();
	}

	// 璇诲彇鏂囦欢
	public static String onotView(String path) throws IOException {
		StringBuffer sb = new StringBuffer();
		File file = new File("/sdcard/pic/" + path + ".txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		return sb.toString();

	}
}
