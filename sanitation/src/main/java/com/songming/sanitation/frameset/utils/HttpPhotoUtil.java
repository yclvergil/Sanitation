package com.songming.sanitation.frameset.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.songming.sanitation.frameset.ApplicationExt;

public class HttpPhotoUtil {

	private static final String TAG = "HttpPhotoUtil";
	/** 连接超时 */
	private static final int TIMEOUT_CONNECTION = 30000; // 暂时延长 20000
	/** 读取超时 */
	private static final int TIMEOUT_SOCKET = 30000; // 暂时延长 20000 产品数据加载不上来

	/** 重试次次数 */
	private static final int RETRY_TIME = 1;
	/** 用户host */
	private static String proxyHost = "";
	/** 用户端口 */
	private static int proxyPort = 80;
	/** 是否使用用户端口 */
	private static boolean useProxy = false;

	/**
	 * 
	 * 发起网络请求 上传图片
	 * 
	 * @param url
	 *            URL
	 * @param requestData
	 *            requestData
	 * @return INPUTSTREAM
	 * @throws AppException
	 */
	public static String doPostUpLoad(String url, byte[] requestData,
			Handler handler) {
		String responseBody = null;
		HttpPost httpPost = null;
		HttpClient httpClient = null;
		int statusCode = -1;
		try {
			httpClient = getHttpClient();
			httpPost = new HttpPost(url);
			// 设置HTTP POST请求参数必须用NameValuePair对象
			MultipartEntity entity = new MultipartEntity();

			entity.addPart("key", new ByteArrayBody(requestData,
					"re-signup.PNG"));
			// 设置HTTP POST请求参数
			httpPost.setEntity(entity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			statusCode = httpResponse.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Toast.makeText(ApplicationExt.getApplication(), "网络错误",
						Toast.LENGTH_SHORT).show();
				Message msg = new Message();
				msg.what = 500;
				handler.sendMessage(msg);
				return null;
			}
			InputStream is = httpResponse.getEntity().getContent();
			responseBody = getStreamAsString(is, HTTP.UTF_8);
			Log.e(TAG, responseBody);
			JSONObject json = new JSONObject(responseBody);
			// json = new JSONObject(DES3.decode(json.optString("data", "{}")));
			json = new JSONObject(json.optString("data", "{}"));
			responseBody = json.toString();
			Message msg = new Message();
			msg.what = statusCode;
			msg.obj = responseBody;
			handler.sendMessage(msg);
		} catch (Exception e) {
			Message msg = new Message();
			msg.what = 500;
			handler.sendMessage(msg);
			e.printStackTrace();
		} finally {
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}
		return responseBody;
	}

	/**
	 * 
	 * 得到httpClient
	 * 
	 * @return
	 */
	private static HttpClient getHttpClient() {
		final HttpParams httpParams = new BasicHttpParams();

		if (useProxy) {
			HttpHost proxy = new HttpHost(proxyHost, proxyPort, "http");
			httpParams.setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
		}

		HttpConnectionParams.setConnectionTimeout(httpParams,
				TIMEOUT_CONNECTION);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SOCKET);
		HttpClientParams.setRedirecting(httpParams, true);
		final String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.14) Gecko/20110218 Firefox/3.6.14";

		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpClientParams.setCookiePolicy(httpParams,
				CookiePolicy.BROWSER_COMPATIBILITY);
		HttpProtocolParams.setUseExpectContinue(httpParams, false);
		HttpClient client = new DefaultHttpClient(httpParams);

		return client;
	}

	/**
	 * 
	 * 将InputStream 转化为String
	 * 
	 * @param stream
	 *            inputstream
	 * @param charset
	 *            字符集
	 * @return
	 * @throws IOException
	 */
	private static String getStreamAsString(InputStream stream, String charset)
			throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					stream, charset), 8192);
			StringWriter writer = new StringWriter();

			char[] chars = new char[8192];
			int count = 0;
			while ((count = reader.read(chars)) > 0) {
				writer.write(chars, 0, count);
			}

			return writer.toString();
		} finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

}
