package com.common.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blueberry.activity.R;
import com.common.bean.LoginBean;
import com.lidroid.xutils.util.LogUtils;

public class Util {
	private static LoginBean usersBean;
	
	public static LoginBean getUsersBean() {
		return usersBean;
	}

	public static void setUsersBean(LoginBean usersBean) {
		Util.usersBean = usersBean;
	}

	public static ExecutorService executorService = Executors
			.newFixedThreadPool(10);

	public static void Toast(Context context, String string) {
		android.widget.Toast.makeText(context, string, Toast.LENGTH_SHORT)
				.show();
	}
	/**
	 * 放大缩小图片
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		Bitmap newbmp = null;
		if (bitmap != null) {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Matrix matrix = new Matrix();
			float scaleWidht = ((float) w / width);
			float scaleHeight = ((float) h / height);
			matrix.postScale(scaleWidht, scaleHeight);
			newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,
					true);
		}
		return newbmp;
	}
	/**
	 　　 *字符串的日期格式的计算
	 　　 */
	public static int daysBetween(String smdate,String bdate) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	public static void asynTask(final Context frame, final IAsynTask task) {
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				String dataType = data.getString("IAsynTaskResult");
				Serializable ser = data.getSerializable("IAsynTaskRunData");
				if ("success".equals(dataType))
					task.updateUI(ser);
				else if ("error".equals(dataType)) {
					Toast.makeText(frame, "网络异常，请稍候再试！", Toast.LENGTH_SHORT)
							.show();
					System.err.println("--------------异步订单错误！-------------");
					if (null == ser)
						Log.e("Util异步订单错误！", ((Throwable) ser) + "");
					else
						((Throwable) ser).printStackTrace();
				} else {
					task.updateUI(null);
				}
			}
		};

		executorService.execute(new Runnable() {
			// new Thread(new Runnable(){
			public void run() {
				Message msg = new Message();
				Bundle data = new Bundle();
				try {
					data.putSerializable("IAsynTaskRunData", task.run());
					data.putString("IAsynTaskResult", "success");
				} catch (Throwable e) {
					LogUtils.e("------------------异步订单错误！-----------------", e);
					data.putSerializable("IAsynTaskRunData", e);
					data.putString("IAsynTaskResult", "error");
				}
				msg.setData(data);
				handler.sendMessage(msg);
			}
		});
	}

	public static void asynTask(final Context frame, final String message,
			final IAsynTask task) {
		final ProgressDialog pd = createLoadingDialog(frame, message);
		pd.setCancelable(true);
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				String dataType = data.getString("IAsynTaskResult");
				Serializable ser = data.getSerializable("IAsynTaskRunData");
				if ("success".equals(dataType))
					task.updateUI(ser);
				else if ("error".equals(dataType)) {
					Toast.makeText(frame, "网络异常，请稍候再试！", Toast.LENGTH_SHORT)
							.show();
					System.err.println("--------------异步订单错误！-------------");
					if (null == ser)
						Log.e("Util异步订单错误！", ((Throwable) ser) + "");
					else
						((Throwable) ser).printStackTrace();
				} else {
					task.updateUI(null);
				}
				if (null != pd) {
					pd.dismiss();
					pd.cancel();
				}
			}
		};

		executorService.execute(new Runnable() {
			// new Thread(new Runnable(){
			public void run() {
				Message msg = new Message();
				Bundle data = new Bundle();
				try {
					data.putSerializable("IAsynTaskRunData", task.run());
					data.putString("IAsynTaskResult", "success");
				} catch (Throwable e) {
					LogUtils.e("------------------异步订单错误！-----------------", e);
					data.putSerializable("IAsynTaskRunData", e);
					data.putString("IAsynTaskResult", "error");
				}
				msg.setData(data);
				handler.sendMessage(msg);
			}
		});
	}

	public static ProgressDialog createLoadingDialog(Context context, String msg) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.load_animation);
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);
		ProgressDialog loadingDialog = ProgressDialog.show(context, null, msg);
		loadingDialog.setCancelable(true);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		return loadingDialog;
	}

	public static String toUTF(String string) {
		try {
			return URLEncoder.encode(string, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 验证只包含中文英文
	 */
	public static boolean checkZNAndEN(String string) {
		String regex = "^[a-zA-Z0-9\u4E00-\u9FA5]+$";
		Pattern pattern = Pattern.compile(regex);
		Matcher match = pattern.matcher(string);
		return match.matches();
	}

	public static void showToast(Context context,
			String string) {
		// TODO Auto-generated method stub
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
		
	}
	public static <T> void toIntent(Context context,Class<T> c){
		Intent intent=new Intent(context, c);
		context.startActivity(intent);
	}
	/**
	 * dp转px
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dpToPx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int pxToDp(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static Bitmap getLocalBitmap(String path,
			BitmapFactory.Options options, Context c) {
		if (TextUtils.isEmpty(path))
			return null;
		Bitmap temBitmap = null;
		try {
			temBitmap = BitmapFactory.decodeFile(path, options);
			if (temBitmap == null) {
				temBitmap = BitmapFactory.decodeResource(c.getResources(),
						R.drawable.zw174);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			temBitmap = BitmapFactory.decodeResource(c.getResources(),
					R.drawable.zw174);
		}
		return temBitmap;
	}
	/**
	 * 加载本地缩略图
	 * 
	 * @param path
	 * @param showWidth
	 * @param showHeight
	 * @return
	 */
	public static Bitmap getLocationThmub(String path, int showWidth,
			int showHeight) {
		// 对于图片的二次采样,主要得到图片的宽与高
		int width = 0;
		int height = 0;
		int sampleSize = 1; // 默认缩放为1
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 仅仅解码边缘区域
		// 如果指定了inJustDecodeBounds，decodeByteArray将返回为空
		BitmapFactory.decodeFile(path, options);
		// 得到宽与高
		height = options.outHeight;
		width = options.outWidth;
		// 图片实际的宽与高，根据默认最大大小值，得到图片实际的缩放比例
		while ((height / sampleSize > showHeight)
				|| (width / sampleSize > showWidth)) {
			sampleSize += 1;
		}
		// 不再只加载图片实际边缘
		options.inJustDecodeBounds = false;
		// 并且制定缩放比例
		options.inSampleSize = sampleSize;
		options.inPreferredConfig = Config.ARGB_8888;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap getLocalBitmap(String path, int width, int height) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inSampleSize = 2;
		opts.inJustDecodeBounds = false;
		opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		opts.outWidth = width;
		opts.outHeight = height;
		Bitmap bm = getLocalBitmap(path, opts);
		return zoomBitmap(bm, width, height);
	}
	public static Bitmap getLocalBitmap(String path,
			BitmapFactory.Options options) {
		if (TextUtils.isEmpty(path))
			return null;
		Bitmap temBitmap = null;
		try {
			temBitmap = BitmapFactory.decodeFile(path, options);
		} catch (Throwable t) {
			t.printStackTrace();

		}
		return temBitmap;
	}
}
