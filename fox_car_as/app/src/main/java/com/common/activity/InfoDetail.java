package com.common.activity;

import java.io.IOException;
import java.util.ArrayList;



import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.blueberry.activity.R;
import com.common.adapter.GoodsDetailCommentsListViewAdapter;
import com.common.bean.CommentsList;
import com.common.custom.MyListView;
import com.common.util.HttpUtil;
import com.common.util.MyApp;
import com.common.util.MyBackAsynaTask;
import com.common.util.Util;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InfoDetail extends Activity {
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.title1)
	private TextView title11;
	@ViewInject(R.id.zan)
	private TextView zan;
	@ViewInject(R.id.leixing)
	private TextView leixing;
	@ViewInject(R.id.time)
	private TextView time;
	@ViewInject(R.id.baochou)
	private TextView baochou;
	@ViewInject(R.id.content)
	private TextView content;
	@ViewInject(R.id.add_new_xd)
	private TextView add_new_xd;
	@ViewInject(R.id.username)
	private TextView username;
	@ViewInject(R.id.imageView1)
	private ImageView imageView1;
	@ViewInject(R.id.shoucang)
	private ImageView shoucang;
	@ViewInject(R.id.dianzan)
	private ImageView dianzan;
	@ViewInject(R.id.goodsCommentsListView)
	private MyListView goodsCommentsListView;
	private String id;
	private String tag;
	private MyApp myApp;
	private String jsonString;
	private GoodsDetailCommentsListViewAdapter gooddetailcommentsAdapter;
	private ArrayList<CommentsList> lists = new ArrayList<CommentsList>();

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_xd_message);
		myApp = (MyApp) InfoDetail.this.getApplication();
		ViewUtils.inject(this);
		id = getIntent().getStringExtra("id");
		tag = getIntent().getStringExtra("tag");
//		title.setText(tag);
		if(tag.equals("资讯信息")){
			leixing.setVisibility(View.GONE);
			baochou.setVisibility(View.GONE);
//			shoucang.setVisibility(View.GONE);
		}
		gooddetailcommentsAdapter = new GoodsDetailCommentsListViewAdapter(InfoDetail.this);
		goodsCommentsListView.setAdapter(gooddetailcommentsAdapter);

		//loadingCommentsData(id);

	}
	@OnClick(R.id.add_new_xd)
	public void addNew(View view) {
		Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送到属性   
        intent.setType("text/plain"); // 分享发送到数据类型   
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject"); // 分享的主题   
        intent.putExtra(Intent.EXTRA_TEXT, "extratext"); // 分享的内容   
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 允许intent启动新的activity   
        startActivity(Intent.createChooser(intent, "分享")); // //目标应用选择对话框的  
	}
	@OnClick(R.id.btn_left)
	public void back(View view) {
		finish();
	}
	@OnClick(R.id.dianzan)
	public void dianzan(View view) {
		
//		dianzan(id);
	}
	@OnClick(R.id.shoucang)
	public void shoucang(View view) {
		
		shoucang(id);
	}
	@OnClick(R.id.pinglun)
	public void pinglun(View view) {
		
		Intent intent = new Intent(InfoDetail.this,AddDuanziComments.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}
	
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x125) {
				//

				Toast.makeText(InfoDetail.this, "发布成功!", Toast.LENGTH_LONG)
						.show();
				finish();
			}
			
			if (msg.what == 0x126) {
				//
				ArrayList<CommentsList> goods_list = CommentsList
						.newInstanceList(jsonString);
				lists.addAll(goods_list);

				gooddetailcommentsAdapter.setGoodsDatas(goods_list);
				gooddetailcommentsAdapter.notifyDataSetChanged();
			}
			if (msg.what == 0x127) {
				//
				
			}
			if (msg.what == 0x128) {
				//
				Toast.makeText(InfoDetail.this, "收藏成功", Toast.LENGTH_SHORT)
				.show();
				loadingGoodsDetailData(id);
			}
			if (msg.what == 0x129) {
				//
				Toast.makeText(InfoDetail.this, "收藏失败", Toast.LENGTH_SHORT)
				.show();
			}
			if (msg.what == 0x121) {
				//
				JSONObject obj;
				try {
					obj = new JSONObject(jsonString);
					String jsonStr = obj.optString("jsonString");
					System.out.println(jsonStr);
					JSONObject goods = new JSONObject(jsonStr);
					title11.setText("标题:" + goods.optString("title"));
					content.setText(goods.optString("content"));
					baochou.setVisibility(View.GONE);
					leixing.setText("类型:"+goods.optString("type2"));
					zan.setText("赞(+"+goods.optString("zan")+")");
					int status_=goods.optInt("status");
					username.setText("作者:" + goods.optString("author"));
					String imageurl_ = goods.optString("image_url").split(
							"\\\\")[1];
					String imageurl = HttpUtil.URL_BASEUPLOAD + imageurl_;
					
					MyBackAsynaTask asynaTask = new MyBackAsynaTask(imageurl,
							imageView1);
					asynaTask.execute();
					time.setText("发布日期：" + goods.optString("pubdate"));

					loadingCommentsData(id);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
			}
			if (msg.what == 0x122) {
				//
				Toast.makeText(InfoDetail.this, "报名失败", Toast.LENGTH_SHORT)
				.show();
			}
			if (msg.what == 0x123) {
				//
				Toast.makeText(InfoDetail.this, "收藏成功", Toast.LENGTH_SHORT)
				.show();
				loadingGoodsDetailData(id);
			}
			if (msg.what == 0x124) {
				//
				Toast.makeText(InfoDetail.this, "收藏失败", Toast.LENGTH_SHORT)
				.show();
			}
			if (msg.what == 0x111) {
				//
			}
			if (msg.what == 0x112) {
				//
			}
		};
	};
	public void loadingCommentsData(final String id) {
		
		new Thread() {
			public void run() {
				try {
					
					lists =new ArrayList<CommentsList>();
					String url = HttpUtil.URL_DUANZICOMMENTS + id;

					jsonString="";
					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

							try {
								JSONObject obj = new JSONObject(result);
								String arrlist = obj.optString("jsonString");
								// JSONObject obj = new JSONObject(json);
								if (arrlist != "" && !arrlist.equals("arrlist")
										&& arrlist != null && !arrlist.equals("[]")) {
									jsonString =arrlist;
									
									
									
									handler.sendEmptyMessage(0x126);
								} else {
									handler.sendEmptyMessage(0x127);
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
	public void shoucang(final String goods_id) {
		
		

		new Thread() {
			public void run() {
				try {
					
					String url = HttpUtil.URL_SHOUCANG +"folder.duanziid="+goods_id+"&folder.userid="+myApp.getLoginKey();
					

					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

							try {
								JSONObject obj = new JSONObject(result);
								String jsonStr = obj.optString("jsonString");
								if(jsonStr.equals("1")){
									
									handler.sendEmptyMessage(0x128);
								}else{
									handler.sendEmptyMessage(0x129);
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
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadingGoodsDetailData(id);

	}

	public void loadingGoodsDetailData(final String goods_id) {
		
		
		new Thread() {
			public void run() {
				try {
					
					String url = HttpUtil.URL_BIODETAIL + goods_id;
					

					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);
							jsonString=result;
							handler.sendEmptyMessage(0x121);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}.start();
		
		
	}
}
