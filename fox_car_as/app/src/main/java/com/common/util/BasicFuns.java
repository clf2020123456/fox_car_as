package com.common.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;



import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.widget.Toast;

public class BasicFuns {
	
	public static  void showDialog(Context currentActivitycontext,String msg){
		AlertDialog.Builder builder = new AlertDialog.Builder(currentActivitycontext);
		builder.setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   return;
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	public static  void showDialogConfirmExit(final Context currentActivitycontext){
		AlertDialog.Builder builder = new AlertDialog.Builder(currentActivitycontext);
		builder.setMessage("确定退出系统吗?")
		       .setNegativeButton("取消", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   return;
		           }
		       })
		       .setNeutralButton("隐藏", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   Intent i= new Intent(Intent.ACTION_MAIN);

//		        	   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //android123提示如果是服务里调用，必须加入new task标识
		        	   i.addCategory(Intent.CATEGORY_HOME);
		        	   currentActivitycontext.startActivity(i);
		           }
		       })
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
//		        	   BasicFuns.exitSystem(currentActivitycontext);
		        	  // return;
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	public static  void showDialogWithTitle(Context currentActivitycontext,String msg,String title){
		AlertDialog.Builder builder = new AlertDialog.Builder(currentActivitycontext);
		builder.setTitle(title).setMessage(msg)
		       .setCancelable(false)
		       .setPositiveButton("确定", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		        	   return;
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
	}
	
	
	
	public static  void showDialogWithTitle_check(Context currentActivitycontext,String msg,String title){
	
	}
	
	public static  void toast(Context currentActivitycontext,String text) {
        Toast.makeText(currentActivitycontext, text, Toast.LENGTH_LONG).show();
    }
	
/*	
	public static void exitSystem(Context _context){
		//
		NotificationManager manager = (NotificationManager) _context
		.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.cancelAll();
		//
		 AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
			Intent intent = new Intent(_context, AlarmReceiver.class);
			//创建封装BroadcastReceiver 的pendingIntent对象
			PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, 0,intent, 0);
			alarmManager.cancel(pendingIntent);
		
		//manager.
		  ExitApplication.getInstance().exit(_context);
	}*/
	
	
	
	/* 上传文件至Server的方法 */
    public static void uploadFile(Context _context,String picPath,String actionUrl,String planId)
    {
    			try {
    				// 请求普通信息
    				Map<String, String> params = new HashMap<String, String>();
    				// params.put("filename", "张三");
    				params.put("user.id", planId);
    				File imageFile = new File(picPath);
    	    		FormFile formfile = new FormFile(imageFile.getName(), imageFile,
    	    						"user.file", "application/octet-stream");
    	    		SocketHttpRequester.post(actionUrl, params, formfile);
    				Toast.makeText(_context, "图片上传成功!", Toast.LENGTH_LONG).show();
    				
    				
    			} catch (Exception e) {
    				Toast.makeText(_context, "图片上传失败!", Toast.LENGTH_LONG).show();
    				e.printStackTrace();
    			}
    }
    public static void uploadFile2_0(Context _context,String picPath,String actionUrl,String title,String conent,String author,String userid,String type,String price,String tel)
    {
    	try {
    		// 请求普通信息
    		Map<String, String> params = new HashMap<String, String>();
    		// params.put("filename", "张三");
    		params.put("biotech.title", title);
    		params.put("biotech.content", conent);
    		params.put("biotech.author", author);
    		params.put("biotech.type", 0+"");
    		params.put("biotech.type2", type);
    		params.put("biotech.ck_phone", tel);
    		params.put("biotech.status", 0+"");
    		params.put("biotech.add_userid", userid);
    		params.put("biotech.price", price);
    		File imageFile = new File(picPath);
    		FormFile formfile = new FormFile(imageFile.getName(), imageFile,
    				"biotech.file", "application/octet-stream");
    		SocketHttpRequester.post(actionUrl, params, formfile);
    		Toast.makeText(_context, "发布成功!", Toast.LENGTH_LONG).show();
    		
    		
    	} catch (Exception e) {
    		Toast.makeText(_context, "图片上传失败!", Toast.LENGTH_LONG).show();
    		e.printStackTrace();
    	}
    }
    /**
     * 	String name = proj_name.getText().toString();
		String group = pro_group.getText().toString();
		String user = proj_user.getText().toString();
		String content = proj_content.getText().toString();
     * @param _context
 	query += "biotech.proj_name=" + proj_name + "&";
		query += "biotech.proj_group=" + pro_group + "&";
		query += "biotech.proj_user=" + proj_user + "&";
		query += "biotech.proj_content=" + proj_content;
     */
    public static void uploadFile2_1(Context _context,String picPath,String actionUrl,String name,String group,String user,String content)
    {
    	try {
    		// 请求普通信息
    		Map<String, String> params = new HashMap<String, String>();
    		// params.put("filename", "张三");
    		params.put("biotech.proj_name", name);
    		params.put("biotech.proj_group", group);
    		params.put("biotech.proj_user", user);
    		params.put("biotech.proj_content", content);
    		File imageFile = new File(picPath);
    		FormFile formfile = new FormFile(imageFile.getName(), imageFile,
    				"biotech.file", "application/octet-stream");
    		SocketHttpRequester.post(actionUrl, params, formfile);
    		Toast.makeText(_context, "发布成功!", Toast.LENGTH_LONG).show();
    		
    		
    	} catch (Exception e) {
    		Toast.makeText(_context, "图片上传失败!", Toast.LENGTH_LONG).show();
    		e.printStackTrace();
    	}
    }
    
    
    
    
    public static void uploadVideoFile(Context _context,String videoName,String actionUrl,String planId)
    {

    	

    			

    			
    			try {
    				// 请求普通信息
    				Map<String, String> params = new HashMap<String, String>();
    				// params.put("filename", "张三");
    				params.put("planId", planId);
    				// 上传文件
    				File videoFile = new File(Environment.getExternalStorageDirectory(),videoName);
    				/*FormFile formfile = new FormFile(videoFile.getName(), videoFile,
    						"video", "application/octet-stream");
    				
    				SocketHttpRequester.post(actionUrl, params, formfile);*/
    				Toast.makeText(_context, "视频上传成功!", Toast.LENGTH_LONG).show();
    				
    				
    			} catch (Exception e) {
    				Toast.makeText(_context, "视频上传失败!", Toast.LENGTH_LONG).show();
    				e.printStackTrace();
    			}

    
    	
    	
    }
	

}
