package com.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.blueberry.activity.R;
import com.common.bean.CartTypeBean;
import com.common.bean.CuncheBean;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("NewApi")
public class AddCunche extends Activity  implements View.OnTouchListener{
	@ViewInject(R.id.car_no)
	private EditText car_no;

	@ViewInject(R.id.btn_left)
	private EditText btn_left;

	@ViewInject(R.id.username)
	private EditText username;
	@ViewInject(R.id.cunche_start_date)
	private EditText cunche_start_date;
	@ViewInject(R.id.cunche_end_date)
	private EditText cunche_end_date;
	@ViewInject(R.id.imageMyAvator)
	private ImageView imageMyAvator;
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
	private String zan,zan_,money,jsonString,ring,image_url;
	private String jsonString2;
	Bitmap mBitmap = null;
	private List<CartTypeBean> cartTypeBeans = new ArrayList<CartTypeBean>();

	@ViewInject(R.id.spinner1)
	private Spinner spinner1;
	private ArrayAdapter<CartTypeBean> arrayAdapter;
	private List<String> teamList=new ArrayList<>();
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp = (MyApp) AddCunche.this.getApplication();
		/*
		 * StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		 * .detectDiskReads().detectDiskWrites().detectNetwork()
		 * .penaltyLog().build()); StrictMode.setVmPolicy(new
		 * StrictMode.VmPolicy.Builder()
		 * .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		 * .penaltyLog().penaltyDeath().build());
		 */
		setContentView(R.layout.add_cunche);
		ViewUtils.inject(this);
		cunche_start_date.setOnTouchListener(this);
		cunche_end_date.setOnTouchListener(this);
		System.out.println("-------------------------");

		getCartType();
		loaddata();


	}
	public void initList(){
		teamList.add("罗马");
		teamList.add("那不勒斯");
		teamList.add("国际米兰");
		teamList.add("AC米兰");
	}
	@OnClick({ R.id.register, R.id.btn_left, R.id.addimage })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.register:
			if (TextUtils.isEmpty(car_no.getText().toString().trim())) {
				Util.Toast(this, "不能为空");
				return;
			}
			if (TextUtils.isEmpty(username.getText().toString().trim())) {
				Util.Toast(this, "不能为空");
				return;
			}
			if (TextUtils.isEmpty(cunche_start_date.getText().toString().trim())) {
				Util.Toast(this, "不能为空");
				return;
			}
			if (TextUtils.isEmpty(cunche_end_date.getText().toString().trim())) {
				Util.Toast(this, "不能为空");
				return;
			}
			CartTypeBean selectedItem = (CartTypeBean) spinner1.getSelectedItem();
			if (selectedItem==null) {
				Util.Toast(this, "分类不能为空");
				return;
			}

	/*		SensitivewordFilter filter = new SensitivewordFilter(AddDingdan.this);
			Set<String> set = filter.getSensitiveWord(gamecontext.getText().toString().trim()+gamecontext.getText().toString().trim(), 1);

			if(set.size()>0){
				Toast.makeText(AddDingdan.this, "检测到您发布的信息里面有"+set.size()+"个网络敏感词,请重新输入", Toast.LENGTH_SHORT).show();;
				return;
			}*/


			 String cunche_start_date_ = cunche_start_date.getText().toString();
			 String cunche_end_date_ = cunche_end_date.getText().toString();
			 final String typeId = selectedItem.getPinpai()+"";


			try {
				final int days=Util.daysBetween(cunche_start_date_,cunche_end_date_);
				final int money=days*10;
				new AlertDialog.Builder(AddCunche.this)
						.setTitle("提示")
						.setMessage("您存车"+days+"天，预计需要"+money+"元存车费")

						.setPositiveButton("提交订单",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										// TODO Auto-generated method stub
										addXD(money,days,typeId);
									}
								}).setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
												int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
							}
						}).show();

			} catch (ParseException e) {
				e.printStackTrace();
			}


			//
			break;

		case R.id.btn_left:
			finish();
			break;
		case R.id.addimage:
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
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			View view = View.inflate(this, R.layout.date_time_dialog, null);
			final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);
			final TimePicker timePicker = (android.widget.TimePicker) view.findViewById(R.id.time_picker);
			timePicker.setIs24HourView(true);
			builder.setView(view);

			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(System.currentTimeMillis());
			datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), null);


			if (v.getId() == R.id.cunche_start_date) {
				final int inType = cunche_start_date.getInputType();
				cunche_start_date.setInputType(InputType.TYPE_NULL);
				cunche_start_date.onTouchEvent(event);
				cunche_start_date.setInputType(inType);
				cunche_start_date.setSelection(cunche_start_date.getText().length());

				builder.setTitle("设置日期");
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d",
								datePicker.getYear(),
								datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						sb.append(" ");
						sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());

						cunche_start_date.setText(sb);
//                        editEndTime.requestFocus();

						dialog.cancel();
					}
				});

			}
			if (v.getId() == R.id.cunche_end_date) {
				final int inType = cunche_end_date.getInputType();
				cunche_end_date.setInputType(InputType.TYPE_NULL);
				cunche_end_date.onTouchEvent(event);
				cunche_end_date.setInputType(inType);
				cunche_end_date.setSelection(cunche_end_date.getText().length());

				builder.setTitle("设置日期");
				builder.setPositiveButton("确  定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						StringBuffer sb = new StringBuffer();
						sb.append(String.format("%d-%02d-%02d",
								datePicker.getYear(),
								datePicker.getMonth() + 1,
								datePicker.getDayOfMonth()));
						sb.append(" ");
						sb.append(timePicker.getCurrentHour()).append(":").append(timePicker.getCurrentMinute());

						cunche_end_date.setText(sb);
//            			editStarttime.requestFocus();

						dialog.cancel();
					}
				});

			}

			Dialog dialog = builder.create();
			dialog.show();
		}

		return true;
	}

	// 提示对话框方法

	/**
	 *
	 */
	public void addXD(final int money,final int days,final String typeid) {

/*		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);*/
		final String cunche_start_date_ = cunche_start_date.getText().toString();
		final String cunche_end_date_ = cunche_end_date.getText().toString();
		final String car_no_ = car_no.getText().toString();

		final String userid=myApp.getLoginKey();
		final String username=myApp.getLoginName();
		final String actionUrl = HttpUtil.URL_CUNCHE;

	
		
		new Thread() {
			public void run() {
				try {

					try {
						// 请求普通信息
						Map<String, String> params = new HashMap<String, String>();
						// params.put("filename", "张三");
						params.put("cunche.car_no", URLEncoder.encode(car_no_));
			    		params.put("cunche.username",username);
			    		params.put("cunche.userid", userid+"");
			    		params.put("cunche.status",0+"");
			    		params.put("cunche.days",days+"");
			    		params.put("cunche.cunche_start_date_str",URLEncoder.encode(cunche_start_date_));
			    		params.put("cunche.cunche_end_date_str",URLEncoder.encode(cunche_end_date_));
			    		params.put("cunche.money",money+"");
			    		params.put("cunche.car_pinpai",typeid+"");

//						File imageFile = new File(path);
//						FormFile formfile = new FormFile(imageFile.getName(),
//								imageFile, "cunche.file",
//								"application/octet-stream");
						SocketHttpRequester.post(actionUrl, params, new FormFile[]{});

					} catch (Exception e) {
						Toast.makeText(AddCunche.this, "上传失败!",
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
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x125) {
				//

				Toast.makeText(AddCunche.this, "发布成功!", Toast.LENGTH_LONG)
						.show();
				Intent intent = new Intent(AddCunche.this, TabHostActivity.class);
				startActivity(intent);
				finish();
			}
			
			if (msg.what == 0x126) {
				//
				Toast.makeText(AddCunche.this, "发布失败!", Toast.LENGTH_LONG)
				.show();
				finish();
				
			}
			if (msg.what == 0x128) {
				//
				JSONObject obj;
				try {
//					obj = new JSONObject(jsonString);
//					String arrlist = obj.optString("jsonString");
					JSONObject user = new JSONObject(jsonString);
					if(user.length()> 0){
						String username1 = user.optString("username");
						String password1 = user.optString("password");
						String qqnum1= user.optString("qqnum");
						String phone1 = user.optString("phone");
						String realname1 = user.optString("name");
						String address1 = user.optString("address");
						zan = user.optString("status");
						zan_ = user.optString("zan_");
						money = user.optString("money");
						image_url = user.optString("image_url");
						if(zan.equals("0")){
							car_no.setText(qqnum1);
						}

						username.setText(username1);





					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}else 	if (msg.what == 0x137) {
				//
				ArrayList<CartTypeBean> goods_list = CartTypeBean
						.newInstanceList(jsonString2);
				cartTypeBeans.addAll(goods_list);
				for (CartTypeBean cartTypeBean : goods_list) {


				}
//				initList();
				arrayAdapter = new ArrayAdapter<CartTypeBean>(AddCunche.this,android.R.layout.simple_spinner_item,cartTypeBeans);
				arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				//将adapter 添加到spinner中


				spinner1.setAdapter(arrayAdapter);

				System.out.println(jsonString2);
//				adapter.notifyDataSetChanged();
			}
			if (msg.what == 0x138) {
				//
				cartTypeBeans.addAll(new ArrayList<CartTypeBean>());
//				adapter.notifyDataSetChanged();
				System.out.println(jsonString2);
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
	@SuppressLint("NewApi")
	public void getCartType() {



		new Thread() {

			public void run() {
				try {
					cartTypeBeans = new ArrayList<CartTypeBean>();
					String url = HttpUtil.CartTypeLIST;


					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

					try {
						JSONObject obj = new JSONObject(result);
						String arrlist = obj.optString("jsonString");
						// JSONObject obj = new JSONObject(json);
						if (arrlist != "" && !arrlist.equals("arrlist")
								&& arrlist != null && !arrlist.equals("[]")) {
							jsonString2 =arrlist;
							System.out.println(jsonString2);


							handler.sendEmptyMessage(0x137);
						} else {
							handler.sendEmptyMessage(0x138);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();
	}

	@SuppressLint("NewApi")
	public void loaddata() {

		new Thread() {
			public void run() {
				try {

					String url = HttpUtil.URL_LOAD;
					String query = "";
					query += "user.id=" + myApp.getLoginKey();
					url += query;
					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

					JSONObject obj;
					try {
						obj = new JSONObject(result);
						jsonString = obj.optString("jsonString");

					} catch (JSONException e) {
						e.printStackTrace();
					}


					handler.sendEmptyMessage(0x128);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();

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
