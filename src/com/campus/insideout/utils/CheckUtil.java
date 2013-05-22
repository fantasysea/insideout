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
		/* èŽ·å–å­—æ®µå€¼çš„é•¿åº¦ï¼Œå¦‚æžœå«ä¸­æ–‡å­—ç¬¦ï¼Œåˆ™æ¯ä¸ªä¸­æ–‡å­—ç¬¦é•¿åº¦ä¸?ï¼Œå¦åˆ™ä¸º1 */
		for (int i = 0; i < value.length(); i++) {
			/* èŽ·å–ä¸?¸ªå­—ç¬¦ */
			String temp = value.substring(i, i + 1);
			/* åˆ¤æ–­æ˜¯å¦ä¸ºä¸­æ–‡å­—ç¬?*/
			if (temp.matches(chinese)) {
				/* ä¸­æ–‡å­—ç¬¦é•¿åº¦ä¸? */
				valueLength += 2;
			} else {
				/* å…¶ä»–å­—ç¬¦é•¿åº¦ä¸? */
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
		// ç§»åŠ¨ï¼?34ã€?35ã€?36ã€?37ã€?38ã€?39ã€?50ã€?51ã€?57(TD)ã€?58ã€?59ã€?87ã€?88ï¼?82
		// è”é?ï¼?30ã€?31ã€?32ã€?52ã€?55ã€?56ã€?85ã€?86
		// ç”µä¿¡ï¼?33ã€?53ã€?80ã€?89ã€ï¼ˆ1349å«é?ï¼?

		String s1 = "^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$";
		Pattern p = Pattern.compile(s1);
		Matcher m = p.matcher(tel);

		return m.find();// boolean

	}
}
