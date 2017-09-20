package com.norra.cloud.http.frame;

import java.io.IOException;

import org.apache.http.HttpResponse;

/**
 * HTTP 请求实际劳动工人
 * 
 * @author CharLiu
 */
public interface HttpWorker {
	HttpResponse doHttpRequest(Request<?> request) throws IOException;
}
