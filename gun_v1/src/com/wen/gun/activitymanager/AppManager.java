package com.wen.gun.activitymanager;

import java.util.Stack;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;


public class AppManager {
	private static Stack<Activity> mActivityStack;
	private static AppManager mAppManager;

	static int sysVersion;// 系统版本号

	private AppManager() {
	}

	/**
	 * 单一实例
	 */
	public static AppManager getInstance() {
		if (mAppManager == null) {
			mAppManager = new AppManager();
		}
		return mAppManager;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (mActivityStack == null) {
			mActivityStack = new Stack<Activity>();
		}
		mActivityStack.add(activity);
	}

	/**
	 * 获取栈顶Activity（堆栈中最后一个压入的）
	 */
	public Activity getTopActivity() {
		Activity activity = mActivityStack.lastElement();
		return activity;
	}

	/**
	 * 结束栈顶Activity（堆栈中最后一个压入的）
	 */
	public void killTopActivity() {
		Activity activity = mActivityStack.lastElement();
		killActivity(activity);
	}

	/**
	 * 结束指定的Activity
	 */
	public void killActivity(Activity activity) {
		if (activity != null) {
			mActivityStack.remove(activity);

			activity.finish();

			activity = null;
		}
	}

	/**
	 * 结束指定类名的Activity
	 */
	public void killActivity(Class<?> cls) {
		for (Activity activity : mActivityStack) {
			if (activity.getClass().equals(cls)) {
				killActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void killAllActivity() {
		for (int i = 0, size = mActivityStack.size(); i < size; i++) {
			if (null != mActivityStack.get(i)) {
				mActivityStack.get(i).finish();
			}
		}
		mActivityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void AppExit(Context context) {
		try {
			killAllActivity();

			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);

			// 退出后台线程？
			sysVersion = android.os.Build.VERSION.SDK_INT;

			if (sysVersion < android.os.Build.VERSION_CODES.GINGERBREAD) {// 2.3
																			// 以下的版本
				activityMgr.restartPackage(context.getPackageName());
			} else {
				// 2.2以上是过时的,请用killBackgroundProcesses代替
				activityMgr.killBackgroundProcesses(context.getPackageName());
			}

			System.exit(0);// 只有 菜单退出 才真正退出

		} catch (Exception e) {
		}
	}
}
