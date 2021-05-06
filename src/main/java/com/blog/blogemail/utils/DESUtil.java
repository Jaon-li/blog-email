package com.blog.blogemail.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

/**
 * Created by Wesley on 2019/7/2
 *
 * @author Wesley
 * @date 2019/7/2
 **/
public class DESUtil {

    private static Key key;
    //设置秘钥key
    private static String KEY_STR =  "sdf4654fgw2aqw63f54a4d138dc4304ebf393fe4";
    private static String CHARSET_NAME = "UTF-8";
    private static String ALGORITHM = "DES";

    private static Logger logger = LoggerFactory.getLogger(DESUtil.class);

    static {
        try {
            //生成DES算法对象
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM);
            //运用SHA1安全策略
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            //设置上密钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            //初始化基于SHA1的算法对象
            generator.init(secureRandom);
            //生成密钥对象
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            logger.error("DESUtil static",e);
        }
    }

    /**
     * 获取加密的信息
     *
     * @param str
     * @return
     */
    public static String getEncryptString(String str) {
        //基于BASE64编码，接收byte[]并转换成String
        //BASE64Encoder base64Encoder = new BASE64Encoder();
        try {
            // 按UTF8编码
            byte[] bytes = str.getBytes(CHARSET_NAME);
            // 获取加密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化密码信息
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密
            byte[] doFinal = cipher.doFinal(bytes);
            // byte[]to encode好的String并返回
            //return base64Encoder.encode(doFinal);
            return Base64.getEncoder().encodeToString(doFinal);
        } catch (Exception e) {
            logger.error("getEncryptString",e);
            return null;
        }
    }

    /**
     * 获取解密之后的信息
     *
     * @param str
     * @return
     */
    public static String getDecryptString(String str) {
        // 基于BASE64编码，接收byte[]并转换成String
        //BASE64Decoder base64decoder = new BASE64Decoder();
        try {
            // 将字符串decode成byte[]
            byte[] bytes = Base64.getDecoder().decode(str);
                    //base64decoder.decodeBuffer(str);
            // 获取解密对象
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 初始化解密信息
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 解密
            byte[] doFinal = cipher.doFinal(bytes);
            // 返回解密之后的信息
            return new String(doFinal, CHARSET_NAME);
        } catch (Exception e) {
            logger.error("getDecryptString",e);
            return  null;
        }
    }
}
