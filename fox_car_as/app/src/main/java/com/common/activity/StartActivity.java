/**
 *
 * PackageName:net.shopnc.android
 * FileNmae:StartActivity.java
 */
package com.common.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.blueberry.activity.R;




import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class StartActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_view);
		
		//start new activity
	
		
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
		
				
				//start new activity
				Intent it=new Intent();
				it.setClass(StartActivity.this,Login.class);
				startActivity(it);
				StartActivity.this.finish();
			}
		}, 1000);
	  		
						
				
  		
		
	
	}

}
