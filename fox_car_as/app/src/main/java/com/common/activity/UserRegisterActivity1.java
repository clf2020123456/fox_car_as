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
import android.widget.Spinner;
import android.widget.TextView;
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

public class UserRegisterActivity1 extends Activity {
	@ViewInject(R.id.username)
	private EditText username;
	@ViewInject(R.id.userpasswords)
	private EditText userpasswords;
	@ViewInject(R.id.editText1)
	private EditText editText1;
	@ViewInject(R.id.editQQ)
	private EditText editQQ;
	@ViewInject(R.id.editCarType)
	private EditText editCarType;

	@ViewInject(R.id.sex)
	private EditText sexType;
	@ViewInject(R.id.editPhone)
	private EditText editPhone;
	@ViewInject(R.id.editName)
	private EditText editName;
	@ViewInject(R.id.editAddress)
	private EditText editAddress;
	@ViewInject(R.id.btn_left)
	private Button btn_left;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_reg1);
		ViewUtils.inject(this);
		username.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					// 此处为得到焦点时的处理内容
				} else {
					if (TextUtils.isEmpty(username.getText().toString().trim())) {
						Toast.makeText(UserRegisterActivity1.this, "用户名不能为空", 0)
								.show();
					} else if (username.getText().toString().trim().length() < 4
							|| username.getText().toString().trim().length() > 20) {
						Toast.makeText(UserRegisterActivity1.this,
								"用户名的长度应在4-20个字符之间", Toast.LENGTH_SHORT).show();
					} else {
						Pattern pattern = Pattern.compile("[0-9]*");
						Matcher matcher = pattern.matcher(username.getText()
								.toString().trim());
						Pattern pattern2 = Pattern.compile("\\W+");
						Matcher matcher2 = pattern2.matcher(username.getText()
								.toString().trim());
						if (matcher.matches()) {
							Toast.makeText(UserRegisterActivity1.this,
									"用户名不能全部是数字", 0).show();
							return;
						}
						if (matcher2.find()) {
							Toast.makeText(UserRegisterActivity1.this,
									"用户名不能含有特殊字符，只能是由汉字、数字、英文字母以及下划线组成", 0)
									.show();
							return;
						}
//						checkUser(username.getText().toString().trim());
					}
				}
			}
		});
	}

	@OnClick({ R.id.register, R.id.userlogin , R.id.btn_left})
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.register:
			if (TextUtils.isEmpty(username.getText().toString().trim())) {
				Util.showToast(this, "输入用户名！");
				return;
			}
			if (TextUtils.isEmpty(userpasswords.getText().toString().trim())) {
				Util.showToast(this, "输入密码！");
				return;
			}
			if (TextUtils.isEmpty(editText1.getText().toString().trim())) {
				Util.showToast(this, "输入确认密码！");
				return;
			}
			if (!userpasswords.getText().toString().trim()
					.equals(editText1.getText().toString().trim())) {
				Util.showToast(this, "输入确认密码！");
				return;
			}
			if (!sexType.getText().toString().trim()
					.equals("男".trim())||!sexType.getText().toString().trim()
					.equals("女".trim())) {
				Util.showToast(this, "性别只能是男或者女！");
				return;
			}
			String user_name = username.getText().toString().trim();
			String password = userpasswords.getText().toString().trim();
			String qq = editQQ.getText().toString();
			String phone = editPhone.getText().toString().trim();
			String name = editName.getText().toString().trim();
			String address = editAddress.getText().toString().trim();
			String carType = editCarType.getText().toString().trim();
			String sex = sexType.getText().toString().trim();
			if(!isMobile(phone)){
				Util.showToast(this, "手机号码格式输入不正确");
				return;
				
			}
			SendData(user_name, password, qq, phone,name,address,carType,sex);
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
				Toast.makeText(UserRegisterActivity1.this, "恭喜，信息保存成功", Toast.LENGTH_SHORT).show();;
            	Intent intent = new Intent(UserRegisterActivity1.this,Login.class);
            	UserRegisterActivity1.this.startActivity(intent);
			}
			if (msg.what == 0x127) {
				Toast.makeText(UserRegisterActivity1.this, "抱歉，操作失败", Toast.LENGTH_SHORT).show();;
			}
			if (msg.what == 0x128) {
				Toast.makeText(UserRegisterActivity1.this, "抱歉，该用户名已被占用", Toast.LENGTH_SHORT).show();;
			}
			if (msg.what == 0x129) {
				Toast.makeText(UserRegisterActivity1.this, "抱歉，该手机号已被注册", Toast.LENGTH_SHORT).show();;
			}
		};
	};
	public void SendData(final String username , final String password ,final String qq,final String phone,final String name,final String address
	,final String carType,final String sex){
		
		
		new Thread() {
			public void run() {
				try {
					String url = HttpUtil.URL_REGISTER;
					String query ="";
					query+="user.username="+username+"&";
					query+="user.password="+password+"&";
					query+="user.qqnum="+qq+"&";
					query+="user.name="+name+"&";
					query+="user.status="+1+"&";
					query+="user.address="+address+"&";
					query+="user.phone="+phone;
					query+="user.carType="+carType;
					query+="user.sex="+sex;
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
									}else if(arrlist.equals("3")){
							
								    	handler.sendEmptyMessage(0x129);
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
						Util.Toast(UserRegisterActivity1.this, "用户名已经存在。");
						username.setText("");
					}
				} else {
					Util.Toast(UserRegisterActivity1.this, "网络连接错误");
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
