package com.puffer.util.jdbc;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

/**
 * 数据库操作工具类，原生JDBC操作
 * </p>
 * 依赖于org.apache.commons.dbutils架包
 *
 * @author buyi
 * @date 2017年11月29日下午11:05:29
 * @since 1.0.0
 */
public class DBUtil implements Closeable {

	/**
	 * 默认jdbc驱动器
	 */
	private static final String DEFAULT_JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String JDBC_URL_FORMAT = "jdbc:mysql://%s:%d/%s?useUnicode=true&characterEncoding=utf8";

	private Connection conn;

	public DBUtil(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 新建一个实例
	 *
	 * @author buyi
	 * @date 2017年11月29日下午11:26:51
	 * @since 1.0.0
	 * @param ip
	 * @param port
	 * @param dbName
	 * @param user
	 * @param password
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static DBUtil newInstance(String ip, int port, String dbName, String user, String password) throws ClassNotFoundException, SQLException {
		Class.forName(DEFAULT_JDBC_DRIVER);
		String jdbcUrl = String.format(JDBC_URL_FORMAT, ip, port, dbName);
		Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
		return new DBUtil(conn);
	}

	/**
	 * 查询单对象
	 *
	 * @author buyi
	 * @date 2017年11月29日下午11:22:51
	 * @since 1.0.0
	 * @param t
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> T queryObject(T t, String sql, Object... params) throws SQLException {
		T obj = (T) new QueryRunner().query(conn, sql, new BeanHandler(t.getClass()), params);
		return obj;
	}

	/**
	 * 查询集合
	 *
	 * @author buyi
	 * @date 2017年11月29日下午11:19:57
	 * @since 1.0.0
	 * @param sql
	 * @param t
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> List<T> queryList(T t, String sql, Object... params) throws SQLException {
		List<T> list = (List<T>) new QueryRunner().query(conn, sql, new BeanListHandler(t.getClass()), params);

		return list;
	}

	/**
	 * 查询单元素集合
	 *
	 * @author buyi
	 * @date 2017年11月29日下午11:24:39
	 * @since 1.0.0
	 * @param t
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public <T> List<T> queryListColunm(T t, String sql, Object... params) throws SQLException {
		List<T> list = (List<T>) new QueryRunner().query(conn, sql, new ColumnListHandler<T>(), params);

		return list;
	}

	@Override
	public void close() throws IOException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
