package com.spring.common.util.rsa;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:RSA工具类
*/
public class RsaUtils {

    /** *//**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /** *//**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /** *//**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /** *//**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /** *//**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** *//**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /** *//**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        System.out.println(Base64.encodeBase64String(publicKey.getEncoded()));
        System.out.println(Base64.encodeBase64String(privateKey.getEncoded()));
        return keyMap;
    }

    /** *//**
     * <p>
     * 用私钥对信息生成数字签名
     * </p>
     *
     * @param data 已加密数据
     * @param privateKey 私钥(BASE64编码)
     *
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(data);
        
        return Base64.encodeBase64String(signature.sign());
    }

    /** *//**
     * <p>
     * 校验数字签名
     * </p>
     *
     * @param data 已加密数据sssssss
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     *
     * @return
     * @throws Exception
     *
     */
    public static boolean verify(byte[] data, String publicKey, String sign)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey publicK = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicK);
        signature.update(data);
        return signature.verify(Base64.decodeBase64(sign));
    }

    /** *//**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    /** *//**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    /** *//**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeBase64String(encryptedData);
    }

    /** *//**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64.encodeBase64String(encryptedData);
    }

    /** *//**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }

    /** *//**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return Base64.encodeBase64String(key.getEncoded());
    }
    
    static String privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJEq5BlUar57utzY+9Tha8GLK/ufni6qdhKZYmCdA6ES8tRvE2sBu4+9nKyK/lgv5QqWh19ygoWhDPmnZvoPGcbeGcHbMZ4YlXGyEgOjmIR33NqG9BrqixOcNPWEfme7Jn5f35iL2pkmMeGZd8aeGYdxDUjpIUgLkctrdyvt3NVhAgMBAAECgYBUaKcnH1HOHq3B2p1b5BM+/8h8UAyvP8jV+cAdQ08n6pet9ERLNT+1TeB653sLFhZM+MgQNMo2HzYnODKFdiBa/bBCjiDFft0xUXlbtXUSBsTFQEiepGii5ILRnSDqEvcpcQ/3sbhZ5q8RKXXcZBignzGN3rcizFJRxodMiFTQAQJBAPiiDY3g4XsAr4KiuLwzjeWKMUnFcabZLQdZ2z0ky82zk7Qr6KnaSMP0tJDqmqtZuset4iNq+2lYE3XMFQd25OECQQCVeAZuyeI33fgh6enl9U7YoJoye0JtHaQKF6MVOVrfN/9rvpeE3RS95E/t8sSStan8IS36JvZVB2u7e95l44CBAkEA1Tyu4ULAP20MGb8TLx4MEZRex0VWPuG987MGC7+WJzpfcEPETIBQrfceMbdzpYfUYFLqQrQLIYMPVZUNaBR5IQJBAIT2F2rYhjdCaufoSFx7Ip+MBn9frJCabIFZ04Ye1lp5WurCydC0Ri5B+mRmsDz+A2+5KEg9/qVXC5vlLcqfXYECQH77xj2FjxWJJR3j2Dwxdq9XTFzVwVjFxqoH25K9YvpFj1cfJc3SdSKxUs8++i2KMIuWugSSA0VCGSQG4GwNWKs=";
    public static void main(String[] args) throws Exception {
    	genKeyPair();
    	
    	String pub = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCAMnOkquWsMpoietf2UFDebZ3lsGuNdCVcphZB77Kg1UptuwKPHfjOqHfwJehy6M3230UskcRS6IjWxWBKinYtxQFJT3oCMge3Qw7tKDnQ3SdpDhj3w3jtER1w+ZDooX8Rxf8xO2rIlpT/ilKlLByi0pLYk2iYejHMEzeb1odvpwIDAQAB";
    	String pri = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIAyc6Sq5awymiJ61/ZQUN5tneWwa410JVymFkHvsqDVSm27Ao8d+M6od/Al6HLozfbfRSyRxFLoiNbFYEqKdi3FAUlPegIyB7dDDu0oOdDdJ2kOGPfDeO0RHXD5kOihfxHF/zE7asiWlP+KUqUsHKLSktiTaJh6McwTN5vWh2+nAgMBAAECgYA58ZPfm/cPrBeK+idfB9JllvpFdUZHu3WfuNpalzMQCxgHHP39Hjj1f6QmgZvdRGjD6wrjKEeqibxABxqM1qSvRqKgEE5bGPawRN8NmhQKZgH8dmgnroux9igSLUauW4iK9BEu7+VsMTIPpBfvw6Oqjbq2vSZxrOXbsrCjVOJ0MQJBAOeFgWS/SPj3879l7fOvAAk+m1vnLU60zk9Q8l8M5K1G86ykgvSuCmPlFHUwb2u2k2dqtE8OpcgSujEJlH3SFssCQQCNwE7HiNzwPnkS79MReaXtZo+kOSuoZm8dHL5Na2w/Gt46TBWBy44Sn1FnuO59zn3AHb/qJp+Unu9hBwjn/5MVAkEAu/wV5RTADo9dldIQlGVKtTbldIjPq/F49sP2GCFi6CMNJefqAPvzqcrOgpzSS3ZSV403XF27VCmIjtqFfGgCYwJAfPyF+hZJiWPsabAhEZWLoqn+GbV3M0Mu0iKYAbeoeHP+YrTXedy75tuncad0UmWSJ6WTpKhtqSQCujDzv2xwiQJBAJuh/ynAVJPKgRVgecq3xMEhTLdeFi6TAljbhHKPrXKP2C5FfMU1MhJDB+0iggWRQ5zBHhqWsSpJSL6x9k01gtA=";
    	
    	String source = "j00gzbnRzv92eqzddEQFzBcBaZ0lD289";
    	byte[] data = source.getBytes();
    	String mi = RsaUtils.encryptByPublicKey(data, pub);
    	System.out.println("加密后的密文："+mi);
    	String data1 = RsaUtils.decryptByPrivateKey(Base64.decodeBase64(mi), pri);
    	System.out.println("解密后的明文："+data1);

    }
}