package com.yuepointbusiness.utils;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public class RSAUtils {

    private static final String KEY_ALGORITHM = "RSA";
    public static final String SPECIFIC_KEY_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String SIGNATURE_ALGORITHM = "SHA1withRSA";
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * 使用私钥对数据进行签名
     *
     * @param data          需要签名的数据
     * @param privateKeyStr 私钥（使用BASE64进行编码）
     * @return 返回加签名后的BASE64编码的字符串
     */
    public static String signByPrivateKey(String data, String privateKeyStr, String charsetName) throws Exception {
//        byte[] privateKeyByte = Base64.decodeBase64(privateKeyStr);
        byte[] privateKeyByte = Base64.decode(privateKeyStr, Base64.NO_WRAP);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);//私钥格式，从字节数组中构建的私钥内容
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//密钥工厂
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);//通过密钥工厂，从指定的密钥中生成RSA格式的私钥对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);//签名算法
        signature.initSign(privateKey);//签名算法采用私钥初始化
        signature.update(data.getBytes(charsetName));//将字符串编码为指定字符集字符数组，此处传入被签名的内容
        //return Base64.encodeBase64String(signature.sign());//签名，并将签名后的内容编码为base64的字符串返回
        return Base64.encodeToString(signature.sign(), Base64.NO_WRAP);//签名，并将签名后的内容编码为base64的字符串返回
    }

    /**
     * 使用公钥进行数字签名的校验
     *
     * @param data         需要进行验签的原始数据
     * @param publicKeyStr 公钥（BASE64编码字符串）
     * @param sign         签名 （BASE64编码字符串）
     * @return
     */
    public static boolean verifyByPublicKey(String data, String publicKeyStr, String sign, String charsetName) throws Exception {
        try {
//            byte[] publicKeyByte = Base64.decodeBase64(publicKeyStr);//公钥字符串解码
            byte[] publicKeyByte = Base64.decode(publicKeyStr, Base64.NO_WRAP);//公钥字符串解码
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(publicKeyByte);//x509格式结构的公钥
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);//rsa算法之密钥生成器
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(encodedKeySpec);//从结构性数据，转换为公钥对象
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);//签名算法
            signature.initVerify(publicKey);//初始化验证用的公钥
            signature.update(data.getBytes(charsetName));//更新需要要整的内容
            return signature.verify(Base64.decode(sign, Base64.NO_WRAP));//判断是否是对应的签名
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 公钥加密
     *
     * @param data
     * @param publicKeyStr
     * @return
     */
    public static String encryptByPublicKey(String data, String publicKeyStr)
            throws Exception {
        if (null == data)
            return null;
        byte[] dataB = data.getBytes("UTF-8");
        byte[] publicKeyByte = Base64.decode(publicKeyStr, Base64.NO_WRAP);
//        byte[] publicKeyByte = Base64.decodeBase64(publicKeyStr);
        X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(publicKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(encodedKeySpec);
        Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = dataB.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(dataB, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataB, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
//        return Base64.encodeBase64String(encryptedData);
        return Base64.encodeToString(encryptedData, Base64.NO_WRAP);
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param privateKeyStr
     * @return
     */
    public static String decryptByPrivateKey(String data, String privateKeyStr)
            throws Exception {
        if (null == data)
            return null;
//        byte[] dataB = Base64.decodeBase64(data);
//        byte[] privateKeyByte = Base64.decodeBase64(privateKeyStr);
        byte[] dataB = Base64.decode(data,Base64.NO_WRAP);
        byte[] privateKeyByte = Base64.decode(privateKeyStr, Base64.NO_WRAP);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyByte);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance(SPECIFIC_KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        // 模长
        int key_len = privateKey.getModulus().bitLength() / 8;
        byte[] decryptedData = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            int dataLength = dataB.length;
            for (int i = 0; i < dataLength; i += key_len) {
                int decryptLength = dataLength - i < key_len ? dataLength - i
                        : key_len;
                byte[] doFinal = cipher.doFinal(dataB, i, decryptLength);
                bout.write(doFinal);
            }
            decryptedData = bout.toByteArray();
        } finally {
            if (bout != null) {
                bout.close();
            }
        }
        return new String(decryptedData);
    }
}