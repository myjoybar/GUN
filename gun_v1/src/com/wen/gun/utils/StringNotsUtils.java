package com.wen.gun.utils;

public class StringNotsUtils {

	/** 
	 * TODO<设置提示文字>
	 * @param times
	 * @return
	 * @throw
	 * @return String
	 */
	public static String setText(int times) {

		if (times == 0) {
			return "";
		} else if (times > 20) {
			return "可以卸载了";
		} else if (times % 5 == 0) {
			String zheng = "";
			for (int i = 0; i < times / 5; i++) {
				zheng = "正" + zheng;
			}

			return "已连续使用" + zheng + "次";
		} else {

			return "已连续使用" + times + "次";
		}
	}
}
