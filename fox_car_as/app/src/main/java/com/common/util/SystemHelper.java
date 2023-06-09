package com.common.util;

import java.io.File;


import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.blueberry.activity.R;


/**
 * 获取系统信息的工具类
 *
 * @author hjgang
 */
public class SystemHelper {
	private SystemHelper() {
	}

	/**
	 * 创建本应用的桌面快捷方式<br/>
	 * 注意：需要添加权限&lt;uses-permission android:name="com.android.launcher.permission.INSTALL_SHORTCUT"/&gt;  
	 */
	public static void createShortcut(Context context, Class<?> clazz){
		Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

		//快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, context.getString(R.string.app_name));
		shortcut.putExtra("duplicate", false); //不允许重复创建

		Intent localIntent2 = new Intent(context, clazz);
	    localIntent2.setAction(Intent.ACTION_MAIN);
	    localIntent2.addCategory(Intent.CATEGORY_LAUNCHER);

	    shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, localIntent2);//指定快捷方式要启动的Activity类型

		//快捷方式的图标
		ShortcutIconResource iconResource = ShortcutIconResource.fromContext(context, R.drawable.s0001);
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconResource);

		context.sendBroadcast(shortcut);
	}

	/**
	 * 检查是否已经创建了桌面快捷方式<br/>
	 * 注意：需要添加权限&lt;uses-permission android:name="com.android.launcher.permission.READ_SETTINGS"/&gt;  
	 * @return
	 */
	public static boolean hasShortCut(Context context) {
		String url = "";
		if (android.os.Build.VERSION.SDK_INT < 8) {
			url = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			url = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(url), null, "title=?",
				new String[] { context.getString(R.string.app_name) }, null);

		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}

		return false;
	}
	/**
	 * 获取当前机器的屏幕信息对象<br/>
	 * 另外：通过android.os.Build类可以获取当前系统的相关信息
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getScreenInfo(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics dm = new DisplayMetrics();
		windowManager.getDefaultDisplay().getMetrics(dm);
		// dm.widthPixels;//寬度
		// dm.heightPixels; //高度
		// dm.density; //密度
		return dm;
	}

	/**
	 * 获取手机号<br/>
	 * 注意：需要添加权限&lt;uses-permission
	 * android:name="android.permission.READ_PHONE_STATE"/&gt;。另外很多手机不能获取到当前手机号
	 *
	 * @param context
	 * @return
	 */
	@SuppressLint("MissingPermission")
	public static String getMobileNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getLine1Number();
	}

	/**
	 * 检测当前的网络连接是否可用<br/>
	 * 注意：需要添加权限&lt;uses-permission
	 * android:name="android.permission.ACCESS_NETWORK_STATE"/&gt;
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		boolean flag = false;
		try {
			ConnectivityManager connManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null != connManager) {
				NetworkInfo info = connManager.getActiveNetworkInfo();
				if (null != info && info.isAvailable()) {
					flag = true;
				}
			}
		} catch (Exception e) {
			Log.e("NetworkInfo", "Exception", e);
		}
		return flag;
	}

	/**
	 * 检测当前网络连接的类型<br/>
	 * 注意：需要添加权限 <uses-permission  android:name="android.permission.ACCESS_NETWORK_STATE"/>;
	 * @param context
	 * @return 返回0代表GPRS网络;返回1,代表WIFI网络;返回-1代表网络不可用
	 */
	public static int getNetworkType(Context context) {
		int code = -1;
		try {
			ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (null != connManager) {
				State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
				if (State.CONNECTED == state) {
					code = ConnectivityManager.TYPE_WIFI;
				} else {
					state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
					if (State.CONNECTED == state) {
						code = ConnectivityManager.TYPE_MOBILE;
					}
				}
			}
		} catch (Exception e) {
			Log.e("NetworkInfo", "Exception", e);
		}
		return code;
	}

	/**
	 * 返回当前程序版本代码,如:1
	 * @param context
	 * @return 当前程序版本代码
	 */
	public static int getAppVersionCode(Context context) {
		int versionCode = -1;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionCode = pi.versionCode;

		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionCode;
	}

	/**
	 * 返回当前程序版本名,如:1.0.1
	 * 
	 * @param context
	 * @return 当前程序版本名
	 */
	public static String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			versionName = pi.versionName;

		} catch (Exception e) {
			Log.e("VersionInfo", "Exception", e);
		}
		return versionName;
	}
	

	/**
	 * 安装指定的APK文件，主要用于本应用程序的更新
	 * 
	 * @param context
	 * @param apk(apk文件的全路径名)
	 */
	public static void installAPK(Context context, String apk) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(apk)),
				"application/vnd.android.package-archive");
		context.startActivity(intent);
	}
}
