package com.sciits.devops.poc.util;

public class UMUtils {

	public static boolean isEmpty(String s) {
		return s == null || "".equals(s.trim());
	}

	public static boolean isEmpty(Double d) {
		//return d == null || d == 0D;
		return d == null;
	}

	public static boolean isEmpty(Long l) {
		/*return l == null || l == 0L;*/
		return l == null;
	}

}
