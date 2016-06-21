package org.daelly.oj.common;

import static org.junit.Assert.*;

import org.junit.Test;

public class CommonTest {

	@Test
	public void test() {
		String str = "123$456$789";
		String[] arr = str.split("$");
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i]);
		}
	}

}
