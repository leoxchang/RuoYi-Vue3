package com.truntao.common.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.truntao.common.constant.Constants;

/**
 * 通用http发送方法
 *
 * @author truntao
 */
public class HttpUtils {
    private HttpUtils() {
    }

    private static final String ACCEPT = "accept";

    private static final String CONNECTION = "connection";

    private static final String USER_AGENT = "user-agent";

    private static final String CONNECTION_VALUE = "Keep-Alive";

    private static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64)";

    private static final String URL_ERROR_INFO = "url [{}],param [{}]";

    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url 发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url) {
        return sendGet(url, StringUtils.EMPTY);
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        return sendGet(url, param, Constants.UTF8);
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url         发送请求的 URL
     * @param param       请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param contentType 编码类型
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param, String contentType) {

        String urlNameString = StringUtils.isNotBlank(param) ? url + "?" + param : url;
        log.info("sendGet - {}", urlNameString);
        try {
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            addHeader(connection);
            connection.connect();
            return getResponseResult(connection, contentType);
        } catch (ConnectException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendGet ConnectException", e);
        } catch (SocketTimeoutException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendGet SocketTimeoutException", e);
        } catch (IOException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendGet IOException", e);
        } catch (Exception e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpsUtil.sendGet Exception", e);
        }
        return null;
    }

    /**
     * 获取响应结果
     *
     * @param connection  connection
     * @param contentType 结果编码
     * @return 响应结果
     */
    private static String getResponseResult(URLConnection connection, String contentType) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(),
                contentType))) {

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("receive - {}", result);
        }
        return result.toString();
    }

    /**
     * 添加header
     *
     * @param connection connection
     */
    private static void addHeader(URLConnection connection) {
        connection.setRequestProperty(ACCEPT, "*/*");
        connection.setRequestProperty(CONNECTION, CONNECTION_VALUE);
        connection.setRequestProperty(USER_AGENT, USER_AGENT_VALUE);
    }

    /**
     * 添加post header
     *
     * @param connection connection
     */
    private static void addPostHeader(URLConnection connection) {
        addHeader(connection);
        connection.setRequestProperty("Accept-Charset", StandardCharsets.UTF_8.displayName());
        connection.setRequestProperty("contentType", StandardCharsets.UTF_8.displayName());
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        String result = null;
        try {
            log.info("sendPost - {}", url);
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            addPostHeader(conn);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            sendParam(conn, param);

            result = getResponseResult(conn, StandardCharsets.UTF_8.toString());
        } catch (ConnectException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendPost ConnectException", e);
        } catch (SocketTimeoutException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendPost SocketTimeoutException", e);
        } catch (IOException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendPost IOException", e);
        } catch (Exception e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpsUtil.sendPost Exception", e);
        }
        return result;
    }

    private static void sendParam(URLConnection connection, String param) throws IOException {
        try (PrintWriter out = new PrintWriter(connection.getOutputStream())) {
            out.print(param);
            out.flush();
        }
    }

    public static String sendSSLPost(String url, String param) {
        String result = null;
        String urlNameString = url + "?" + param;
        try {
            log.info("sendSSLPost - {}", urlNameString);
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            URL console = new URL(urlNameString);
            HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
            addPostHeader(conn);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            conn.connect();
            result = getResponseResult(conn);
            conn.disconnect();
        } catch (ConnectException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendSSLPost ConnectException", e);
        } catch (SocketTimeoutException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendSSLPost SocketTimeoutException", e);
        } catch (IOException e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpUtils.sendSSLPost IOException", e);
        } catch (Exception e) {
            log.error(URL_ERROR_INFO, url, param);
            log.error("调用HttpsUtil.sendSSLPost Exception", e);
        }
        return result;
    }

    /**
     * 获取响应结果
     *
     * @param connection connection
     * @return 响应结果
     */
    private static String getResponseResult(URLConnection connection) throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            String ret;
            while ((ret = in.readLine()) != null) {
                if (!ret.trim().isEmpty()) {
                    result.append(new String(ret.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8));
                }
            }
            log.info("receive - {}", result);
        }
        return result.toString();
    }

    private static class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {
            //do nothing
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
            //do nothing
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}