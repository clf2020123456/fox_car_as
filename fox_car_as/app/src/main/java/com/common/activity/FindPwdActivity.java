package com.common.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blueberry.activity.R;
import com.common.util.HttpUtil;
import com.common.util.IAsynTask;
import com.common.util.SendGETRequest;
import com.common.util.Util;
import com.common.util.Web;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindPwdActivity extends Activity {
	@ViewInject(R.id.editPhone)
	private EditText editPhone;
	@ViewInject(R.id.userpasswords)
	private EditText userpasswords;
	@ViewInject(R.id.userpasswords2)
	private EditText userpasswords2;
	@ViewInject(R.id.add_new_xd)
	private EditText add_new_xd;
	@ViewInject(R.id.btn_left)
	private Button btn_left;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_pwd);
		ViewUtils.inject(this);
	}

	@OnClick({ R.id.register, R.id.userlogin, R.id.btn_left })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.register:
			if (TextUtils.isEmpty(editPhone.getText().toString().trim())) {
				Util.showToast(this, "输入用户名！");
				return;
			}
			if (TextUtils.isEmpty(userpasswords.getText().toString().trim())) {
				Util.showToast(this, "输入密码！");
				return;
			}
			if (TextUtils.isEmpty(userpasswords2.getText().toString().trim())) {
				Util.showToast(this, "输入确认密码！");
				return;
			}
			if (!userpasswords.getText().toString().trim()
					.equals(userpasswords2.getText().toString().trim())) {
				Util.showToast(this, "请确认密码！");
				return;
			}
			String password = userpasswords.getText().toString().trim();
			String password2 = userpasswords2.getText().toString().trim();
			String phone = editPhone.getText().toString().trim();
			if(!isMobile(phone)){
				Util.showToast(this, "手机号码格式输入不正确");
				return;
				
			}
			SendData(password,phone);
			break;

		case R.id.userlogin:
			finish();
			Intent intent = new Intent(this, Login.class);
			startActivity(intent);
			break;
			case R.id.btn_left:
			finish();
			break;
		}
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			
			if (msg.what == 0x126) {
				//
				Toast.makeText(FindPwdActivity.this, "恭喜，密码重置成功", Toast.LENGTH_SHORT).show();;
            	Intent intent = new Intent(FindPwdActivity.this,Login.class);
            	FindPwdActivity.this.startActivity(intent);
			}
			if (msg.what == 0x127) {
				Toast.makeText(FindPwdActivity.this, "抱歉，操作失败", Toast.LENGTH_SHORT).show();;
			}
			if (msg.what == 0x128) {
				Toast.makeText(FindPwdActivity.this, "抱歉，该手机号不存在", Toast.LENGTH_SHORT).show();;
			}
		};
	};
	public void SendData(final String password ,final String phone){
		
		
		new Thread() {
			public void run() {
				try {
					String url = HttpUtil.URL_UPDATEPWD;
					String query ="";
					query+="user.password="+password+"&";
					query+="user.phone="+phone;
					url+=query;
					

					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

							try {
								JSONObject obj = new JSONObject(result);
								String arrlist = obj.optString("jsonString");
								// JSONObject obj = new JSONObject(json);
								if(arrlist!="" && !arrlist.equals("arrlist") && arrlist!=null && !arrlist.equals("[]")){
									if(arrlist.equals("1")){
										
						            	
						            	handler.sendEmptyMessage(0x126);
									}else if(arrlist.equals("0")){
									
								    	handler.sendEmptyMessage(0x127);
									}else if(arrlist.equals("2")){
						
								    	handler.sendEmptyMessage(0x128);
									}
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
	/**
     * 验证手机格式
     */
    public static boolean isMobile(String number) {
    /*
    移动：134、135、136、137、138、139、150、151、152、157(TD)、158、159、178(新)、182、184、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、170、173、177、180、181、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String num = "[1][34578]\\d{9}";//"[1]"代表第1位为数字1，"[34578]"代表第二位可以为3、4、5、7、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }
	private void checkUser(final String login) {
		Util.asynTask(this, "验证用户名", new IAsynTask() {

			@Override
			public void updateUI(Serializable runData) {
				// TODO Auto-generated method stub
				if (runData != null) {
					Map<String, String> map = (Map<String, String>) runData;
					if (map.get("type").equals("success")) {

					} else {
						Util.Toast(FindPwdActivity.this, "用户名已经存在。");
					}
				} else {
					Util.Toast(FindPwdActivity.this, "网络连接错误");
				}
			}

			@Override
			public Serializable run() {
				Map<String, String> map = null;
				try {
					map = SendGETRequest.sendGETRequest(Web.checkUserLogin,
							"?users.userlogin=" + login);
				} catch (Exception e) {
					// TODO: handle exception
				}
				// TODO Auto-generated method stub
				return (Serializable) map;
			}
		});
	}
}
