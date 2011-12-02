package com.querybuilder.test;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern p = Pattern.compile("(\\w+(\\.\\w+)*)\\s+as\\s+\\w+", Pattern.CASE_INSENSITIVE);
		Pattern p2 = Pattern.compile("(\\w+(\\.\\w+)*)", Pattern.CASE_INSENSITIVE);
		Map<String, String> selectionMap = new LinkedHashMap<String, String>();
		String s = "c.id,c.data,g as v";
		String[] split = s.split(",");
		int i = 0;
		for (String r : split) {
			String sanitized = sanitize(r);
			if (p.matcher(sanitized).find()) {
				int lastIndexOf = sanitized.lastIndexOf(" ");
				String alias = sanitized.substring(lastIndexOf + 1);
				int lastIndexOf2 = sanitized.lastIndexOf(" as");
				String property = sanitized.substring(0, lastIndexOf2);
				selectionMap.put(property, alias);
			} else if (p2.matcher(sanitized).find()) {
				String alias = "_a" + i;
				selectionMap.put(sanitized, alias);
				i++;
			} else {
				throw new IllegalArgumentException("Expresion de seleccion no valida: " + sanitized);
			}
		}
		
		System.out.println(selectionMap);
	}
	
	private static String sanitize(String s) {
		return s.trim().toLowerCase().replaceAll("\\s+", " ");
	}

}
