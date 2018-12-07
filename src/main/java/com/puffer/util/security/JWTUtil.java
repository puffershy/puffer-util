package com.puffer.util.security;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

/**
 * JSON WEB TOKEN 工具类
 * 
 * @author buyi
 * @since 1.0.0
 * @date 2018下午5:25:34
 */
public class JWTUtil {
	/** 过期时间 30 分钟 */
	private static final long EXPIRE_TIME = 30 * 60 * 1000;

	/**
	 * 从token中获得用户名
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:28:04
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			return null;
		}
	}

	/**
	 * 生成token
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:34:05
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String generateToken(String username, String password) {
		try {
			Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256(password);
			// 附带username信息
			return JWT.create().withClaim("username", username).withExpiresAt(date).sign(algorithm);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

	/**
	 * 校验token是否正确
	 * 
	 * @author buyi
	 * @since 1.0.0
	 * @date 2018下午5:35:44
	 * @param token
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean verify(String token, String username, String password) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(password);
			JWTVerifier verifier = JWT.require(algorithm).withClaim("username", username).build();
			// 校验不通过，会抛出异常
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception exception) {
			return false;
		}
	}
}
