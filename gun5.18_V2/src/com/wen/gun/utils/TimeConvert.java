package com.wen.gun.utils;

import com.wen.gun.constant.Constant;

public class TimeConvert {

	/** 
	 * TODO<����ת���ɷ������ָ�ʽ>
	 * @param seconds
	 * @return
	 * @throw
	 * @return String
	 */
	public static String secondsToMinute(int seconds) {

		// if(seconds!=0){
		// String m = (seconds % 3600) / 60<10 ? "0"+(seconds % 3600) / 60 :
		// (seconds % 3600) / 60+"";
		// String s = seconds % 60<10 ? "0"+seconds % 60:seconds % 60+"";
		// return m+ ":" + s ;
		// }else{
		// return "";
		// }
		if(seconds==Constant.TIME_DURATION){
			return "00:00";
		}else{
			String m = (seconds % 3600) / 60 < 10 ? "0" + (seconds % 3600) / 60
					: (seconds % 3600) / 60 + "";
			String s = seconds % 60 < 10 ? "0" + seconds % 60 : seconds % 60 + "";
			return m + ":" + s;
		}

		

		
	}

	/** 
	 * TODO<����ת���ɷ��뺺�ָ�ʽ>
	 * @param seconds
	 * @return
	 * @throw
	 * @return String
	 */
	public static String secondsToMinute1(int seconds) {

		String m = (seconds % 3600) / 60 < 10 ? "0" + (seconds % 3600) / 60
				: (seconds % 3600) / 60 + "";
		String s = seconds % 60 < 10 ? "0" + seconds % 60 : seconds % 60 + "";
		return m + "��:" + s + "��";

	}

}
