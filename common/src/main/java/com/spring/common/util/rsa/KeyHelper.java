package com.spring.common.util.rsa;

import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:RSA工具类
*/
public class KeyHelper {
	/**
	 * 获取公钥
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	/**
	 * 获取密钥
	 * 
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);
		byte[] keyBytes = new byte[(int) f.length()];
		dis.readFully(keyBytes);
		dis.close();
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}

	/**
	 * 生存rsa公钥和密钥
	 * 
	 * @param publicKeyFilename
	 * @param privateKeyFilename
	 * @param password
	 * @throws IOException
	 * @throws NoSuchAlgorithmException
	 */
	public static void generateKey(String publicKeyFilename, String privateKeyFilename, String password)
			throws IOException, NoSuchAlgorithmException {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		SecureRandom secureRandom = new SecureRandom(password.getBytes());
		keyPairGenerator.initialize(1024, secureRandom);
		KeyPair keyPair = keyPairGenerator.genKeyPair();
		byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
		FileOutputStream fos = new FileOutputStream(publicKeyFilename);
		fos.write(publicKeyBytes);
		fos.close();
		byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
		fos = new FileOutputStream(privateKeyFilename);
		fos.write(privateKeyBytes);
		fos.close();
	}

	public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
		

		generateKey("D:/Java/Farben-Project-Manage/src/main/resources/RSA/pub.key",
				"D:/Java/Farben-Project-Manage/src/main/resources/RSA/pri.key", "1*&623!f");
	}
}
