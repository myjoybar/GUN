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
		L.i(TAG, "���������ɵ�ʱ��Ϊ=" + Constant.TIME_DURATION);
		animationTime = 1000;
		mc = new MyCount(Constant.TIME_DURATION * 1000 + 1000, 1000); // ����1��
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
				L.i(TAG, "�Ѿ���ʼ�˶�");
			}

			break;

		case R.id.donut_progress:
			T.showLong(MainActivity.this, "�㽫��  " + mDTime + " ��ָ��ֻ�ʹ��Ȩ");
			break;
		default:
			break;
		}

	}

	/**
	 * TODO<�����ʼ��ʱ���������Ч����3s��ֹͣ����Ч��������������ʱ>
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
	 * TODO<��������ʱ>
	 * 
	 * @throw
	 * @return void
	 */
	private void Countdown() {
		tv_notes.setText("�ٵȵ� �� �ػ�����");
		mc.start();// ��������ʱ
	}

	/**
	 * TODO<����һ�����ƺ�׼����һ�ε���������׼��>
	 * 
	 * @throw
	 * @return void
	 */
	public void prepareNewTask() {
		L.i(TAG, "��ν�����");
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
	 * TODO<�������ƴ���>
	 * 
	 * @throw
	 * @return void
	 */
	public void saveUseTimes() {

		int times = (Integer) SPUtils.get(MainActivity.this, "use_times", 0);
		SPUtils.put(MainActivity.this, "use_times", times + 1);
	}

	/**
	 * TODO<���ÿ�ʼ�����뵹��ʱ�����л�ʱ�Ķ���Ч��>
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
	 * TODO<��ʼ��������>
	 * 
	 * @throw
	 * @return void
	 */
	private void stratService() {
		Intent intent = new Intent(MainActivity.this, MonitorService.class);
		startService(intent);

	}

	/**
	 * TODO<ֹͣ����>
	 * 
	 * @throw
	 * @return void
	 */
	private void stopService() {
		Intent intent = new Intent(MainActivity.this, MonitorService.class);
		stopService(intent);
	}

	/* ����һ������ʱ���ڲ��� */
	class MyCount extends CountDownTimer {
		public MyCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		// ���һ�β����ٵ���
		@Override
		public void onTick(long millisUntilFinished) {
			L.i(TAG, "���ڵ���ʱ");
			if (donutProgress.getProgress() != Constant.TIME_DURATION) {
				donutProgress.setProgress(donutProgress.getProgress() + 1);
				mDTime = TimeConvert.secondsToMinute1(Constant.TIME_DURATION
						- donutProgress.getProgress());

			}

		}

		/**
		 * ���ط������������Ҫ����������ر�APP
		 */
		@Override
		public void onFinish() {
			saveUseTimes();
			prepareNewTask();

			L.i(TAG, "����ʱ����");
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

		// mc.cancel(); ��������
		if (mShowAnimation != null) {
			mShowAnimation.cancel();
		}
		if (mHideAnimation != null) {
			mHideAnimation.cancel();
		}

	}

	// ���ѡ��30���ӣ�33��33�룬35��������������ʱ��
	public void setMonitorDurationTime() {
		switch (StringRandomUtils.getRandomNumber(1, 3)) {
		case 1:
			Constant.TIME_DURATION = 1800; // 30min
			// Constant.TIME_DURATION = 60;

			break;
		case 2:
			Constant.TIME_DURATION = 2013; // 33��33��
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
