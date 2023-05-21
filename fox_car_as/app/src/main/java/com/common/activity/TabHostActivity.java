/**
 *
 * PackageName:net.shopnc.android
 * FileNmae:MainActivity.java
 */
package com.common.activity;



import com.blueberry.activity.R;
import com.common.util.MyApp;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TabHost;

/**
 * Tab 底部菜单处理
 * @author KingKong·HE
 */
public class TabHostActivity extends TabActivity {
	/** tab标签名*/
	public final static String TAB_TAG_HOME = "home";
	public final static String TAB_TAG_TYPE = "type";
	public final static String TAB_TAG_MYSTORE = "mystore";
	public final static String TAB_TAG_CART = "cart";

	public final static String TAB_TAG_OPINION = "opinion";
	private int eventsize;
	
	private TabHost tabHost;
	
	/** 启动每个操作项的Intent */
	private Intent homeIntent;
	private Intent typeIntent;
	private Intent mystoreIntent;
	private Intent cartIntent;

	private Intent opinionIntent;
	
	/** 界面上的各个单选按钮 */
	private RadioButton btn_home;
	private RadioButton btn_type;
	private RadioButton btn_mystore;
	private RadioButton btn_cart;

	private RadioButton btn_opinoin;
	 private static final int NOTIFICATION_FLAG = 1;  
	private MyApp myApp;
	private int oldButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_host);
		myApp = (MyApp) TabHostActivity.this.getApplication();
		myApp.addActivity(this);
		


		homeIntent = new Intent(this, HomeActivity.class);

		if(myApp.getStatus().equals("0")){
			mystoreIntent = new Intent(this, MeActivity.class);
		}else if(myApp.getStatus().equals("1")){
			mystoreIntent = new Intent(this, MeActivity1.class);
		}else if(myApp.getStatus().equals("2")){
			mystoreIntent = new Intent(this, MeActivity2.class);
		}



		cartIntent = new Intent(this, XinwenList.class);
		opinionIntent = new Intent(this, MessageList.class);
		
		tabHost = this.getTabHost();
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_HOME).setIndicator(TAB_TAG_HOME).setContent(homeIntent));
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_TYPE).setIndicator(TAB_TAG_TYPE).setContent(typeIntent));
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_MYSTORE).setIndicator(TAB_TAG_MYSTORE).setContent(mystoreIntent));
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_CART).setIndicator(TAB_TAG_CART).setContent(cartIntent));
		tabHost.addTab(tabHost.newTabSpec(TAB_TAG_OPINION).setIndicator(TAB_TAG_OPINION).setContent(opinionIntent));
	
		////////////////////// find View ////////////////////////////
		btn_home = (RadioButton)this.findViewById(R.id.main_tab_home);
		btn_type = (RadioButton)this.findViewById(R.id.main_tab_type);
		btn_mystore = (RadioButton)this.findViewById(R.id.main_tab_mystore);
		btn_cart = (RadioButton)this.findViewById(R.id.main_tab_cart);
		btn_opinoin = (RadioButton)this.findViewById(R.id.main_tab_opinion);
		
		MyRadioButtonClickListener listener = new MyRadioButtonClickListener();
		btn_home.setOnClickListener(listener);
		btn_type.setOnClickListener(listener);
		btn_mystore.setOnClickListener(listener);
		btn_cart.setOnClickListener(listener);
		btn_opinoin.setOnClickListener(listener);
		
		oldButton = R.id.main_tab_home;
		myApp.setTabHost(tabHost);
		myApp.setMyStoreButton(btn_mystore);
//		myApp.setTypeButton(btn_type);
		myApp.setCartButton(btn_cart);
		myApp.setHomeButton(btn_home);
		myApp.setHomeButton(btn_opinoin);
		if(!myApp.getLoginKey().equals("")){
	      
		}
		
		
		
		
		
		
	}
	
	
	class MyRadioButtonClickListener implements View.OnClickListener{
		public void onClick(View v) {
			RadioButton btn = (RadioButton)v;
			switch(btn.getId()){
			case R.id.main_tab_home:
				oldButton = R.id.main_tab_home;
				tabHost.setCurrentTabByTag(TAB_TAG_HOME);
				break;
			case R.id.main_tab_type:
				oldButton = R.id.main_tab_type;
				tabHost.setCurrentTabByTag(TAB_TAG_TYPE);
				break;
			case R.id.main_tab_mystore:
				tabHost.setCurrentTabByTag(TAB_TAG_MYSTORE);
				break;
			case R.id.main_tab_cart:
				tabHost.setCurrentTabByTag(TAB_TAG_CART);
				break;
				case R.id.main_tab_opinion:
				tabHost.setCurrentTabByTag(TAB_TAG_OPINION);
				break;
			}
		}
	}
	
	
	

	
}
