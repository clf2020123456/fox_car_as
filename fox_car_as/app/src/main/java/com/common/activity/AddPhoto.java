package com.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blueberry.activity.R;
import com.common.util.BitmapUtil;
import com.common.util.FileUtil;
import com.common.util.FormFile;
import com.common.util.HttpUtil;
import com.common.util.MyApp;
import com.common.util.SocketHttpRequester;
import com.common.util.Util;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("NewApi")
public class AddPhoto extends Activity {
	@ViewInject(R.id.imageMyAvator)
	private CircleImageView imageMyAvator;
	private MyApp myApp;
	public final static int PHOTO_ZOOM = 0;
	public final static int PHOTO_ZOOM2 = 10;
	public final static int TAKE_PHOTO = 1;
	public final static int PHOTO_RESULT = 2;
	public static final String IMAGE_UNSPECIFIED = "image/*";
	private String imageDir;
	private String picnameString;// 图片名字
	// 创建一个以当前时间为名称的文件
	public static File tempFile = new File(
			Environment.getExternalStorageDirectory(),
			FileUtil.getPhotoFileName());

	private String path;

	private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	Bitmap mBitmap = null;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp = (MyApp) AddPhoto.this.getApplication();
		/*
		 * StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		 * .detectDiskReads().detectDiskWrites().detectNetwork()
		 * .penaltyLog().build()); StrictMode.setVmPolicy(new
		 * StrictMode.VmPolicy.Builder()
		 * .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		 * .penaltyLog().penaltyDeath().build());
		 */
		setContentView(R.layout.add_photo);
		ViewUtils.inject(this);


		
			
	}

	@OnClick({ R.id.register, R.id.top_back, R.id.imageMyAvator })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.register:
	/*		SensitivewordFilter filter = new SensitivewordFilter(AddDingdan.this);
			Set<String> set = filter.getSensitiveWord(gamecontext.getText().toString().trim()+gamecontext.getText().toString().trim(), 1);

			if(set.size()>0){
				Toast.makeText(AddDingdan.this, "检测到您发布的信息里面有"+set.size()+"个网络敏感词,请重新输入", Toast.LENGTH_SHORT).show();;
				return;
			}*/
			addXD();
			break;

		case R.id.top_back:
			finish();
			break;
		case R.id.imageMyAvator:
			/*
			 * // TODO Auto-generated method stub Intent intent = new Intent();
			 * 开启Pictures画面Type设定为image intent.setType("image/*");
			 * 使用Intent.ACTION_GET_CONTENT这个Action
			 * intent.setAction(Intent.ACTION_GET_CONTENT); 取得相片后返回本画面
			 * startActivityForResult(intent, 1);
			 */
			Intent intent = new Intent();
			/* 开启Pictures画面Type设定为image */
			intent.setType("image/*");
			/* 使用Intent.ACTION_GET_CONTENT这个Action */
			intent.setAction(Intent.ACTION_GET_CONTENT);
			/* 取得相片后返回本画面 */
			startActivityForResult(intent, PHOTO_ZOOM);
			break;
		}
	}

	// 提示对话框方法

	/**
	 *
	 */
	public void addXD() {

/*		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);*/
		String author = myApp.getLoginName();
		final String userid=myApp.getLoginKey();
		final String actionUrl = HttpUtil.URL_PHOTO_ADD;

	
		
		new Thread() {
			public void run() {
				try {

					try {
						// 请求普通信息
						Map<String, String> params = new HashMap<String, String>();
						// params.put("filename", "张三");
			    		params.put("user.id",myApp.getLoginKey());

						File imageFile = new File(path);
						FormFile formfile = new FormFile(imageFile.getName(),
								imageFile, "user.file",
								"application/octet-stream");
						SocketHttpRequester.post(actionUrl, params, formfile);

					} catch (Exception e) {
						Toast.makeText(AddPhoto.this, "上传失败!",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
						handler.sendEmptyMessage(0x126);
					}

					handler.sendEmptyMessage(0x125);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();
	}
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x125) {
				//

				Toast.makeText(AddPhoto.this, "上传成功!", Toast.LENGTH_LONG)
						.show();
				finish();
			}
			
			if (msg.what == 0x126) {
				//
				Toast.makeText(AddPhoto.this, "上传失败!", Toast.LENGTH_LONG)
				.show();
				finish();
				
			}
		};
	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.e("uri", uri.toString());

			try {
				String[] pojo = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(uri, pojo, null, null, null);
				ContentResolver cr = this.getContentResolver();
				if (cursor != null) {

					int colunm_index = cursor
							.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
					cursor.moveToFirst();
					String path1 = cursor.getString(colunm_index);
					/***
					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
					 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
					 */
					if (path1.endsWith("jpg") || path1.endsWith("png")||path1.endsWith("jpeg")) {
						path = path1;
						Bitmap bitmap = BitmapFactory.decodeStream(cr
								.openInputStream(uri));
						imageMyAvator.setImageBitmap(bitmap);
					} else {
						//alert();
					}
				} else {
					//alert();
					String path1 = uri.getPath();
					/***
					 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
					 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
					 */
					if (path1.endsWith("jpg") || path1.endsWith("png")||path1.endsWith("jpeg")) {
						path = path1;
						Bitmap bitmap = BitmapFactory.decodeStream(cr
								.openInputStream(uri));
						imageMyAvator.setImageBitmap(bitmap);
					}
				}

			} catch (Exception e) {

			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	// 将进行剪裁后的图片显示到UI界面上
	private void setPicToView(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			mBitmap = photo;
			String uriString = System.currentTimeMillis() + "";
			BitmapUtil.saveBitmapFile(photo, uriString);
			imageMyAvator.setImageBitmap(photo);
			path = "/sdcard/pic/" + uriString + ".jpg";
			// final File mFile = new File(("/sdcard/pic/" + uriString +
			// ".jpg"));
			/*
			 * new Thread() { public void run() { String maString =
			 * HttpAssist.uploadFile(mFile);
			 * System.out.println("=======maString" + maString); }; }.start();
			 */

		}
	}

	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { if (resultCode == RESULT_OK) { Uri uri =
	 * data.getData(); Log.e("uri", uri.toString());
	 * 
	 * try { String[] pojo = { MediaStore.Images.Media.DATA }; Cursor cursor =
	 * managedQuery(uri, pojo, null, null, null); if (cursor != null) {
	 * ContentResolver cr = this.getContentResolver(); int colunm_index = cursor
	 * .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	 * cursor.moveToFirst(); String path1 = cursor.getString(colunm_index);
	 *//***
	 * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
	 * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
	 */
	/*
	 * if (path1.endsWith("jpg") || path1.endsWith("png")) { path = path1;
	 * 
	 * BitmapFactory.Options options = new BitmapFactory.Options();
	 * options.inSampleSize = 2;// 图片宽高都为原来的二分之一，即图片为原来的四分之一 Bitmap bitmap =
	 * BitmapFactory.decodeStream( cr.openInputStream(uri), null, options);
	 * imageMyAvator.setImageBitmap(bitmap);
	 * 
	 * } else { // alert(); } } else { // alert(); }
	 * 
	 * } catch (Exception e) {
	 * 
	 * } } super.onActivityResult(requestCode, resultCode, data); }
	 */
}
