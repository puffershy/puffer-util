package com.puffer.util.lang;


import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class TextTest {

	@Test
	public void test() {
		int size = 1000;
		List<String> list = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			list.add("TSXTSDTsdfsdfS_123134_" + i + ".txt");
		}

		System.out.println(list);
	}

}
