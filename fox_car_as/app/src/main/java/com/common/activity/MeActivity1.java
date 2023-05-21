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

import com.blueberry.activity.R;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.common.util.HttpUtil;
import com.common.util.MyApp;
import com.common.util.MyBackAsynaTask;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

public class MeActivity1 extends Activity{
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
		myApp = (MyApp) MeActivity1.this.getApplication();
		setView();
		initView();

	}

	public void setView() {
		setContentView(R.layout.fragment_my1);
		ViewUtils.inject(this);

	}
	@OnClick({ R.id.top_back })
	public void onclick(View view) {
		switch (view.getId()) {

		case R.id.top_back:
			Intent intent =new Intent(MeActivity1.this,TabHostActivity.class);
			startActivity(intent);
			break;
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

					if(image_url.length()>0){
						String imagename = image_url
								.split("\\\\")[1];
//						MyBackAsynaTask asynaTask = new MyBackAsynaTask(
//								HttpUtil.URL_BASEUPLOAD + imagename, roundImg);
//						asynaTask.execute();

						Glide.with(MeActivity1.this).load(HttpUtil.URL_BASEUPLOAD+imagename).into(roundImg);

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
		line3=(LinearLayout) findViewById(R.id.linearLayoutCategory3);
		line4=(LinearLayout) findViewById(R.id.linearLayoutCategory4);
		line5=(LinearLayout) findViewById(R.id.linearLayoutCategory5);


		line1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity1.this,AddPhoto.class);
				startActivity(intent);


			}
		});
		line2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity1.this,UserProfileActivity1.class);
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
				Intent intent = new Intent(MeActivity1.this,Login.class);
				startActivity(intent);
			}
		});
		line4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MeActivity1.this,Folder_Me.class);
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

}
