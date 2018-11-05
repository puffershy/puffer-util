package com.buyi.util.http;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * http请求响应实体
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2017下午4:22:41
 */
public class HttpResponse {
	// private static final int RESPONSE_CODE_SUCCESS = 200;

	private String url;
	private String host;
	private String path;
	private int port;
	private String protocol;
	private String query;
	private String ref;
	private String userInfo;
	private int code;
	private String content;
	private String contentType;
	private String message;
	private String method;
	private int connectionTimeout;
	private int readTimeout;

	private HttpResponse() {
		super();
	}

	private HttpResponse(int code, String content) {
		super();
		this.code = code;
		this.content = content;
	}

	public static HttpResponse newInstance() {
		return new HttpResponse();
	}

	public static HttpResponse newInstance(int code, String content) {
		return new HttpResponse(code, content);
	}

	/**
	 * 如果响应码在[200,300)区间，表示请求已经处理，或者已接收
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2017上午9:03:14
	 * @return
	 */
	public boolean isSuccess() {
		return code >= 200 && code < 300;
	}

	public String getUrl() {
		return url;
	}

	public HttpResponse setUrl(String url) {
		this.url = url;
		return this;
	}

	public String getHost() {
		return host;
	}

	public HttpResponse setHost(String host) {
		this.host = host;
		return this;
	}

	public String getPath() {
		return path;
	}

	public HttpResponse setPath(String path) {
		this.path = path;
		return this;
	}

	public int getPort() {
		return port;
	}

	public HttpResponse setPort(int port) {
		this.port = port;
		return this;
	}

	public String getProtocol() {
		return protocol;
	}

	public HttpResponse setProtocol(String protocol) {
		this.protocol = protocol;
		return this;
	}

	public String getQuery() {
		return query;
	}

	public HttpResponse setQuery(String query) {
		this.query = query;
		return this;
	}

	public String getRef() {
		return ref;
	}

	public HttpResponse setRef(String ref) {
		this.ref = ref;
		return this;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public HttpResponse setUserInfo(String userInfo) {
		this.userInfo = userInfo;
		return this;
	}

	public int getCode() {
		return code;
	}

	public HttpResponse setCode(int code) {
		this.code = code;
		return this;
	}

	public String getContent() {
		return content;
	}

	public HttpResponse setContent(String content) {
		this.content = content;
		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public HttpResponse setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public HttpResponse setMessage(String message) {
		this.message = message;
		return this;
	}

	public String getMethod() {
		return method;
	}

	public HttpResponse setMethod(String method) {
		this.method = method;
		return this;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public HttpResponse setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
		return this;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public HttpResponse setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
