package com.common.activity;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blueberry.activity.R;

import com.bumptech.glide.Glide;
import com.common.util.HttpUtil;
import com.common.util.MyApp;
import com.common.util.MyBackAsynaTask;
import com.common.util.Util;


import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeActivity extends Activity{
	private TextView username,phone,role;
	private String money;
	private CircleImageView roundImg;
	private Button logout;
	private MyApp myApp;
	private String jsonstring;
	LinearLayout line1,line2,line2_,line3,line4,line5,line6,line7,line8;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp = (MyApp) MeActivity.this.getApplication();
		setView();
		initView();

	}

	public void setView() {
		setContentView(R.layout.fragment_my);
		ViewUtils.inject(this);

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

					if(image_url.length()>0&&!image_url.equals("null")){
						String imagename = image_url
								.split("\\\\")[1];
//						MyBackAsynaTask asynaTask = new MyBackAsynaTask(
//								HttpUtil.URL_BASEUPLOAD + imagename, roundImg);
//						asynaTask.execute();

						Glide.with(MeActivity.this).load(HttpUtil.URL_BASEUPLOAD+imagename).into(roundImg);

					}


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		};
	};
	public void initView() {
		username =(TextView) findViewById(R.id.username);
		phone =(TextView) findViewById(R.id.phone);
		role =(TextView) findViewById(R.id.role);
		logout =(Button) findViewById(R.id.logout);
		roundImg =(CircleImageView) findViewById(R.id.roundImg);

		line1=(LinearLayout) findViewById(R.id.linearLayoutCategory1);
		line2=(LinearLayout) findViewById(R.id.linearLayoutCategory2);
		line4=(LinearLayout) findViewById(R.id.linearLayoutCategory4);


		line1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity.this,AddPhoto.class);
				startActivity(intent);


			}
		});
		line2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity.this,UserProfileActivity.class);
				startActivity(intent);


			}
		});
		line4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity.this,Folder_Me.class);
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
				Intent intent = new Intent(MeActivity.this,Login.class);
				startActivity(intent);
			}
		});
	}


}
