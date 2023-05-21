package com.common.utils;

import java.io.Serializable;

import android.graphics.Bitmap;

/**
 * 
 */
public class PhotoInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	private int image_id;
	private String path_file;
	private String path_absolute;
	private boolean choose = false;
	private Bitmap bitmap;
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public int getImage_id() {
		return image_id;
	}
	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}
	public String getPath_file() {
		return path_file;
	}
	public void setPath_file(String path_file) {
		this.path_file = path_file;
	}
	public String getPath_absolute() {
		return path_absolute;
	}
	public void setPath_absolute(String path_absolute) {
		this.path_absolute = path_absolute;
	}
	public boolean isChoose() {
		return choose;
	}
	public void setChoose(boolean choose) {
		this.choose = choose;
	}
}
