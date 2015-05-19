package com.wen.gun.activity;

import com.wen.gun.R;
import com.wen.gun.circleprogress.DonutProgress;
import com.wen.gun.constant.Constant;
import com.wen.gun.ripplelibrary.RippleBackground;
import com.wen.gun.service.MonitorService;
import com.wen.gun.uiBUtton.ICompete;
import com.wen.gun.uiBUtton.UIRippleButton;
import com.wen.gun.utils.L;
import com.wen.gun.utils.SPUtils;
import com.wen.gun.utils.StringNotsUtils;
import com.wen.gun.utils.StringRandomUtils;
import com.wen.gun.utils.T;
import com.wen.gun.utils.TimeConvert;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends BaseActivity {
	private static String TAG = MainActivity.class.getName();
	private TextView tv_notes;
	
	UIRippleButton r_btn;
	private RelativeLayout rippleBackground;
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
	//	tv_notes.setText(StringNotsUtils.setText(0));
		setMonitorDurationTime();
		L.i(TAG, "���������ɵ�ʱ��Ϊ=" + Constant.TIME_DURATION);
		animationTime = 1000;
		mc = new MyCount(Constant.TIME_DURATION * 1000 , 1000); // ����1��
		donutProgress.setMax(Constant.TIME_DURATION);
		isStart = false;

	}

	@Override
	protected void initView() {
		r_btn =  (UIRippleButton) this.findViewById(R.id.r_btn);
		tv_notes = (TextView) this.findViewById(R.id.tv_notes);
		rippleBackground = (RelativeLayout) this.findViewById(R.id.content);
		donutProgress = (DonutProgress) this.findViewById(R.id.donut_progress);
	}

	@Override
	protected void setLinstener() {
	//	r_btn.setOnClickListener(this);
		donutProgress.setOnClickListener(this);
		r_btn.setCompeteListener(new ICompete() {
			
			@Override
			public void onCompete() {
				 if (!isStart) {
  					isStart = true;
  					
  					stratService();
  					
  					donutProgress.setVisibility(View.VISIBLE);
  					donutProgress.setClickable(true);	
  					setShowAnimation(donutProgress, animationTime);
  					Countdown();					
  					donutProgress.setProgress(0);
  					 						
  			 }
				
			}
		});
		
		
	}
	@Override
	protected void fillData() {
		// TODO Auto-generated method stub

	}
	
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.donut_progress:
			 tv_notes.setVisibility(View.VISIBLE);
		     tv_notes.setText("�ٵȵ� �� �ػ�����");			
		    setHideAnimation(tv_notes,4000);
			break;
			
		default:
			break;
		}

	}

	
private void setRippleEffect1() {

				

		

	}


	/**
	 * TODO<��������ʱ>
	 * 
	 * @throw
	 * @return void
	 */
	private void Countdown() {
		//tv_notes.setText("�ٵȵ� �� �ػ�����");
	
	//	setHideAnimation(tv_notes,animationTime);
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
	//	tv_notes.setVisibility(View.INVISIBLE);
		isStart = false;
	
		r_btn.setVisibility(View.VISIBLE);
		
		donutProgress.setProgress(0);
		donutProgress.setVisibility(View.INVISIBLE);
		
		L.i(TAG,"ddddd"+tv_notes.VISIBLE+"");
		tv_notes.clearAnimation();
		tv_notes.setVisibility(View.INVISIBLE);
		L.i(TAG,"ddddd"+tv_notes.VISIBLE+"");
		
		donutProgress.setClickable(false);
		setHideAnimation(donutProgress, animationTime);
	//	setShowAnimation(tv_start, animationTime);
		stopService();
		int times = (Integer) SPUtils.get(MainActivity.this, "use_times", 0);
	//	tv_notes.setText(StringNotsUtils.setText(times));
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

		mHideAnimation = new AlphaAnimation(1.0f, 0.01f);

		mHideAnimation.setDuration(duration);

		mHideAnimation.setFillAfter(true);
		mHideAnimation.setAnimationListener(new MyTextNotesHideListener());

		view.startAnimation(mHideAnimation);
		view.setVisibility(View.INVISIBLE);

	}

	private void setShowAnimation(View view, int duration) {

		if (null == view || duration < 0) {

			return;

		}

		if (null != mShowAnimation) {

			mShowAnimation.cancel();

		}

		mShowAnimation = new AlphaAnimation(0.01f, 1.0f);

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
				L.i(TAG, donutProgress.getProgress()+"");
				L.i(TAG, (Constant.TIME_DURATION
						- donutProgress.getProgress())+"");

			}else{
				L.i(TAG, "==");
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
			//	Constant.TIME_DURATION = 1800; // 30min
			 Constant.TIME_DURATION = 1800;

			break;
		case 2:
			//Constant.TIME_DURATION = 2013; // 33��33��
			 Constant.TIME_DURATION = 1800;

			break;
		case 3:
			// Constant.TIME_DURATION = 2100; // 35min
			Constant.TIME_DURATION = 1800;

			break;

		default:
			break;
		}
	}
	
	
		

	public class MyTextNotesHideListener implements AnimationListener{
		


	
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onAnimationEnd(Animation animation) {
		tv_notes.setVisibility(View.INVISIBLE);
		
	}

	
	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}
	}
}
