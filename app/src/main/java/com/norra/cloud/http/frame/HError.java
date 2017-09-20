package com.norra.cloud.http.frame;

/**
 * 
 * @author CharLiu
 * 
 */
@SuppressWarnings("serial")
public class HError extends Throwable {
	public static final int CONNECT_TIME_OUT 	= -998;
	public static final int SO_TIME_OUT 		= -999;
	public static final int CONNECT_ERROR 		= -1000;
	public static final int NETWORK_ERROR 		= -1001;
	public static final int SERVER_ERROR 		= -1002;
	public static final int PARSE_ERROR 		= -1003;
	public static final int UNKNOWN_ERROR 		= -1004;

	protected String errorMsg;
	protected int errorCode;
	protected int httpStatusCode = 200;

	public HError(Throwable t, int code, String msg) {
		super(t);
		this.errorCode = code;
		this.errorMsg = msg;
	}

	public HError(Throwable t, int code) {
		super(t);
		this.errorCode = code;
		this.errorMsg = "";
	}

	public HError(int code) {
		this.errorCode = code;
		this.errorMsg = "";
	}

	public HError(int code, String msg) {
		this.errorCode = code;
		this.errorMsg = msg;
	}

	public String getErrorMsg() {
		return errorMsg + "ErrorCode:" + errorCode + " Stack:" + getMessage();
	}

	public int getErrorCode() {
		return errorCode;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public static boolean isHttpStateCode(int errorCode) {
		return errorCode <= NETWORK_ERROR && errorCode >= UNKNOWN_ERROR;
	}

	/**
	 * 服务器错误响应
	 * 
	 * @return
	 */
	public boolean isServerRespondedError() {
		return !isHttpStateCode(errorCode);
	}

    public boolean isTokenExpiredError() {
        return errorCode == 100;
    }

}