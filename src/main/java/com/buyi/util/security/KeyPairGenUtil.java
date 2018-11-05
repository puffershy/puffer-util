package com.buyi.util.security;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

import org.apache.commons.codec.binary.Base64;

public class KeyPairGenUtil {

	public static void main(String[] args) throws Exception {

		String privatePath = "D:/keys/fuiou/buyiPrivateKey.key"; // 准备导出的私钥
		String publicPath = "D:/keys/fuiou/buyiPublicKey.key"; // 准备导出的公钥
		PrivateKey privateKey = getPrivateKeyFromStore();
		createKeyFile(privateKey, privatePath);
		PublicKey publicKey = getPublicKeyFromCrt();
		createKeyFile(publicKey, publicPath);

		byte[] publicKeyBytes = publicKey.getEncoded();
		byte[] privateKeyBytes = privateKey.getEncoded();
		String publicKeyBase64 = Base64.encodeBase64String(publicKeyBytes);
		String privateKeyBase64 = Base64.encodeBase64String(privateKeyBytes);

		System.out.println("publicKeyBase64:" + publicKeyBase64);

		System.out.println("privateKeyBase64:" + privateKeyBase64);
	}

	private static PrivateKey getPrivateKeyFromStore() throws Exception {
		String alias = "test"; // KeyTool中生成KeyStore时设置的alias
		String storeType = "JCEKS"; // KeyTool中生成KeyStore时设置的storetype
		char[] pw = "5201314".toCharArray(); // KeyTool中生成KeyStore时设置的storepass
		String storePath = "D:/keys/fuiou/test.store"; // KeyTool中已生成的KeyStore文件
		storeType = null == storeType ? KeyStore.getDefaultType() : storeType;
		KeyStore keyStore = KeyStore.getInstance(storeType);
		InputStream is = new FileInputStream(storePath);
		keyStore.load(is, pw);
		// 由密钥库获取密钥的两种方式
		// KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry)
		// keyStore.getEntry(alias, new KeyStore.PasswordProtection(pw));
		// return pkEntry.getPrivateKey();
		return (PrivateKey) keyStore.getKey(alias, pw);
	}

	private static PublicKey getPublicKeyFromCrt() throws CertificateException, FileNotFoundException {
		String crtPath = "D:/keys/fuiou/test.crt"; // KeyTool中已生成的证书文件
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		FileInputStream in = new FileInputStream(crtPath);
		Certificate crt = cf.generateCertificate(in);
		PublicKey publicKey = crt.getPublicKey();
		return publicKey;
	}

	private static void createKeyFile(Object key, String filePath) throws Exception {
		FileOutputStream fos = new FileOutputStream(filePath);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(key);
		oos.flush();
		oos.close();
	}
}