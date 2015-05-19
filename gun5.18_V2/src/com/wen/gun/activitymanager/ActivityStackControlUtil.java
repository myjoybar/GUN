package com.wen.gun.activitymanager;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

public class ActivityStackControlUtil extends Activity {

	/* 每个activity的add中添加进集合，然后关闭就移除集合 ， */
	private static List<Activity> activityList = new ArrayList<Activity>();

	public static void remove(Activity activity) {

		activityList.remove(activity);

	}

	public static void add(Activity activity) {

		activityList.add(activity);

	}

	public static void finishProgram() {

		for (Activity activity : activityList) {

			activity.finish();

		}

		// 杀掉当前程序进程
		// android.os.Process.killProcess(android.os.Process.myPid());

	}

}
