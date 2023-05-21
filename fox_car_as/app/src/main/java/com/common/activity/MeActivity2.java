package com.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.blueberry.activity.R;

import com.bumptech.glide.Glide;
import com.common.util.HttpUtil;
import com.common.util.MyApp;
import com.common.util.MyBackAsynaTask;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import baidumap.LocationDemo_Current0;
import de.hdodenhof.circleimageview.CircleImageView;

public class MeActivity2 extends Activity{
	private TextView username,phone,role,haoping,chaping;
	private String money;
	private CircleImageView roundImg,locate;
	private Button logout;
	private MyApp myApp;
	private String jsonstring;
	LinearLayout line1,line2,line2_,line3,line4,line5,line6,line7,line8;

	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	private MyLocationConfiguration.LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	private static final int accuracyCircleFillColor = 0xAAFFFF88;
	private static final int accuracyCircleStrokeColor = 0xAA00FF00;

	boolean isFirstLoc = true; // 是否首次定位
	private Double x;
	private Double y;
	private String zuobiao,address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp = (MyApp) MeActivity2.this.getApplication();
		setView();
		initView();

		// 定位初始化
		mLocClient = new LocationClient(this);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true); // 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		mLocClient.setLocOption(option);
		mLocClient.start();

	}

	public void setView() {
		setContentView(R.layout.fragment_my2);
		ViewUtils.inject(this);

	}
	@OnClick({ R.id.top_back,R.id.locate })
	public void onclick(View view) {
		switch (view.getId()) {

		case R.id.top_back:
			Intent intent =new Intent(MeActivity2.this,TabHostActivity.class);
			startActivity(intent);
			break;
			case R.id.locate:

				Intent intent2 =new Intent(MeActivity2.this, LocationDemo_Current0.class);
				startActivity(intent2);
			break;
		}
	}
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			if (isFirstLoc) {
				isFirstLoc = false;//121.429579,31.17235
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatus.Builder builder = new MapStatus.Builder();
				builder.target(ll).zoom(15.0f);

				// http://api.map.baidu.com/geocoder?ak=RLXx7wEbmyzcFTYsuxOeXjK5Vsh7Fj1c&output=json&location=31.17235,121.429579

//				location_.setText("物业工作人员当前定位："+location.getProvince()+location.getCity()+location.getCountry());
//				location_.setText("物业工作人员当前定位："+location.getLatitude()+"-"+
//						location.getLongitude());

				zuobiao=location.getLatitude()+","+
						location.getLongitude();

				loaddataadd();
//				mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
			}
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		loaddata();
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
						jsonstring = obj.optString("jsonString");

					} catch (JSONException e) {
						e.printStackTrace();
					}


					handler.sendEmptyMessage(0x125);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();

	}
	public void updateaddress() {


		new Thread() {
			public void run() {
				try {
					String url = HttpUtil.URL_UPDATEADDRESS + "user.id=" + myApp.getLoginKey()
							+ "&user.address=" + address;


					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

					try {
						JSONObject obj = new JSONObject(result);
						String arrlist = obj.optString("jsonString");
						// JSONObject obj = new JSONObject(json);
						if (arrlist != "" && !arrlist.equals("arrlist")
								&& arrlist != null && !arrlist.equals("[]")) {
							if(arrlist.equals("1")){

								handler.sendEmptyMessage(0x123);
							}else{

								handler.sendEmptyMessage(0x124);
							}
						} else {

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

	public void loaddataadd() {

		new Thread() {
			public void run() {
				try {

					String url = HttpUtil.URL_LOADADDRESS+zuobiao;
					String result = HttpUtil.queryStringConnectForPost(url);

					JSONObject obj;
					try {
						obj = new JSONObject(result);
						String result2 = obj.optString("result");

						JSONObject obj2 = new JSONObject(result2);

						address= obj2.optString("formatted_address");

					} catch (JSONException e) {
						e.printStackTrace();
					}


					handler.sendEmptyMessage(0x126);
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


				JSONObject user;
				try {
					user = new JSONObject(jsonstring);
					money=user.optString("money");
					String phone_=user.optString("phone");
					String status=user.optString("status");
					String zan=user.optString("zan");
					String zan_=user.optString("zan_");
					String name=user.optString("name");
					String image_url=user.optString("image_url");
					username.setText(name+"\n账号余额："+money+"元");
					phone.setText(phone_);
					if(status.equals("0")){
						role.setText("存车人");
					}else if(status.equals("1")){
						role.setText("车商");
					}else if(status.equals("2")){
						role.setText("租车人");
					}

					haoping.setText(zan);
					chaping.setText(zan_);

					if(image_url.length()>0){
						String imagename = image_url
								.split("\\\\")[1];
//						MyBackAsynaTask asynaTask = new MyBackAsynaTask(
//								HttpUtil.URL_BASEUPLOAD + imagename, roundImg);
//						asynaTask.execute();

						Glide.with(MeActivity2.this).load(HttpUtil.URL_BASEUPLOAD+imagename).into(roundImg);

					}


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (msg.what ==0x126 ) {
				//


			}
			if (msg.what == 0x123) {
				//
				Toast.makeText(MeActivity2.this, "操作成功",
						Toast.LENGTH_SHORT).show();
			}
			if (msg.what == 0x124) {
				//
				Toast.makeText(MeActivity2.this, "操作失败",
						Toast.LENGTH_SHORT).show();
			}

		};
	};
	public void initView() {
		username =(TextView) findViewById(R.id.username);
		haoping =(TextView) findViewById(R.id.haoping);
		chaping =(TextView) findViewById(R.id.chaping);
		phone =(TextView) findViewById(R.id.phone);
		role =(TextView) findViewById(R.id.role);
		logout =(Button) findViewById(R.id.logout);
		roundImg =(CircleImageView) findViewById(R.id.roundImg);
		locate =(CircleImageView) findViewById(R.id.locate);

		line1=(LinearLayout) findViewById(R.id.linearLayoutCategory1);
		line2=(LinearLayout) findViewById(R.id.linearLayoutCategory2);
		line4=(LinearLayout) findViewById(R.id.linearLayoutCategory4);


		line1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity2.this,AddPhoto.class);
				startActivity(intent);


			}
		});
		line2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity2.this,UserProfileActivity2.class);
				startActivity(intent);


			}
		});
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				myApp.setLoginKey("");
				myApp.setLoginName("");
				myApp.setLoginPwd("");
				myApp.setStatus("");
				Intent intent = new Intent(MeActivity2.this,Login.class);
				startActivity(intent);
			}
		});
		line4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity2.this,Folder_Me.class);
				startActivity(intent);
			}
		});


	}

/*	@Override
	public void onItemSelected(View view, int position, long id, String name) {
		selectedTextView.setText(name);
	}

	@Override
	public void onItemClick(View view, int position, long id, String name) {
		switch (position) {
		case 0:
			Util.toIntent(this, InformationAvtivity.class);
			break;
		case 1:
			Util.toIntent(this, ExchangeList.class);
			break;
		case 2:
			Util.toIntent(this, GamePCList.class);
			break;
		case 3:
			Util.toIntent(this, GameXDList.class);
			break;
		case 4:
			Util.toIntent(this, PictureList.class);
			break;
		case 5:
			Util.toIntent(this, Foundingfriends.class);
			break;
		}
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Util.setUsersBean(null);
		return super.onKeyDown(keyCode, event);
	}*/
@Override
protected void onDestroy() {
	// 退出时销毁定位
	mLocClient.stop();
	super.onDestroy();
}
}
