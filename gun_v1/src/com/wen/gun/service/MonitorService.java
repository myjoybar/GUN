package com.wen.gun.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.wen.gun.activity.MonitorActivity;
import com.wen.gun.utils.L;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class MonitorService extends Service {

	boolean flag = true;// 用于停止线程
	private ActivityManager activityManager;
	private Timer timer;
	private TimerTask task = new TimerTask() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (activityManager == null) {
				activityManager = (ActivityManager) MonitorService.this
						.getSystemService(ACTIVITY_SERVICE);
			}

			List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(
					2, ActivityManager.RECENT_WITH_EXCLUDED);
			RecentTaskInfo recentInfo = recentTasks.get(0);
			Intent intent = recentInfo.baseIntent;
			String recentTaskName = intent.getComponent().getPackageName();
	
//			
//			||!recentTaskName.equals("com.android.contacts")
//			||!recentTaskName.equals("com.android.phone")
//			||!recentTaskName.equals("com.android.launcher")
//			||!recentTaskName.equals("com.miui.home")
			
				if (!recentTaskName.equals("com.wen.gun")
						) {
					L.i("MonitorService", "Yes--recentTaskName=" + recentTaskName);
					Intent intentNewActivity = new Intent(MonitorService.this,
							MonitorActivity.class);
					intentNewActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intentNewActivity);

				}else{
					L.i("MonitorService", "No--recentTaskName="+recentTaskName);

				

				}
			}
	

	};

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		if (flag == true) {
			timer = new Timer();
			timer.schedule(task, 0, 100);		
			flag = false;
		}

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		timer.cancel();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
