package com.norra.cloud.http.frame;

import com.norra.cloud.http.frame.FileDownloadTask.DownloadListener;

/**
 * 
 * @author CharLiu
 * 
 */
public class FileDownloader {

	public static FileDownloadTask download(final String fileUrl, String savePath, DownloadListener listener) {
		FileDownloadTask task = new FileDownloadTask(fileUrl, savePath, listener);
		task.startDownload();
		return task;
	}

}
