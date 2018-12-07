package com.puffer.util.net.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FTP工具类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2018下午2:43:06
 */
public class FtpUtil {

	private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	/**
	 * 上传文件
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午3:10:08
	 * @param client
	 * @param origFile
	 *            源文件路径
	 * @param destDir
	 *            目标文件夹路径
	 * @return
	 * @throws IOException
	 */
	public static boolean upload(FTPClient client, String origFile, String destDir) throws IOException {
		if (client == null) {
			throw new IllegalArgumentException("param client must be not null");
		}

		boolean isChange = client.changeWorkingDirectory(destDir);
		if (!isChange) {
			// 如果定位文件夹路径不成功，则新建文件夹
			mkdir(client, destDir);
		}

		File file = new File(origFile);
		if (!file.exists()) {
			throw new FileNotFoundException("file :" + origFile + " is not found");
		}

		try (FileInputStream in = new FileInputStream(file)) {
			return client.storeFile(file.getName(), in);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午4:14:35
	 * @param client
	 * @param origDir
	 * @param destDir
	 * @param origFile
	 * @return
	 * @throws IOException
	 */
	public static boolean download(FTPClient client, String origDir, String origFile, String destDir)
			throws IOException {
		if (client == null) {
			throw new IllegalArgumentException("param client must be not null");
		}

		boolean changeWorkingDirectory = client.changeWorkingDirectory(origDir);
		if (!changeWorkingDirectory) {
			logger.error("origDir is not found." + origDir);
			return false;
		}

		// 判断下载目的文件夹路径是否存在,如果不存在，则新建目录
		FileUtils.forceMkdir(new File(destDir));

		try (OutputStream out = new FileOutputStream(destDir.concat(File.separator).concat(origFile))) {
			// 下载文件
			return client.retrieveFile(origFile, out);
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午3:32:40
	 * @param client
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public static boolean mkdir(FTPClient client, String dir) throws IOException {
		if (client == null) {
			throw new IllegalArgumentException("param client must be not null");
		}

		return client.makeDirectory(dir);
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午4:01:26
	 * @param client
	 * @param destFile
	 * @return
	 * @throws IOException
	 */
	public static boolean exists(FTPClient client, String destFile) throws IOException {
		if (client == null) {
			throw new IllegalArgumentException("param client must be not null");
		}

		FTPFile[] listFiles = client.listFiles(destFile);
		if (listFiles != null && listFiles.length > 0) {
			return true;
		}

		return false;
	}

}
