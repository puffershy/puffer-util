package com.puffer.util.http;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.Map;

public class HttpUtil2 {

    /**
     * http连接路径请求路径前缀
     */
    private static final String HTTPS_PREFIX = "https";

    /**
     * post请求方式
     */
    private static final String METHOD_POST = "POST";

    /**
     * 默认字符集
     */
    // private static final String DEFAULT_CHARSET =
    // Charset.defaultCharset().name();
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * json内容类型
     */
    private static final String CONTENT_TYPE_JSON = "application/json";
    /**
     * form表单内容类型
     */
    private static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    /**
     * 默认连接超时
     */
    private static final int DEFAULT_CONNECT_TIMEOUT = 1000 * 30;
    /**
     * 默认读取超时
     */
    private static final int DEFAULT_READ_TIMEOUT = 1000 * 30;

    /**
     * https默认是否配置标记，false-未配置，true-已配置
     */
    private static boolean isHttpsDefaultConfig = false;

    /**
     * 发送表单post请求
     *
     * @param url
     * @param params
     * @param headers
     * @param contentType
     * @param charset
     * @param connectionTimeout
     * @param readTimeout
     * @return
     * @author buyi
     * @date 2017下午2:39:30
     * @since 1.0.0
     */
    public static HttpResponse postForm(String url, Map<String, String> params, Map<String, String> headers,
            String contentType, String charset, Integer connectionTimeout, Integer readTimeout) {
        charset = getCharset(charset);

        String paramsStr = joinParams(params, charset);

        contentType = StringUtils.isBlank(contentType) ? joinContentType(CONTENT_TYPE_FORM, charset) : contentType;

        return post(url, paramsStr, headers, contentType, charset, connectionTimeout, readTimeout);
    }

    /**
     * 发送json post请求
     *
     * @param url
     * @param params
     * @param headers
     * @param contentType
     * @param charset
     * @param connectionTimeout
     * @param readTimeout
     * @return
     * @author buyi
     * @date 2017下午2:40:58
     * @since 1.0.0
     */
    public static HttpResponse postJson(String url, String params, Map<String, String> headers, String contentType,
            String charset, Integer connectionTimeout, Integer readTimeout) {
        charset = getCharset(charset);

        contentType = StringUtils.isBlank(contentType) ? joinContentType(CONTENT_TYPE_JSON, charset) : contentType;

        return post(url, params, headers, contentType, charset, connectionTimeout, readTimeout);
    }

    /**
     * 发送post请求
     *
     * @param url
     * @param params
     * @param headers
     * @param contentType
     * @param charset
     * @param connectionTimeout
     * @param readTimeout
     * @return
     * @author buyi
     * @date 2017下午2:39:51
     * @since 1.0.0
     */
    private static HttpResponse post(String url, String params, Map<String, String> headers, String contentType,
            String charset, Integer connectionTimeout, Integer readTimeout) {
        HttpResponse httpResponse = HttpResponse.newInstance();
        HttpURLConnection connection = null;

        try {
            connection = initConnection(url, METHOD_POST, headers, contentType,
                    connectionTimeout == null ? DEFAULT_CONNECT_TIMEOUT : connectionTimeout,
                    readTimeout == null ? DEFAULT_READ_TIMEOUT : readTimeout);

            connection.connect();

            if (StringUtils.isNotBlank(params)) {
                try (OutputStream outputStream = connection.getOutputStream()) {
                    outputStream.write(params.getBytes());
                    outputStream.flush();
                }
            }

            httpResponse = buildResponse(url, connection);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return httpResponse;
    }

    /**
     * 构建响应参数
     *
     * @param urlStr
     * @param connection
     * @return
     * @throws IOException
     * @author buyi
     * @date 2017下午4:38:10
     * @since 1.0.0
     */
    private static HttpResponse buildResponse(String urlStr, HttpURLConnection connection) throws IOException {
        URL url = connection.getURL();
        String content = null;
        try (InputStream inputStream = connection.getInputStream()) {
            content = IOUtils.toString(inputStream, DEFAULT_CHARSET);
        }

        HttpResponse response = HttpResponse.newInstance().setUrl(urlStr).setHost(url.getHost()).setPath(url.getPath())
                .setPort(url.getPort()).setProtocol(url.getProtocol()).setQuery(url.getQuery()).setRef(url.getRef())
                .setUserInfo(url.getUserInfo()).setCode(connection.getResponseCode()).setContent(content)
                .setContentType(connection.getContentType()).setMessage(connection.getResponseMessage())
                .setMethod(connection.getRequestMethod()).setConnectionTimeout(connection.getConnectTimeout())
                .setReadTimeout(connection.getReadTimeout());

        return response;
    }

    /**
     * 初始化连接
     *
     * @param url
     * @param method
     * @param headers
     * @param contentType
     * @param connectTimeout
     * @param readTimeout
     * @return
     * @throws IOException
     * @author buyi
     * @date 2017下午2:40:02
     * @since 1.0.0
     */
    private static HttpURLConnection initConnection(String url, String method, Map<String, String> headers,
            String contentType, int connectTimeout, int readTimeout) throws IOException {
        URL _url = new URL(url);

        HttpURLConnection connection = null;

        if (isHttps(url)) {
            initHttpsDefaultConfig();
            connection = (HttpsURLConnection) _url.openConnection();
        } else {
            connection = (HttpURLConnection) _url.openConnection();
        }

        connection.setRequestProperty("Content-Type", contentType);
        connection.setConnectTimeout(connectTimeout);
        connection.setReadTimeout(readTimeout);
        connection.setRequestMethod(method);
        connection.setUseCaches(false);
        // 支持connection.getOutputStream().write()
        connection.setDoOutput(true);
        // 支持connection.getInputStream().read()
        connection.setDoInput(true);

        // 设置请求头属性
        if (headers != null && !headers.isEmpty()) {
            for (String key : headers.keySet()) {
                connection.setRequestProperty(key, headers.get(key));
            }
        }

        return connection;
    }

    /**
     * 初始化https默认配置
     *
     * @author buyi
     * @date 2017下午2:00:30
     * @since 1.0.0
     */
    private static void initHttpsDefaultConfig() {
        if (isHttpsDefaultConfig) {
            return;
        }

        synchronized (trustAllCerts) {
            if (!isHttpsDefaultConfig) {
                setHttpsVerifyDefaultConfig();
                isHttpsDefaultConfig = true;
            }
        }
    }

    /**
     * 构建信任全部管理器
     */
    private static final TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[] {};
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {

        }
    } };

    /**
     * 构建主机名校验
     */
    private static final HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            if ("localhost".equals(hostname)) {
                return true;
            } else {
                return false;
            }
        }
    };

    /**
     * 设置https默认配置
     * </p>
     * 默认忽略https的证书校验和hostname校验
     *
     * @author buyi
     * @date 2017上午11:04:16
     * @since 1.0.0
     */
    private static void setHttpsVerifyDefaultConfig() {

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            // 设置默认ssl套接工厂,该配置是全局的
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(DO_NOT_VERIFY);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否https路径
     *
     * @param url
     * @return
     * @throws RuntimeException 如果参数url为空，则抛出异常
     * @author buyi
     * @date 2017上午11:24:21
     * @since 1.0.0
     */
    private static boolean isHttps(String url) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("参数url为空");
        }

        return url.toLowerCase().startsWith(HTTPS_PREFIX);
    }

    /**
     * 拼接参数，参数值通过指定的charset转码
     *
     * @param params
     * @param charset
     * @return
     * @author buyi
     * @date 2017上午11:53:17
     * @since 1.0.0
     */
    private static String joinParams(Map<String, String> params, String charset) {
        if (params == null || params.isEmpty()) {
            return "";
        }

        StringBuilder str = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.get(key) == null) {
                continue;
            }

            if (str.length() > 0) {
                str.append("&");
            }

            try {
                str.append(key).append("=").append(URLEncoder.encode(params.get(key), charset));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }

        return str.toString();
    }

    /**
     * 获取字符集
     * </p>
     * 如果传参为空，则返回默认字符集{@link HttpUtil2#DEFAULT_CHARSET}
     *
     * @param charset
     * @return
     * @author buyi
     * @date 2017下午12:23:08
     * @since 1.0.0
     */
    private static String getCharset(String charset) {
        if (StringUtils.isBlank(charset)) {
            return DEFAULT_CHARSET;
        }

        return charset;
    }

    /**
     * 拼接请求类型
     *
     * @param contentType
     * @param charset
     * @return
     * @author buyi
     * @date 2017下午1:50:19
     * @since 1.0.0
     */
    private static String joinContentType(String contentType, String charset) {
        return contentType.concat(";charset=").concat(charset);
    }
}
