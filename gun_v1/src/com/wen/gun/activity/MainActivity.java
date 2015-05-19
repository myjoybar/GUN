package com.wen.gun.activity;

import com.wen.gun.R;
import com.wen.gun.circleprogress.DonutProgress;
import com.wen.gun.constant.Constant;
import com.wen.gun.ripplelibrary.RippleBackground;
import com.wen.gun.service.MonitorService;
import com.wen.gun.utils.L;
import com.wen.gun.utils.SPUtils;
import com.wen.gun.utils.StringNotsUtils;
import com.wen.gun.utils.StringRandomUtils;
import com.wen.gun.utils.T;
import com.wen.gun.utils.TimeConvert;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
	private static String TAG = MainActivity.class.getName();
	private TextView tv_start, tv_notes;
	private RippleBackground rippleBackground;
	private DonutProgress donutProgress;
	private boolean isStart;
	private MyCount mc;
	private AlphaAnimation mHideAnimation = null;
	private AlphaAnimation mShowAnimation = null;
	private int animationTime;
	private String mDTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		setLinstener();
		fillData();
	}

	@Override
	protected void initData() {
		// int times = (Integer) SPUtils.get(MainActivity.this, "use_times", 0);
		tv_notes.setText(StringNotsUtils.setText(0));
		setMonitorDurationTime();
		L.i(TAG, "这次随机生成的时间为=" + Constant.TIME_DURATION);
		animationTime = 1000;
		mc = new MyCount(Constant.TIME_DURATION * 1000 + 1000, 1000); // 加上1秒
		donutProgress.setMax(Constant.TIME_DURATION);
		isStart = false;

	}

	@Override
	protected void initView() {
		tv_start = (TextView) this.findViewById(R.id.tv_start);
		tv_notes = (TextView) this.findViewById(R.id.tv_notes);
		rippleBackground = (RippleBackground) findViewById(R.id.content);
		donutProgress = (DonutProgress) this.findViewById(R.id.donut_progress);
	}

	@Override
	protected void setLinstener() {
		tv_start.setOnClickListener(this);
		donutProgress.setOnClickListener(this);

	}

	@Override
	protected void fillData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_start:
			if (!isStart) {
				isStart = true;
				donutProgress.setProgress(0);
				setRippleEffect();
				stratService();
			} else {
				L.i(TAG, "已经开始了额");
			}

			break;

		case R.id.donut_progress:
			T.showLong(MainActivity.this, "你将在  " + mDTime + " 后恢复手机使用权");
			break;
		default:
			break;
		}

	}

	/**
	 * TODO<点击开始的时候出现涟漪效果，3s后停止涟漪效果，并开启倒计时>
	 * 
	 * @throw
	 * @return void
	 */
	private void setRippleEffect() {

		rippleBackground.startRippleAnimation();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				rippleBackground.stopRippleAnimation();
				tv_start.setVisibility(View.INVISIBLE);
				donutProgress.setVisibility(View.VISIBLE);
				donutProgress.setClickable(true);
				setHideAnimation(tv_start, animationTime);
				setShowAnimation(donutProgress, animationTime);
				Countdown();

			}
		}, 3000);

	}

	/**
	 * TODO<开启倒计时>
	 * 
	 * @throw
	 * @return void
	 */
	private void Countdown() {
		tv_notes.setText("再等等 或 关机重启");
		mc.start();// 开启倒计时
	}

	/**
	 * TODO<结束一次治疗后准备下一次的治疗数据准备>
	 * 
	 * @throw
	 * @return void
	 */
	public void prepareNewTask() {
		L.i(TAG, "这次结束了");
		isStart = false;
		tv_start.setVisibility(View.VISIBLE);
		donutProgress.setProgress(0);
		donutProgress.setVisibility(View.INVISIBLE);
		donutProgress.setClickable(false);
		setHideAnimation(donutProgress, animationTime);
		setShowAnimation(tv_start, animationTime);
		stopService();
		int times = (Integer) SPUtils.get(MainActivity.this, "use_times", 0);
		tv_notes.setText(StringNotsUtils.setText(times));
	}

	/**
	 * TODO<保存治疗次数>
	 * 
	 * @throw
	 * @return void
	 */
	public void saveUseTimes() {

		int times = (Integer) SPUtils.get(MainActivity.this, "use_times", 0);
		SPUtils.put(MainActivity.this, "use_times", times + 1);
	}

	/**
	 * TODO<设置开始界面与倒计时界面切换时的动画效果>
	 * 
	 * @param view
	 * @param duration
	 * @throw
	 * @return void
	 */
	private void setHideAnimation(View view, int duration) {

		if (null == view || duration < 0) {

			return;

		}

		if (null != mHideAnimation) {

			mHideAnimation.cancel();

		}

		mHideAnimation = new AlphaAnimation(1.0f, 0.0f);

		mHideAnimation.setDuration(duration);

		mHideAnimation.setFillAfter(true);

		view.startAnimation(mHideAnimation);

	}

	private void setShowAnimation(View view, int duration) {

		if (null == view || duration < 0) {

			return;

		}

		if (null != mShowAnimation) {

			mShowAnimation.cancel();

		}

		mShowAnimation = new AlphaAnimation(0.0f, 1.0f);

		mShowAnimation.setDuration(duration);

		mShowAnimation.setFillAfter(true);

		view.startAnimation(mShowAnimation);

	}

	/**
	 * TODO<开始监听服务>
	 * 
	 * @throw
	 * @return void
	 */
	private void stratService() {
		Intent intent = new Intent(MainActivity.this, MonitorService.class);
		startService(intent);

	}

	/**
	 * TODO<停止服务>
	 * 
	 * @throw
	 * @return void
	 */
	private void stopService() {
		Intent intent = new Intent(MainActivity.this, MonitorService.class);
		stopService(intent);
	}

	/* 定义一个倒计时的内部类 */
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		// 最后一次不会再调用
		@Override
		public void onTick(long millisUntilFinished) {
			L.i(TAG, "我在倒计时");
			if (donutProgress.getProgress() != Constant.TIME_DURATION) {
				donutProgress.setProgress(donutProgress.getProgress() + 1);
				mDTime = TimeConvert.secondsToMinute1(Constant.TIME_DURATION
						- donutProgress.getProgress());

			}

		}

		/**
		 * 重载方法，如果有需要可以在这里关闭APP
		 */
		@Override
		public void onFinish() {
			saveUseTimes();
			prepareNewTask();

			L.i(TAG, "倒计时结束");
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					// AppManager.getInstance().AppExit(getApplicationContext());
				}
			}, 3000);

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// mc.cancel(); 保持运行
		if (mShowAnimation != null) {
			mShowAnimation.cancel();
		}
		if (mHideAnimation != null) {
			mHideAnimation.cancel();
		}

	}

	// 随机选择30分钟，33分33秒，35分钟这三种锁定时间
	public void setMonitorDurationTime() {
		switch (StringRandomUtils.getRandomNumber(1, 3)) {
		case 1:
			Constant.TIME_DURATION = 1800; // 30min
			// Constant.TIME_DURATION = 60;

			break;
		case 2:
			Constant.TIME_DURATION = 2013; // 33分33秒
			// Constant.TIME_DURATION = 60;

			break;
		case 3:
			Constant.TIME_DURATION = 2100; // 35min
			// Constant.TIME_DURATION = 60;

			break;

		default:
			break;
		}
	}
}
