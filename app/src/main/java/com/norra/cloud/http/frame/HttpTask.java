package com.norra.cloud.http.frame;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.conn.ConnectTimeoutException;

import com.norra.cloud.http.ObjectRequest;
import com.norra.cloud.utils.Logger;

/**
 * Http 请求任务
 * 
 * @author CharLiu
 * 
 */
@SuppressWarnings("rawtypes")
public class HttpTask implements Runnable {

	private static int DEFAULT_POOL_SIZE = 4096;
	private Request request;
	private final HttpWorker httpWork;
	private ByteArrayPool mPool;
	private ResponsePoster mPoster;
	private static final int MAX_RETRY_COUNT = 2;

	public HttpTask(Request<?> request, ResponsePoster poster, HttpWorker worker) {
		this(request, poster, new ByteArrayPool(DEFAULT_POOL_SIZE), worker);
	}

	public HttpTask(Request<?> req, ResponsePoster poster, ByteArrayPool pool, HttpWorker worker) {
		request = req;
		mPool = pool;
		mPoster = poster;
		httpWork = worker;
	}

	@Override
	public void run() {
		Logger.d("API", "Request:" + request.getUrl() + "\n" + "参数:" + request.getStringParams() + ", Header:" + request.getHeaders());
		int tryTimes = 0;
		Response<?> response = null;
		while (tryTimes < MAX_RETRY_COUNT) {
			tryTimes++;
			HttpResponse httpResponse = null;
			try {
				if (isInterrupted() || request.isCancled()) {
					return;
				}
				httpResponse = httpWork.doHttpRequest(request);
				StatusLine statusLine = httpResponse.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				Map<String, String> headers = new HashMap<String, String>();
				headers = convertHeaders(httpResponse.getAllHeaders());
				if (statusCode == HttpStatus.SC_OK) {
					byte[] data = null;
					if (httpResponse.getEntity() != null) {
						data = entityToBytes(httpResponse.getEntity());
					} else {
						data = new byte[0];
					}
					response = request.parseResponse(new NetworkResponse(data, headers));
					if (response != null) {
						request.dispatchResponseInThread(response.result);
					} else {
						response = Response.error(new HError(HError.PARSE_ERROR, "Parse result is null"));
					}
					break; //SC == 200 时不再重试
				} else {
					if (httpResponse.getEntity() != null) {
						if (HLog.Config.LOG_D) {
							byte[] data = entityToBytes(httpResponse.getEntity());
							if (request instanceof ObjectRequest) {
								String method = ((ObjectRequest) request).getMethod();
								Log.e("", "接口：" + method + ", 返回非200 ->" + statusCode + ", 错误信息:" + new String(data));
							}
						}
					}
					response = Response.error(new HError(statusCode, "Server error statusCode not 200, statusCode:" + statusCode));
				}
				
			} catch (SocketTimeoutException e) {
				//默认响应超时
				response = Response.error(new HError(e, HError.SO_TIME_OUT, "Socket Timeout"));
			} catch (ConnectTimeoutException e) {
				response = Response.error(new HError(e, HError.CONNECT_TIME_OUT, "Connect Timeout"));
			} catch (MalformedURLException e) {
				response = Response.error(new HError(e, HError.NETWORK_ERROR, "Malformed URL"));
			} catch (ConnectException e){
				if (HLog.Config.LOG_E)
					e.printStackTrace();
				response = Response.error(new HError(e, HError.CONNECT_ERROR, "IOException, error:" + e.getMessage()));
			} catch (IOException e) {
				if (HLog.Config.LOG_E)
					e.printStackTrace();
				response = Response.error(new HError(e, HError.NETWORK_ERROR, "IOException, error:" + e.getMessage()));
			} catch (Throwable t) {
				if (HLog.Config.LOG_E)
					t.printStackTrace();
				response = Response.error(new HError(t, HError.UNKNOWN_ERROR, "IOException, error:" + t.getMessage()));
			}
		}
		if (!request.isCancled() && !isInterrupted()) {
			mPoster.dispatchResponse(request, response);
		}
	}

	private boolean isInterrupted() {
		return Thread.interrupted();
	}

	private byte[] entityToBytes(HttpEntity entity) throws IOException {
		PoolingByteArrayOutputStream bytes = new PoolingByteArrayOutputStream(mPool, (int) entity.getContentLength());
		byte[] buffer = null;
		try {
			InputStream in = entity.getContent();
			if (in == null) {
				throw new IOException();
			}
			buffer = mPool.getBuf(1024);
			int count;
			while ((count = in.read(buffer)) != -1) {
				bytes.write(buffer, 0, count);
			}
			return bytes.toByteArray();
		} finally {
			consumeEntity(entity);
			mPool.returnBuf(buffer);
			bytes.close();
		}
	}

	private void consumeEntity(HttpEntity entity) {
		try {
			// Close the InputStream and release the resources by
			entity.consumeContent();
		} catch (IOException e) {
			// This can happen if there was an exception above that left the
			// entity in
			// an invalid state.
			HLog.e("Error occured when calling consumingContent");
		}
	}

	/**
	 * Converts Headers[] to Map<String, String>.
	 */
	private static Map<String, String> convertHeaders(Header[] headers) {
		Map<String, String> result = new HashMap<String, String>();
		for (int i = 0; i < headers.length; i++) {
			result.put(headers[i].getName(), headers[i].getValue());
		}
		return result;
	}

}
