package com.buyi.util.net.ftp;

import java.io.IOException;
import java.net.SocketException;

import com.buyi.util.net.ftp.FtpUtil;
import org.apache.commons.net.ftp.FTPClient;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * ftp工具类单元测试类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2018下午5:58:47
 */
public class FtpUtilTest {
	private FTPClient client;

	@BeforeClass
	public void initFTPClient() throws SocketException, IOException {
		client = new FTPClient();
		client.connect("ftp-1.fuiou.com", 9021);
		boolean login = client.login("JZH354237", "H3Y399btFLu45ykE");

		Assert.assertTrue(login, "FTP登录失败");

		client.setControlEncoding("UTF-8");
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		client.enterLocalPassiveMode();
	}

	@Test
	public void upload() throws SocketException, IOException {
		String origFile = "D:/home/webapps/p2p/fileStore/fuiou_tmp_img/0002900F035423735052419910128775X20180330.jpeg";
		String destDir = "/ImageUpload/buyi/";

		boolean upload = FtpUtil.upload(client, origFile, destDir);
		Assert.assertTrue(upload, "上传文件失败");

	}

	@Test
	public void download() throws IOException {
		String origDir = "/ImageUpload/20180404";
		String origFile = "0002900F035261363282119880225918X20180404.jpg";
		String destDir = "D:/home/webapps/p2p/fileStore/fuiou_tmp_img/";

		boolean download = FtpUtil.download(client, origDir, origFile, destDir);

		Assert.assertTrue(download, "下载文件失败");

	}

}
