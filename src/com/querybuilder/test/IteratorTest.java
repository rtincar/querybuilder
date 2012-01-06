package com.querybuilder.test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class IteratorTest {
	
	public static void main(String[] args) {
		List<String> lista = Arrays.asList("uno","dos","tres");
		Iterator<String> iterator = lista.iterator();
		StringBuilder sb = new StringBuilder();
		while (iterator.hasNext()) {
			String next = iterator.next();
			sb.append(next);
			if (iterator.hasNext()) {
				sb.append(", ");
			}
		}
		System.out.println(sb.toString());
	}

}
