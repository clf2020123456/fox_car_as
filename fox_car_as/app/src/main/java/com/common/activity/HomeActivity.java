package com.common.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blueberry.activity.R;
import com.common.util.Constants;
import com.common.util.MyApp;
import com.common.util.SystemHelper;
import com.common.util.Util;
import com.lidroid.xutils.view.annotation.ViewInject;

public class HomeActivity extends Activity {
	private LinearLayout ll_point;
	private ArrayList<View> arrayList;
	private ArrayList<ImageView> imageViews;
	private Timer timer;
	private MyApp myApp;
	private LayoutInflater HeadlayoutInflater;
	private ViewPager viewPager;
	private int i;
	private int count;
	private TextView textHomeSearch;
	private ScrollView myScrollView;
	private ListView listRecommendGoods;
	private LinearLayout linearLayoutCategory0;
	private LinearLayout linearLayoutCategory1;
	private LinearLayout linearLayoutCategory2;
	private LinearLayout linearLayoutCategory3;
	private LinearLayout linearLayoutCategory4;
	private LinearLayout linearLayoutCategory5;
	private LinearLayout linearLayoutCategory6;
	private LinearLayout linearLayoutCategory7;
	private LinearLayout circle_layout;
	private Button buttonSeeAll;
	private Button buttonClickSign;
	private Button buttonHomeLogin;
	private LinearLayout linearLayoutHomeLogin;

	private Button btn_left;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		myApp = (MyApp) HomeActivity.this.getApplication();

		btn_left = (Button) findViewById(R.id.btn_left);
		myScrollView = (ScrollView) findViewById(R.id.myScrollView);
		linearLayoutCategory0 = (LinearLayout) findViewById(R.id.linearLayoutCategory0);
		linearLayoutCategory1 = (LinearLayout) findViewById(R.id.linearLayoutCategory1);
		linearLayoutCategory2 = (LinearLayout) findViewById(R.id.linearLayoutCategory2);
		linearLayoutCategory3 = (LinearLayout) findViewById(R.id.linearLayoutCategory3);
		linearLayoutCategory4 = (LinearLayout) findViewById(R.id.linearLayoutCategory4);
		linearLayoutCategory5 = (LinearLayout) findViewById(R.id.linearLayoutCategory5);
		linearLayoutHomeLogin = (LinearLayout) findViewById(R.id.linearLayoutHomeLogin);
		CategoryOnClickListener categoryLister = new CategoryOnClickListener();
		linearLayoutCategory0.setOnClickListener(categoryLister);
		linearLayoutCategory1.setOnClickListener(categoryLister);
		linearLayoutCategory2.setOnClickListener(categoryLister);
		linearLayoutCategory3.setOnClickListener(categoryLister);
		linearLayoutCategory4.setOnClickListener(categoryLister);
		linearLayoutCategory5.setOnClickListener(categoryLister);
		RegisterBroadcastReceiver();
		btn_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

                new AlertDialog.Builder(HomeActivity.this)
                        .setMessage("是否退出程序")

                        .setPositiveButton("确认",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stub

                                        System.exit(0);//直接结束程序
                                    }
                                }).setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // TODO Auto-generated method stub

                                finish();
                            }
                        }).show();

			}
		});
        loadingHomeData();

	}
	 public void loadingHomeData() {
	    	initHeadImage();
	    }
	 
	    public void initHeadImage() {
	        viewPager = (ViewPager) findViewById(R.id.viewpager);
	        ll_point = (LinearLayout) findViewById(R.id.ll_point);
	        initPagerChild();
	        int sw = SystemHelper.getScreenInfo(HomeActivity.this).widthPixels;
	        int sh = SystemHelper.getScreenInfo(HomeActivity.this).heightPixels;
	        int h = 450;
	        RelativeLayout.LayoutParams childLinerLayoutParames = new RelativeLayout.LayoutParams(
	                LinearLayout.LayoutParams.FILL_PARENT, h);
	        viewPager.setLayoutParams(childLinerLayoutParames);

	        viewPager.setAdapter(new ViewPagerAdapter(arrayList));
	        draw_Point(0);// 默认首次进入

	        timer = new Timer(true);
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                runOnUiThread(new Runnable() {
	                    @Override
	                    public void run() {
	                        int index = viewPager.getCurrentItem();
	                        if (index == arrayList.size() - 1)
	                            index = 0;
	                        else
	                            index++;
	                        viewPager.setCurrentItem(index);

	                    }
	                });
	            }
	        }, 5000, 5000);

	        /***
	         * viewpager PageChangeListener
	         */
	        viewPager.setOnPageChangeListener(new OnPageChangeListener() {
	            @Override
	            public void onPageSelected(int arg0) {
	                draw_Point(arg0);
	            }

	            @Override
	            public void onPageScrolled(int arg0, float arg1, int arg2) {
	            }

	            @Override
	            public void onPageScrollStateChanged(int arg0) {
	            }
	        });
	    }

		public void initPagerChild() {
	    	arrayList = new ArrayList<View>();
	        ImageView imageView0 = new ImageView(HomeActivity.this);
	        imageView0.setScaleType(ScaleType.FIT_XY);
	        imageView0.setImageResource(R.drawable.banner01);

	        ImageView imageView1 = new ImageView(HomeActivity.this);
	        imageView1.setScaleType(ScaleType.FIT_XY);
	        imageView1.setImageResource(R.drawable.banner02);
	        
	        ImageView imageView2 = new ImageView(HomeActivity.this);
	        imageView2.setScaleType(ScaleType.FIT_XY);
	        imageView2.setImageResource(R.drawable.banner03);
	     
	      
	        

	        arrayList.add(imageView0);
	        arrayList.add(imageView1);
	        arrayList.add(imageView2);
	    initPoint();
	}
	    public void initPoint() {
	        imageViews = new ArrayList<ImageView>();
	        ImageView imageView;

	        for (i = 0; i < 3; i++) {
	            imageView = new ImageView(this);
	            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
	                    new LayoutParams(LayoutParams.WRAP_CONTENT,
	                            LayoutParams.WRAP_CONTENT));
	            layoutParams.leftMargin = 10;
	            layoutParams.rightMargin = 10;
	            ll_point.addView(imageView, layoutParams);

	            imageViews.add(imageView);
	        }
	    }
	private BroadcastReceiver homeBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action == Constants.APP_BORADCASTRECEIVER) {
				linearLayoutHomeLogin.setVisibility(View.GONE);
			}
		}
	};

	public void RegisterBroadcastReceiver() {
		IntentFilter homeIntentFilter = new IntentFilter();
		homeIntentFilter.addAction(Constants.APP_BORADCASTRECEIVER);
		HomeActivity.this.registerReceiver(homeBroadcastReceiver,
				homeIntentFilter);
	}

	public class CategoryOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = null;
			Bundle b = new Bundle();
			switch (v.getId()) {
			case R.id.linearLayoutCategory0:
				if (myApp.getLoginKey() != "") {
					intent = new Intent(HomeActivity.this,
							MessageList.class);
				} else {
					intent = new Intent(HomeActivity.this, Login.class);
				}
				break;
			case R.id.linearLayoutCategory1:
				if (myApp.getLoginKey() != "") {
//					if(!myApp.getStatus().equals("0")){
						intent = new Intent(HomeActivity.this, MyZucheList.class);
//					}else{
//						Toast.makeText(HomeActivity.this,"抱歉,您没有权限操作",Toast.LENGTH_SHORT).show();
//					}
				} else {
					intent = new Intent(HomeActivity.this, Login.class);
				}
				break;
			case R.id.linearLayoutCategory2:
				if (myApp.getLoginKey() != "") {
					intent = new Intent(HomeActivity.this, XinwenList.class);
				} else {
					intent = new Intent(HomeActivity.this, Login.class);
				}
				break;
			case R.id.linearLayoutCategory3:
					if (myApp.getLoginKey() != "") {
//						if(!myApp.getStatus().equals("2")){
							intent = new Intent(HomeActivity.this, MyCuncheList.class);
//						}else{
//							Toast.makeText(HomeActivity.this,"抱歉,您没有权限操作",Toast.LENGTH_SHORT).show();
//						}

				} else {
					intent = new Intent(HomeActivity.this, Login.class);
				}
				break;
			case R.id.linearLayoutCategory4:
				if (myApp.getLoginKey() != "") {
//					if(!myApp.getStatus().equals("2")){
						intent = new Intent(HomeActivity.this, AddCunche.class);
//					}else{
//						Toast.makeText(HomeActivity.this,"抱歉,您没有权限操作",Toast.LENGTH_SHORT).show();
//					}

				} else {
					intent = new Intent(HomeActivity.this, Login.class);
				}
				break;
			case R.id.linearLayoutCategory5:
				if (myApp.getLoginKey() != "") {
//					if(!myApp.getStatus().equals("0")){
						intent = new Intent(HomeActivity.this, CuncheList0.class);
//					}else{
//						Toast.makeText(HomeActivity.this,"抱歉,您没有权限操作",Toast.LENGTH_SHORT).show();
//					}

				} else {
					intent = new Intent(HomeActivity.this, Login.class);
				}
				break;
			}
			if (intent != null) {
				startActivity(intent);
			}
		}
	}

	/***
	 * 更新选中点
	 * 
	 * @param index
	 */
	public void draw_Point(int index) {
		for (int i = 0; i < imageViews.size(); i++) {
			imageViews.get(i).setImageResource(R.drawable.point_gray);
		}
		imageViews.get(index).setImageResource(R.drawable.point_red);
	}

	/***
	 * 对图片处理
	 * 
	 * @author zhangjia
	 */
	public Bitmap getBitmap(Bitmap bitmap, int width) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scale = (float) width / w;
		// 保证图片不变形.
		matrix.postScale(scale, scale);
		// w,h是原图的属性.
		return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
	}

	public class ViewPagerAdapter extends PagerAdapter {
		// 界面列表
		private List<View> views;

		public ViewPagerAdapter(List<View> views) {
			this.views = views;
		}

		// 销毁arg1位置的界面
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(views.get(arg1));
		}

		// 获得当前界面数
		@Override
		public int getCount() {
			if (views != null) {
				// 返回一个比较大的数字
				return views.size();
			}
			return 0;
		}

		// 初始化arg1位置的界面
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(views.get(arg1));
			return views.get(arg1);
		}

		// 判断是否由对象生成界面
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return (arg0 == arg1);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		count = 1;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(homeBroadcastReceiver);
	}

	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// count++;
	// if (count >= 3) {
	// HomeActivity.this.finish();
	// return true;
	// } else {
	// Toast.makeText(HomeActivity.this, "再点击一次退出程序",
	// Toast.LENGTH_SHORT).show();
	// return true;
	// }
	// } else {
	// return super.onKeyDown(keyCode, event);
	// }
	// }
}
