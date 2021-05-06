package com.blog.blogemail.utils;

//import sun.security.krb5.internal.crypto.Aes128;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;

/**
 * Created by Wesley on 2019/7/10
 *
 * @author Wesley
 * @date 2019/7/10
 **/
public class AESUtil {

    //private static Logger logger = LoggerFactory.getLogger(Aes128.class);

    /**
     * 获得一个 密钥长度为 8*32 = 256 位的 AES 密钥，
     *
     * @return 返回经 BASE64 处理之后的密钥字符串（并截取 32 字节长度）
     */
    public static String getAESStrKey() {
        UUID uuid = UUID.randomUUID();
        String aesKey = Base64.getEncoder().encodeToString(uuid.toString().getBytes()).substring(2, 34);
        return aesKey;
    }

    /**
     * 获得一个初始化向量，初始化向量长度为 4*4 = 16 个字节
     *
     * @return 返回经 BASE64 处理之后的密钥字符串（并截取 16 字节长度）
     */
    public static String getIv() {
        UUID uuid = UUID.randomUUID();
        String iv = Base64.getEncoder().encodeToString(uuid.toString().getBytes()).substring(2, 18);
        return iv;
    }


    /**
     * 加密
     *
     * @param content      待加密内容
     * @param secretKeyStr 加密使用的 AES 密钥，BASE64 编码后的字符串
     * @param iv           初始化向量，长度为 16 个字节，16*8 = 128 位
     * @return 加密后的密文, 进行 BASE64 处理之后返回
     */
    public static String encryptAES(byte[] content, String secretKeyStr, String iv) {
        try {
            // 获得一个 SecretKeySpec
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyStr.getBytes(), "AES");
            // 获得加密算法实例对象 Cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
            // 获得一个 IvParameterSpec
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());  // 使用 CBC 模式，需要一个向量 iv, 可增加加密算法的强度
            // 根据参数初始化算法
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            // 执行加密并返回经 BASE64 处助理之后的密文
            return Base64.getEncoder().encodeToString(cipher.doFinal(content));
        } catch (Exception e) {
            //logger.error("加密", e);
            return null;
        }
    }

    /**
     * 解密
     *
     * @param content:      待解密内容，是 BASE64 编码后的字节数组
     * @param secretKeyStr: 解密使用的 AES 密钥，BASE64 编码后的字符串
     * @param iv:           初始化向量，长度 16 字节，16*8 = 128 位
     * @return 解密后的明文，直接返回经 UTF-8 编码转换后的明文
     */
    public static String decryptAES(byte[] content, String secretKeyStr, String iv) {
        try {
            // 密文进行 BASE64 解密处理
            byte[] contentDecByBase64 = Base64.getDecoder().decode(content);
            // 获得一个 SecretKeySpec
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyStr.getBytes(), "AES");
            // 获得加密算法实例对象 Cipher
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //"算法/模式/补码方式"
            // 获得一个初始化 IvParameterSpec
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes());
            // 根据参数初始化算法
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            // 解密
            byte[] bytes = cipher.doFinal(contentDecByBase64);
            return new String(bytes, "utf8");
        } catch (Exception e) {
            //logger.error("解密", e);
            return null;
        }
    }


}
