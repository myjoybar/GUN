package com.wen.gun.utils;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * Logͳһ������
 * 
 * @author wen
 * @version 2015��1��16��
 * @see L
 * @since
 */
@SuppressLint("SimpleDateFormat")
public class L {

	public final static int VERBOSE = 5; // ������������е�����Ϣ ����
											// VERBOSE��DEBUG��INFO��WARN��ERROR

	public final static int DEBUG = 4; // debug�����������DEBUG��INFO��WARN��ERROR������Ϣ

	public final static int INFO = 3; // info�����������INFO��WARN��ERROR������Ϣ

	public final static int WARN = 2; // waring�����������WARN��ERROR������Ϣ

	public final static int ERROR = 1; // error��������ֻ���ERROR������Ϣ

	public static int level = 6; // 0 ��6

	private static final String TAG = "wen";
	// private static final String TAG = _CLASS_FUNC();
	public static final String SEPARATOR = ",";

	// ������Ĭ��tag�ĺ���
	public static void v(String msg) {
		if (VERBOSE >= level)
			Log.v(TAG, msg);
	}

	public static void d(String msg) {
		if (DEBUG >= level)
			Log.d(TAG, msg);
	}

	public static void i(String msg) {
		if (INFO >= level)
			Log.i(TAG, msg);
	}

	public static void w(String msg) {
		if (WARN >= level)
			Log.w(TAG, msg);
	}

	public static void e(String msg) {
		if (ERROR >= level)
			Log.e(TAG, msg);
	}

	// �����Ǵ����Զ���tag�ĺ���
	public static void v(String tag, String msg) {
		if (VERBOSE >= level)
			Log.v(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (DEBUG >= level)
			Log.d(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (INFO >= level)
			Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (WARN >= level)
			Log.w(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (ERROR >= level)
			Log.e(tag, msg);
	}

}
