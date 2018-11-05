package com.buyi.util.security;

import com.buyi.util.security.MD5Utils;
import org.testng.annotations.Test;

public class MD5UtilsTest {

	@Test
	public void md5() {
		String  str = "admin";
		System.out.println(MD5Utils.md5(str));
	}

}
