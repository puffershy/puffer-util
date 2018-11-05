package com.buyi.util.net.ftp;

import java.io.IOException;
import java.net.ConnectException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * FTPClient工厂
 *
 * @author buyi
 * @date 2018年4月1日下午9:58:07
 * @since 1.0.0
 */
public class FtpClientFactory implements KeyedPooledObjectFactory<FtpClientConfig, FTPClient> {

	/**
	 * 创建连接
	 */
	@Override
	public PooledObject<FTPClient> makeObject(FtpClientConfig key) throws Exception {
		FTPClient client = new FTPClient();
		client.connect(key.getHost(), key.getPort());
		int replyCode = client.getReplyCode();
		if (!FTPReply.isPositiveCompletion(replyCode)) {
			client.disconnect();
			throw new ConnectException("FTP refused connection.Host:" + key.getHost() + ",port:" + key.getPort());
		}

		boolean login = client.login(key.getUserName(), key.getPassword());
		if (!login) {
			throw new Exception(
					"FTPClient login failed.userName is " + key.getUserName() + ", password is " + key.getPassword());
		}

		// 设置编码格式
		client.setControlEncoding(key.getEncoding());
		client.setFileType(FTPClient.BINARY_FILE_TYPE);
		// 启用被动模式
		client.enterLocalPassiveMode();
		// 设置连接超时时间
		client.setConnectTimeout(key.getConnectTimeout());
		// 设置读取超时时间
		client.setDataTimeout(key.getReadTimeout());

		return new DefaultPooledObject<>(client);
	}

	/**
	 * 销毁连接
	 */
	@Override
	public void destroyObject(FtpClientConfig key, PooledObject<FTPClient> p) throws Exception {
		FTPClient client = p.getObject();
		if (client != null && client.isConnected()) {
			client.logout();
			client.disconnect();
		}
	}

	/**
	 * 校验连接
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:19:52
	 * @param key
	 * @param p
	 * @return
	 */
	@Override
	public boolean validateObject(FtpClientConfig key, PooledObject<FTPClient> p) {
		FTPClient client = p.getObject();
		try {
			return client.sendNoOp();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 激活连接
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:20:08
	 * @param key
	 * @param p
	 * @throws Exception
	 */
	@Override
	public void activateObject(FtpClientConfig key, PooledObject<FTPClient> p) throws Exception {
		p.getObject().sendNoOp();
	}

	@Override
	public void passivateObject(FtpClientConfig key, PooledObject<FTPClient> p) throws Exception {
		// TODO Auto-generated method stub
	}

}
