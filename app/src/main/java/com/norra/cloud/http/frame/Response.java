package com.norra.cloud.http.frame;

/**
 * 返回数据
 * 
 * @author CharLiu
 * 
 * @param <T>
 *            返回数据类型
 */
public class Response<T> {
	public final T result;
	public final HError error;

	public Response(T result) {
		this.result = result;
		this.error = null;
	}

	public Response(HError err) {
		this.result = null;
		this.error = err;
	}

	public boolean isSuccess() {
		return error == null;
	}

	/** Returns a successful response containing the parsed result. */
	public static <T> Response<T> success(T result) {
		return new Response<T>(result);
	}

	/**
	 * Returns a failed response containing the given error code and an optional
	 * localized message displayed to the user.
	 */
	public static <T> Response<T> error(HError error) {
		return new Response<T>(error);
	}

}
