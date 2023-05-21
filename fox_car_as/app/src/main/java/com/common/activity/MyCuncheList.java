package com.common.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.blueberry.activity.R;
import com.common.bean.CuncheBean;
import com.common.util.HttpUtil;
import com.common.util.MyApp;
import com.common.util.MyBackAsynaTask;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnItemClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyCuncheList extends Activity {
	@ViewInject(R.id.listView1)
	private ListView listView1;
	private List<CuncheBean> basemarkBeans = new ArrayList<CuncheBean>();
	@ViewInject(R.id.add_new_xd)
	private TextView add_new_xd;
	@ViewInject(R.id.address)
	private TextView address;
	@ViewInject(R.id.textView2)
	private TextView textView2;
	@ViewInject(R.id.searchbtn)
	private Button searchbtn;
	@ViewInject(R.id.searchcontent)
	private EditText searchcontent;
	private MyApp myApp;
	private GameAdapter adapter;
	private double longitude;
	private double latitude;
	private String jsonString;
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true; // 是否首次定位
	private String type;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		myApp = (MyApp) MyCuncheList.this.getApplication();
		setContentView(R.layout.my_cunche_xd_list);
		ViewUtils.inject(this);
		if (TextUtils.isEmpty(myApp.getLoginName())) {
			add_new_xd.setVisibility(View.INVISIBLE);
		}
		//type =getIntent().getStringExtra("type");

		//textView2.setText(type);

		searchbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getGameXD(searchcontent.getText().toString());
			}
		});
		adapter = new GameAdapter();
		listView1.setAdapter(adapter);

		getGameXD_();


	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	@OnClick(R.id.btn_left)
	public void back(View view) {
		finish();
	}

	@OnClick(R.id.address)
	public void addcity(View view) {
		startActivityForResult(new Intent(MyCuncheList.this, GetAddressInfoActivity.class), 10000);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (null != data && resultCode == Activity.RESULT_OK) {
			String addr ="";
			if(data.getStringExtra("province")!=null&&data.getStringExtra("city")==null){
				addr +=data.getStringExtra("province")+"市";
			}

			if(data.getStringExtra("province")!=null&&data.getStringExtra("city")!=null){
				addr +=data.getStringExtra("province")+"省";
			}
			if(data.getStringExtra("city")!=null){
				addr +=data.getStringExtra("city")+"市";
			}
			address.setText(addr);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 0x125) {
				//
				ArrayList<CuncheBean> goods_list = CuncheBean
						.newInstanceList(jsonString);
				basemarkBeans.addAll(goods_list);
				adapter.notifyDataSetChanged();
			}

			if (msg.what == 0x126) {
				//
				basemarkBeans.addAll(new ArrayList<CuncheBean>());
				adapter.notifyDataSetChanged();
			}
			if (msg.what == 0x127) {
				//
				ArrayList<CuncheBean> goods_list = CuncheBean
						.newInstanceList(jsonString);
				basemarkBeans.addAll(goods_list);
				adapter.notifyDataSetChanged();
			}
			if (msg.what == 0x128) {
				//
				basemarkBeans.addAll(new ArrayList<CuncheBean>());
				adapter.notifyDataSetChanged();
			}
			if (msg.what == 0x121) {
				//
				Toast.makeText(MyCuncheList.this, "操作成功",
						Toast.LENGTH_SHORT).show();
				Intent intent =new Intent(MyCuncheList.this,TabHostActivity.class);
				startActivity(intent);
			}
			if (msg.what == 0x122) {
				//
				Toast.makeText(MyCuncheList.this, "操作失败",
						Toast.LENGTH_SHORT).show();
			}

			if (msg.what == 0x123) {
				//
				Toast.makeText(MyCuncheList.this, "操作成功",
						Toast.LENGTH_SHORT).show();
				getGameXD_();
			}
			if (msg.what == 0x124) {
				//
				Toast.makeText(MyCuncheList.this, "操作失败",
						Toast.LENGTH_SHORT).show();
			}
		};
	};
	@SuppressLint("NewApi")
	public void getGameXD(final String keywords) {

		new Thread() {
			public void run() {
				try {
					basemarkBeans = new ArrayList<CuncheBean>();
					String url = HttpUtil.URL_DUANZILIST0 + "?keyword=" + keywords;


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
	@SuppressLint("NewApi")
	public void getGameXD_() {



		new Thread() {
			public void run() {
				try {
					basemarkBeans = new ArrayList<CuncheBean>();
					String url = HttpUtil.URL_LISTCUNCHE_MY+myApp.getLoginKey();


					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

							try {
								JSONObject obj = new JSONObject(result);
								String arrlist = obj.optString("jsonString");
								// JSONObject obj = new JSONObject(json);
								if (arrlist != "" && !arrlist.equals("arrlist")
										&& arrlist != null && !arrlist.equals("[]")) {
									jsonString =arrlist;



									handler.sendEmptyMessage(0x127);
								} else {
									handler.sendEmptyMessage(0x128);
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
	@SuppressLint("NewApi")
	public void jiesuan(final String id,final float money) {


		new Thread() {
			public void run() {
				try {
					String url = HttpUtil.URL_JIESUAN1_ + id+"&cunche.money="+money;


					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

					try {
						JSONObject obj = new JSONObject(result);
						String arrlist = obj.optString("jsonString");
						// JSONObject obj = new JSONObject(json);
						if (arrlist != "" && !arrlist.equals("arrlist")
								&& arrlist != null && !arrlist.equals("[]")) {
							if(arrlist.equals("1")){

								handler.sendEmptyMessage(0x123);
							}else{

								handler.sendEmptyMessage(0x124);
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
	@SuppressLint("NewApi")
	public void del(final String id,final String  car_no) {


		new Thread() {
			public void run() {
				try {
					String url = HttpUtil.URL_DEL1_ + id+"&cunche.car_no="+car_no;


					String result = null;
					result = HttpUtil.queryStringConnectForPost(url);

					try {
						JSONObject obj = new JSONObject(result);
						String arrlist = obj.optString("jsonString");
						// JSONObject obj = new JSONObject(json);
						if (arrlist != "" && !arrlist.equals("arrlist")
								&& arrlist != null && !arrlist.equals("[]")) {
							if(arrlist.equals("1")){

								handler.sendEmptyMessage(0x123);
							}else{

								handler.sendEmptyMessage(0x124);
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
		private ArrayList<CuncheBean> duanziList;

		private GameAdapter() {
			inflater = LayoutInflater.from(MyCuncheList.this);
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
						.inflate(R.layout.cunche_xd_list_item, null);
				holder = new Holder();
				holder.image_view = (ImageView) convertView
						.findViewById(R.id.image_view);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.title_ = (TextView) convertView.findViewById(R.id.title_);
				holder.time = (TextView) convertView.findViewById(R.id.times);
				holder.username = (TextView) convertView
						.findViewById(R.id.username);
				convertView.setTag(holder);
			} else {
				holder = (Holder) convertView.getTag();
			}
			String status = basemarkBeans.get(position).getStatus();
			String status_ = "";
			if (status.equals("0")) {
				status_ = "还未租车";
			}
			if(status.equals("1")){
				status_="待出租";
			}
			if(status.equals("2")){
				status_="已出租";
			}
			if(status.equals("3")){
				status_="车主已取车";
			}
			if(status.equals("4")){
				status_="车主已取车，租车已结算";
			}

			if(status.equals("1")){


				holder.title_.setVisibility(View.GONE);
				holder.title_.setTextColor(Color.GREEN);//
				holder.title_.setText("状态：待出租状态");
			}else{
				holder.title_.setVisibility(View.GONE);
				holder.title_.setText("状态：不可出租状态");
				holder.title_.setTextColor(Color.RED);//
			}
			holder.time.setVisibility(View.GONE);
			holder.username.setVisibility(View.GONE);



			holder.title.setText(
					"车牌号:" + basemarkBeans.get(position).getCar_no()
							+"\n租车人:" + basemarkBeans.get(position).getUsername()
							+"\n租车时间" + basemarkBeans.get(position).getCunche_start_date_str()
							+"\n取车时间:" + basemarkBeans.get(position).getCunche_end_date_str()
							+"\n租车收入:" + basemarkBeans.get(position).getMoney2()+"元"
							+"\n应付金额:" + basemarkBeans.get(position).getMoney()+"元"
							+"\n车辆状态:" + status_);
			String imagename ="";
			String[] split = basemarkBeans.get(position).getImage_url()
					.split("\\\\");
			if(split.length>1){
				imagename=split[1];
			}
			MyBackAsynaTask asynaTask = new MyBackAsynaTask(
					HttpUtil.URL_BASEUPLOAD + imagename, holder.image_view);
			asynaTask.execute();
			return convertView;
		}

		public ArrayList<CuncheBean> getDuanziList() {
			return duanziList;
		}

		public void setDuanziList(ArrayList<CuncheBean> duanziList) {
			this.duanziList = duanziList;
		}

	}

	private class Holder {
		ImageView image_view;
		TextView title;
		TextView title_;
		TextView time;
		TextView username;
	}

	@OnItemClick(R.id.listView1)
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, final long id) {
		// TODO Auto-generated method stub
		if(basemarkBeans.get(position).getStatus().equals("2")){
			Toast.makeText(MyCuncheList.this, "车辆正在出租中",
					Toast.LENGTH_SHORT).show();
		}
		if(basemarkBeans.get(position).getStatus().equals("3")){
			Toast.makeText(MyCuncheList.this, "车主已取车",
					Toast.LENGTH_SHORT).show();
		}
		if(basemarkBeans.get(position).getStatus().equals("4")){
			Toast.makeText(MyCuncheList.this, "车主已取车，租车已结算",
					Toast.LENGTH_SHORT).show();
		}

		if(basemarkBeans.get(position).getStatus().equals("0")||basemarkBeans.get(position).getStatus().equals("1")){
			new AlertDialog.Builder(MyCuncheList.this)
					.setTitle("提示")
					.setMessage("操作提示")
					.setNegativeButton("我要取车",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
													int which) {
									// TODO Auto-generated method stub
									//shenhe(basemarkBeans.get(position).getId());
//								Intent intent =new Intent(MyCuncheList.this,AddZuche.class);
//								intent.putExtra("start_limit",basemarkBeans.get(position).getCunche_start_date_str());
//								intent.putExtra("end_limit",basemarkBeans.get(position).getCunche_end_date_str());
//								intent.putExtra("image_url",basemarkBeans.get(position).getImage_url());
//								intent.putExtra("car_no",basemarkBeans.get(position).getCar_no());
//								startActivity(intent);

									float money=Float.parseFloat(basemarkBeans.get(position).getMoney());
									float money2=Float.parseFloat(basemarkBeans.get(position).getMoney2());

									float money3=money2*0.03f;

									float money5=0f;
									if(money>money3){
										money5=money-money3;
									}
									else if(money<=money3){
										money5=0f;
									}


									final float finalMoney = money5;
									new AlertDialog.Builder(MyCuncheList.this)
											.setTitle("费用信息")
											.setMessage("车辆存管费用："+money+"元\n租车收入充抵:"+money3+"元\n实际须缴纳存车费:"+money5+"元")
											.setNegativeButton("进行结算",
													new DialogInterface.OnClickListener() {

														@Override
														public void onClick(DialogInterface dialog,
																			int which) {
															// TODO Auto-generated method stub

															jiesuan(basemarkBeans.get(position).getId(), finalMoney);
														}
													})
											.setPositiveButton("取消",
													new DialogInterface.OnClickListener() {
														@Override
														public void onClick(DialogInterface dialog,
																			int which) {
															// TODO Auto-generated method stub
															dialog.dismiss();
														}
													}).show();
								}
							})
					.setPositiveButton("取消订单",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
													int which) {
									// TODO Auto-generated method stub
									del(basemarkBeans.get(position).getId(),basemarkBeans.get(position).getCar_no());
								}
							}).show();
		}





	}
}
