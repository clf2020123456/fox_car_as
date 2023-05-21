package com.common.activity;

import java.util.ArrayList;
import java.util.List;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blueberry.activity.R;
import com.common.bean.CommentsList;
import com.common.bean.DuanziBean;
import com.common.util.HttpUtil;
import com.common.util.MyApp;
import com.common.util.Util;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

public class MessageList extends Activity {
	@ViewInject(R.id.listView1)
	private ListView listView1;
	private List<CommentsList> basemarkBeans = new ArrayList<CommentsList>();
	@ViewInject(R.id.add_new_xd)
	private TextView add_new_xd;
	private MyApp myApp;
	private GameAdapter adapter;
	private String jsonString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp = (MyApp) MessageList.this.getApplication();
		setContentView(R.layout.message_list);
		ViewUtils.inject(this);
		if (TextUtils.isEmpty(myApp.getLoginName())) {
			add_new_xd.setVisibility(View.INVISIBLE);
		}
		
		adapter = new GameAdapter();
		listView1.setAdapter(adapter);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getGameXD();
		
	}

	@OnClick(R.id.btn_left)
	public void back(View view) {
		finish();
	}

	@OnClick(R.id.add_new_xd)
	public void addNew(View view) {
		Util.toIntent(MessageList.this, AddMessage.class);
	}

	public void getGameXD() {
		
		new Thread() {
			public void run() {
				try {
					String url = HttpUtil.URL_MESSAGELIST+myApp.getLoginKey();
					

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
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x125) {
				//
			}
			
			if (msg.what == 0x126) {
				//
				basemarkBeans=new ArrayList<>();
				ArrayList<CommentsList> goods_list = CommentsList
						.newInstanceList(jsonString);
				basemarkBeans.addAll(goods_list);
				adapter.notifyDataSetChanged();
			}
			if (msg.what == 0x127) {
				//
				
			}
		};
	};
	private class GameAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private ArrayList<DuanziBean> duanziList;

		private GameAdapter() {
			inflater = LayoutInflater.from(MessageList.this);
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
						.inflate(R.layout.message_list_item, null);
				holder = new Holder();
				holder.content = (TextView) convertView.findViewById(R.id.content);
				holder.time = (TextView) convertView.findViewById(R.id.times);
				holder.username = (TextView) convertView
						.findViewById(R.id.username);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			holder.time.setText("反馈时间:"+basemarkBeans.get(position).getCommitdate());
			holder.username.setText("反馈人:"+basemarkBeans.get(position).getUsername());
			holder.content.setText("反馈内容:"+basemarkBeans.get(position).getContent());
			
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
		TextView content;
		TextView time;
		TextView username;
	}

	@OnItemClick(R.id.listView1)
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
	}
}
