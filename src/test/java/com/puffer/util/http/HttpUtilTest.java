package com.puffer.util.http;

import com.google.common.collect.Maps;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

/**
 * {@link HttpUtil}单元测试
 * @author buyi
 * @since 1.0.0
 * @date 2017下午5:05:03
 */
public class HttpUtilTest {

	@Test
	public void postForm() throws IOException {
		try {
			String url = "http://localhost/npay/api/fuiou/withdraw/modify_cash";
			Map<String, String> params = Maps.newHashMap();
			String charset = "utf-8";

			HttpResponse response = HttpUtil.instance().postForm(url, params, charset);
			System.out.println(response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void get() throws IOException {
		try {
			String url = "http://localhost/npay/api/fuiou/withdraw/modify_cash";
			Map<String, String> params = Maps.newHashMap();
			String charset = "utf-8";

			HttpResponse response = HttpUtil.instance().get(url, params);
			System.out.println(response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
