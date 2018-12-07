package com.puffer.util.http;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Headers.Builder;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 基于OkHttp3实现的http工具类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午4:47:15
 */
public class HttpUtil {
	// private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	private static HttpUtil instance;
	private OkHttpClient httpClient;

	private static final String JSON_CONTENT_TYPE = "application/json";

	/**
	 * 默认连接超时
	 */
	private static final int DEFAULT_CONNECT_TIMEOUT = 1000 * 30;
	/**
	 * 默认读取超时
	 */
	private static final int DEFAULT_READ_TIMEOUT = 1000 * 30;

	private HttpUtil() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
				.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS).addInterceptor(getLogInterceptor());
		httpClient = builder.build();
	}

	/**
	 * 获取单例
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午5:26:00
	 * @return
	 */
	public static HttpUtil instance() {
		if (instance == null) {
			synchronized (HttpUtil.class) {
				if (instance == null) {
					instance = new HttpUtil();
				}
			}
		}

		return instance;
	}

	/**
	 * 发送post json请求
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午10:30:47
	 * @param url
	 * @param params
	 * @param charset
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 */
	public HttpResponse postJson(String url, String params, Map<String, String> header, String charset)
			throws IOException {

		MediaType mediaType = MediaType.parse(JSON_CONTENT_TYPE.concat(";").concat("charset=").concat(charset));
		RequestBody requestBody = RequestBody.create(mediaType, params);

		// 封装请求头
		Builder builder = new Headers.Builder();
		if (header != null && !header.isEmpty()) {
			for (String key : header.keySet()) {
				builder.add(key, header.get(key));
			}
		}

		Request request = new Request.Builder().url(url).headers(builder.build()).post(requestBody).build();

		Response response = httpClient.newCall(request).execute();

		return HttpResponse.newInstance(response.code(), response.body().string());
	}

	/**
	 * 发送post form请求
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:50:56
	 * @param url
	 * @param params
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public HttpResponse postForm(String url, Map<String, String> params, String charset) throws IOException {
		// 版本3.9.1写法
		// okhttp3.FormBody.Builder builder = new
		// FormBody.Builder(Charset.forName(charset));
		okhttp3.FormBody.Builder builder = new FormBody.Builder();

		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				builder.add(key, params.get(key));
			}
		}

		Request request = new Request.Builder().url(url).post(builder.build()).build();

		Response response = httpClient.newCall(request).execute();

		return HttpResponse.newInstance(response.code(), response.body().string());
	}

	/**
	 * 发送请求
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午4:47:24
	 * @param url
	 * @param params
	 * @param contentType
	 * @param charset
	 * @param connectTimeout
	 * @param readTimeout
	 * @return
	 * @throws IOException
	 */
	public static HttpResponse post(String url, String params, String contentType, String charset, int connectTimeout,
			int readTimeout) throws IOException {

		MediaType mediaType = MediaType.parse(contentType.concat(";").concat("charset=").concat(charset));
		RequestBody requestBody = RequestBody.create(mediaType, params);

		Builder builder = new Headers.Builder();

		Request request = new Request.Builder().url(url).headers(builder.build()).post(requestBody).build();

		OkHttpClient client = new OkHttpClient.Builder().connectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
				.readTimeout(readTimeout, TimeUnit.MILLISECONDS).build();

		Response response = client.newCall(request).execute();

		return HttpResponse.newInstance(response.code(), response.body().string());
	}

	/**
	 * 发送get请求
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午4:40:12
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public HttpResponse get(String url, Map<String, String> params) throws IOException {
		okhttp3.HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();

		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				builder.addQueryParameter(key, params.get(key));
			}
		}

		Request request = new Request.Builder().url(builder.build()).build();

		Response response = httpClient.newCall(request).execute();

		return HttpResponse.newInstance(response.code(), response.body().string());

	}

	/**
	 * 日志输出
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017下午5:25:45
	 * @return
	 */
	private HttpLoggingInterceptor getLogInterceptor() {
		HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;

		// 新建log拦截器
		HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
			@Override
			public void log(String message) {
				// logger.info(message);
				System.out.println(message);
			}
		});

		loggingInterceptor.setLevel(level);
		return loggingInterceptor;
	}

}
