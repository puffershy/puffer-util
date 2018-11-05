package com.buyi.util.net.ftp;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * 线程池配置<br>
 * 管理初始化线程池的配置
 *
 * @author buyi
 * @date 2018年4月1日下午10:03:36
 * @since 1.0.0
 */
public class FtpPoolConfig extends GenericKeyedObjectPoolConfig {

	public FtpPoolConfig() {
		setTestWhileIdle(true);
        setTimeBetweenEvictionRunsMillis(60000);
        setMinEvictableIdleTimeMillis(1800000L);
        setTestOnBorrow(true);
	}
}
