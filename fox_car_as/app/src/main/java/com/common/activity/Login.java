package com.common.activity;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.alibaba.fastjson.JSON;
import com.blueberry.activity.R;
import com.common.bean.LoginBean;
import com.common.util.HttpUtil;
import com.common.util.IAsynTask;
import com.common.util.MyApp;
import com.common.util.SendGETRequest;
import com.common.util.Util;
import com.common.util.Web;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class Login extends Activity {
	@ViewInject(R.id.name)
	private EditText name;
	@ViewInject(R.id.pwd)
	private EditText pwd;
	@ViewInject(R.id.add_new_xd)
	private TextView add_new_xd;
	private String state;
	private MyApp myApp;
	@ViewInject(R.id.checkboxMyAuto)
	private CheckBox checkboxMyAuto;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_login);
		myApp = (MyApp) Login.this.getApplication();
		ViewUtils.inject(this);
		SharedPreferences sp = getSharedPreferences("user",
				Context.MODE_PRIVATE);
		name.setText(sp.getString("username", null));
		pwd.setText(sp.getString("password", null));

		if(myApp.isIsCheckLogin()){
			//startActivity(new Intent(Login.this,MainActivity.class));
			name.setText(myApp.getLoginName());
			pwd.setText(myApp.getLoginPwd());
			checkboxMyAuto.setChecked(true);
		}


	checkboxMyAuto.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
//				Toast.makeText(Login.this, arg1+"", 1).show();
				myApp.setIsCheckLogin(arg1);

			}
		});
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) != PackageManager.PERMISSION_GRANTED) {
			System.out.println("需要动态获取权限");
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
		}
		
		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			System.out.println("需要动态获取权限");
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_SETTINGS}, 0);
		}else{
//            toast("不需要动态获取权限");
			TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			String deviceId = TelephonyMgr.getDeviceId();
		}
	}
	@SuppressLint("MissingPermission")
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == 0 && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			TelephonyManager TelephonyMgr = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
			System.out.println(TelephonyMgr.getDeviceId());
		}
	}
	@OnClick({  R.id.btn_login, R.id.btn_reg ,R.id.add_new_xd})
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.btn_reg:


			new AlertDialog.Builder(Login.this)
					.setMessage("用户注册")

//					.setPositiveButton("进入注册",
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog,
//													int which) {
//									// TODO Auto-generated method stub
//
//									Util.toIntent(Login.this, UserRegisterActivity.class);
//								}
//							})

//					.setNeutralButton("车商注册",
//					new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog,
//											int which) {
//							// TODO Auto-generated method stub
//
//							Util.toIntent(Login.this, UserRegisterActivity1.class);
//						}
//					})


					.setNegativeButton("进入注册",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog,
											int which) {
							// TODO Auto-generated method stub

							Util.toIntent(Login.this, UserRegisterActivity2.class);
						}
					}).show();
			break;
		case R.id.add_new_xd:
			Util.toIntent(Login.this, FindPwdActivity.class);
			break;
		case R.id.btn_login:
			if (TextUtils.isEmpty(name.getText().toString().trim())) {
				Util.Toast(this, "请输入用户名");
				return;
			}
			if (TextUtils.isEmpty(pwd.getText().toString().trim())) {
				Util.Toast(this, "请输入登录密码");
				return;
			}
			login(name.getText().toString().trim(), pwd.getText().toString()
					.trim());
			break;
		}
	}

	public void login(final String loginName, final String loginPassword) {
		
		
		
		
		new Thread() {
			public void run() {
				try {
					
				
					String url = HttpUtil.URL_LOGIN;
					String query = "";
					query += "user.username=" + loginName + "&";
					query += "user.password=" + loginPassword;

					url += query;


					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);
							LoginBean login = LoginBean.newInstanceList(result);
							if (login != null) {
								int zan_=Integer.parseInt(login.getZan_());
								if(zan_>30){
								
									
									handler.sendEmptyMessage(0x125);
								}else{
							
									myApp.setLoginKey(login.getKey());
									myApp.setLoginName(login.getUsername());
									myApp.setLoginPwd(login.getPassword());
									myApp.setStatus(login.getStatus());
									
									handler.sendEmptyMessage(0x126);
								}
						
							} else {
							
								
								handler.sendEmptyMessage(0x127);
							}

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
				Toast.makeText(Login.this, "该用户已加入黑名单", Toast.LENGTH_SHORT)
				.show();
			}
			
			if (msg.what == 0x126) {
				//
			
				Toast.makeText(Login.this, "登陆成功", Toast.LENGTH_SHORT)
						.show();
				;
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
						InputMethodManager.HIDE_NOT_ALWAYS);
				Intent intent = new Intent(Login.this, TabHostActivity.class);
				Login.this.startActivity(intent);
				
			}
			if (msg.what == 0x127) {
				//
				Toast.makeText(Login.this, "用户名或者密码错误", Toast.LENGTH_SHORT)
				.show();
		;
				
			}
		};
	};
}
