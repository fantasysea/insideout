package com.campus.insideout.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

	public static Boolean checkemil(String emil) {
		String str = "[a-zA-Z0-9_]{1,20}@{1}[a-zA-Z0-9]{2,10}(\\.{1}[a-zA-Z]{2,5}){1,5}";
		if (!emil.matches(str)) {
			return false;
		}
		return true;
	}
	public static Boolean checkNum(String emil) {
		String str = "[0-9_]{1,20}";
		if (!emil.matches(str)) {
			return false;
		}
		return true;
	}

	public static Boolean checkusername(String username) {
//		int leng = username.length();
		int leng = length(username);
		if (leng > 16 || leng < 4) {
			return false;
		}
		return true;
	}

	public static int length(String value) {
		int valueLength = 0;
		String chinese = "[\u0391-\uFFE5]";
		/* 获取字段值的长度，如果含中文字符，则每个中文字符长度�?，否则为1 */
		for (int i = 0; i < value.length(); i++) {
			/* 获取�?��字符 */
			String temp = value.substring(i, i + 1);
			/* 判断是否为中文字�?*/
			if (temp.matches(chinese)) {
				/* 中文字符长度�? */
				valueLength += 2;
			} else {
				/* 其他字符长度�? */
				valueLength += 1;
			}
		}
		return valueLength;
	}

	public static Boolean checkpass(String pass) {
		int pa = pass.length();
		if (pa > 16 || pa < 6) {
			return false;
		}
		return true;
	}

	public static Boolean checktel(String tel) {
		// 移动�?34�?35�?36�?37�?38�?39�?50�?51�?57(TD)�?58�?59�?87�?88�?82
		// 联�?�?30�?31�?32�?52�?55�?56�?85�?86
		// 电信�?33�?53�?80�?89、（1349卫�?�?

		String s1 = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(s1);
		Matcher m = p.matcher(tel);

		return m.find();// boolean

	}
}
