package com.wen.gun.utils;

public class StringNotsUtils {

	/** 
	 * TODO<������ʾ����>
	 * @param times
	 * @return
	 * @throw
	 * @return String
	 */
	public static String setText(int times) {

		if (times == 0) {
			return "";
		} else if (times > 20) {
			return "����ж����";
		} else if (times % 5 == 0) {
			String zheng = "";
			for (int i = 0; i < times / 5; i++) {
				zheng = "��" + zheng;
			}

			return "������ʹ��" + zheng + "��";
		} else {

			return "������ʹ��" + times + "��";
		}
	}
}
