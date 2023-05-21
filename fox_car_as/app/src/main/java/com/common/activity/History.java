package com.common.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blueberry.activity.R;
import com.common.bean.DuanziBean;
import com.common.util.HttpUtil;
import com.common.util.MyApp;
import com.common.util.MyBackAsynaTask;
import com.common.util.Util;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class History extends Activity {
	@ViewInject(R.id.listView1)
	private ListView listView1;
	private List<DuanziBean> basemarkBeans = new ArrayList<DuanziBean>();
	@ViewInject(R.id.add_new_xd)
	private TextView add_new_xd;
	@ViewInject(R.id.searchbtn)
	private Button searchbtn;
	@ViewInject(R.id.searchcontent)
	private EditText searchcontent;
	private MyApp myApp;
	private GameAdapter adapter;
	private String jsonString;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp = (MyApp) History.this.getApplication();
		setContentView(R.layout.game_xd_list2);
		ViewUtils.inject(this);
		if (TextUtils.isEmpty(myApp.getLoginName())) {
			add_new_xd.setVisibility(View.INVISIBLE);
		}
	
		searchbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getGameXD(searchcontent.getText().toString());
			}
		});
		adapter = new GameAdapter();
		listView1.setAdapter(adapter);
		
		getGameXD("");

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@OnClick(R.id.top_back)
	public void back(View view) {
		finish();
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x125) {
				//
				ArrayList<DuanziBean> goods_list = DuanziBean
						.newInstanceList(jsonString);
				basemarkBeans.addAll(goods_list);
				adapter.notifyDataSetChanged();
			}
			
			if (msg.what == 0x126) {
				//
			}
			if (msg.what == 0x127) {
				//
				Toast.makeText(History.this, "操作成功", Toast.LENGTH_SHORT).show();
				getGameXD("");
			}
			if (msg.what == 0x128) {
				Toast.makeText(History.this, "操作失败", Toast.LENGTH_SHORT).show();
				//
			}
			if (msg.what == 0x121) {
				//
				Toast.makeText(History.this, "操作成功", Toast.LENGTH_SHORT).show();
				getGameXD("");
			}
			if (msg.what == 0x122) {
				//
				Toast.makeText(History.this, "操作失败", Toast.LENGTH_SHORT).show();
			}
			if (msg.what == 0x123) {
				//
				Toast.makeText(History.this, "操作成功", Toast.LENGTH_SHORT).show();
				getGameXD("");
			}
			if (msg.what == 0x124) {
				//
				Toast.makeText(History.this, "操作失败", Toast.LENGTH_SHORT).show();
			}
			
			if (msg.what == 0x111) {
				//
				Toast.makeText(History.this, "操作成功", Toast.LENGTH_SHORT).show();
				getGameXD("");
			}
			if (msg.what == 0x112) {
				//
				Toast.makeText(History.this, "操作失败", Toast.LENGTH_SHORT).show();
			}
			
			
			if (msg.what == 0x113) {
				//
				Toast.makeText(History.this, "操作成功", Toast.LENGTH_SHORT).show();
				getGameXD("");
			}
			if (msg.what == 0x114) {
				//
				Toast.makeText(History.this, "操作失败", Toast.LENGTH_SHORT).show();
			}
			
			if (msg.what == 0x115) {
				//
				Toast.makeText(History.this, "操作成功", Toast.LENGTH_SHORT).show();
				getGameXD("");
			}
			if (msg.what == 0x116) {
				//
				Toast.makeText(History.this, "操作失败", Toast.LENGTH_SHORT).show();
			}
		};
	};
	@SuppressLint("NewApi") public void getGameXD(String keywords) {
		
		
		new Thread() {
			public void run() {
				try {
					basemarkBeans = new ArrayList<DuanziBean>();
					String url = HttpUtil.URL_DUANZILIST2+myApp.getLoginKey();

					

					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

							try {
								JSONObject obj = new JSONObject(result);
								String arrlist = obj.optString("jsonString");
								// JSONObject obj = new JSONObject(json);
								if (arrlist != "" && !arrlist.equals("arrlist")
										&& arrlist != null && !arrlist.equals("[]")) {
									jsonString =arrlist;
									
									
									
									handler.sendEmptyMessage(0x125);
								} else {
									handler.sendEmptyMessage(0x126);
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
	@SuppressLint("NewApi") public void jiedan(final String id) {
		
		
		new Thread() {
			public void run() {
				try {
					
					basemarkBeans = new ArrayList<DuanziBean>();
					String url = HttpUtil.URL_WANCHENG+"?biotech.id="+id+"&biotech.get_userid="+myApp.getLoginKey();
					

					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

							try {
								JSONObject obj = new JSONObject(result);
								String arrlist = obj.optString("jsonString");
								// JSONObject obj = new JSONObject(json);
								if (arrlist != "" && !arrlist.equals("arrlist")
										&& arrlist != null && !arrlist.equals("[]")) {
									if(arrlist.equals("1")){
								
											handler.sendEmptyMessage(0x121);
									}else{
									
										handler.sendEmptyMessage(0x122);
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

	private class GameAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ArrayList<DuanziBean> duanziList;

		private GameAdapter() {
			inflater = LayoutInflater.from(History.this);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return basemarkBeans.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return basemarkBeans.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			Holder holder;
			if (convertView == null) {
				convertView = inflater
						.inflate(R.layout.game_xd_list_item, null);
				holder = new Holder();
				holder.image_view = (ImageView) convertView
						.findViewById(R.id.image_view);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.time = (TextView) convertView.findViewById(R.id.times);
				holder.username = (TextView) convertView
						.findViewById(R.id.username);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			String status =basemarkBeans.get(position).getStatus();
			String status2 = basemarkBeans.get(position).getStatus2();
			String status_="";
			if(status.equals("0")){
				status_="已发起，待物业处理";
			}
			if(status.equals("1")){
				status_="物业已派单,正在处理中";
			}
			if(status.equals("2")){
				status_="已完成处理,待业务确认";
			}
			if(status.equals("4")){
				status_="业主已完成确认,已归档";
			}
			if(status.equals("3")){
				status_="超过48小时,已取消";
			}
			if(status2.equals("0")){
//				holder.title_.setVisibility(View.GONE);
				holder.title.setVisibility(View.VISIBLE);
				holder.title.setText("状态：未通过审核");
				holder.title.setTextColor(Color.parseColor("#FF0000"));
			}else{
				holder.title.setVisibility(View.VISIBLE);
				holder.title.setText("状态：审核通过");
			}
			holder.time.setText("发布时间:"
					+ basemarkBeans.get(position).getUpdatetime());
			holder.username.setText("发布人:"
					+ basemarkBeans.get(position).getAuthor());
			if (!status.equals("0")) {
				holder.title.setText(
						"订单编号:" + basemarkBeans.get(position).getOrder_no()
								+"\n发货人:" + basemarkBeans.get(position).getSender()
								+"\n收货人:" + basemarkBeans.get(position).getReceiver()
								+"\n发货地址:" + basemarkBeans.get(position).getFrom_address()
								+"\n收货地址:" + basemarkBeans.get(position).getTo_address()
								+"\n订单状态:" + status_
								+"\n运输物业工作人员:" + basemarkBeans.get(position).getGet_username()
				);
			}else {
				holder.title.setText(
						"订单编号:" + basemarkBeans.get(position).getOrder_no()
								+ "\n发货人:" + basemarkBeans.get(position).getSender()
								+ "\n收货人:" + basemarkBeans.get(position).getReceiver()
								+ "\n发货地址:" + basemarkBeans.get(position).getFrom_address()
								+ "\n收货地址:" + basemarkBeans.get(position).getTo_address()
								+ "\n订单状态:" + status_
				);
			}

			String imagename = basemarkBeans.get(position).getImage_url()
					.split("\\\\")[1];
			MyBackAsynaTask asynaTask = new MyBackAsynaTask(
					HttpUtil.URL_BASEUPLOAD + imagename, holder.image_view);
			asynaTask.execute();
			return convertView;
		}

		public ArrayList<DuanziBean> getDuanziList() {
			return duanziList;
		}

		public void setDuanziList(ArrayList<DuanziBean> duanziList) {
			this.duanziList = duanziList;
		}

	}

	private class Holder {
		ImageView image_view;
		TextView title;
		TextView time;
		TextView username;
	}

	@OnItemClick(R.id.listView1)
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	/*	Intent intent = new Intent(this, InfoDetail.class);
		intent.putExtra("id", basemarkBeans.get(position).getId());
		intent.putExtra("tag", "");
		startActivity(intent);*/
	}
}
