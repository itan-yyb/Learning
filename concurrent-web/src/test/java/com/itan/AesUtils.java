package com.itan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.itan.config.CertificateInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @Author: ye.yanbin
 * @Date: 2022/4/18
 */
public class AesUtils {
    // private final static String DES = "DES";
    //
    // private final static String UTF8 = "UTF-8";
    //
    // static SecretKeyFactory keyFactory = null;
    //
    // static {
    //     try {
    //         keyFactory = SecretKeyFactory.getInstance(DES);
    //     } catch (NoSuchAlgorithmException e) {
    //         e.printStackTrace();
    //     }
    // }
    //
    // /**
    //  * Description 根据键值进行加密
    //  * @param data
    //  * @param key 加密键byte数组
    //  * @return
    //  * @throws Exception
    //  */
    //
    // public static String encrypt(String data, String key) throws Exception {
    //     // 使用指定的编码获取要加密的内容，一般秘钥都是字母或数字不用指定编码，但指定也可以
    //     byte[] bt = encrypt(data.getBytes(UTF8), key.getBytes(UTF8));
    //     //注意：在加密和解密的时候使用sun的BASE64Encoder()进行编码和解码不然会有乱码
    //     //网上查看了很多实例，都没有编码和解码，也说没有乱码问题，而我这里出现了乱码，所以使用BASE64Encoder()进行了编码解码
    //     String strs = new BASE64Encoder().encode(bt);
    //     return strs;
    // }
    //
    // /**
    //  * Description 根据键值进行解密
    //  * @param data
    //  * @param key 加密键byte数组
    //  * @return
    //  * @throws IOException
    //  * @throws Exception
    //  */
    // public static String decrypt(String data, String key) throws IOException, Exception {
    //     if (data == null)
    //         return null;
    //     //注意：在加密和解密的时候使用sun的BASE64Encoder()进行编码和解码不然会有乱码
    //     BASE64Decoder decoder = new BASE64Decoder();
    //     byte[] buf = decoder.decodeBuffer(data);
    //     byte[] bt = decrypt(buf, key.getBytes(UTF8));
    //     return new String(bt, UTF8);
    // }
    //
    //
    //
    // /**
    //  * Description 根据键值进行加密
    //  * @param data
    //  * @param key 加密键byte数组
    //  * @return
    //  * @throws Exception
    //  */
    // private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
    //     // 生成一个可信任的随机数源
    //     SecureRandom sr = new SecureRandom();
    //     // 从原始密钥数据创建DESKeySpec对象，也就是创建秘钥的秘钥内容
    //     DESKeySpec dks = new DESKeySpec(key);
    //     // 密钥工厂用来将密钥(类型 Key 的不透明加密密钥)转换为密钥规范(底层密钥材料的透明表示形式)，反之亦然。秘密密钥工厂只对秘密(对称)密钥进行操作。
    //     // 这里改为使用单例模式
    //     //SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    //     //根据提供的密钥规范(密钥材料)生成 SecretKey(秘钥) 对象。
    //     SecretKey securekey = keyFactory.generateSecret(dks);
    //     // Cipher对象实际完成加密操作,此类为加密和解密提供密码功能
    //     Cipher cipher = Cipher.getInstance(DES);
    //     // 用密钥和随机源初始化此 Cipher。ENCRYPT_MODE用于将 Cipher 初始化为加密模式的常量。
    //     cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
    //     //正式执行加密操作
    //     return cipher.doFinal(data);
    // }
    //
    // /**
    //  * Description 根据键值进行解密
    //  * @param data
    //  * @param key 加密键byte数组
    //  * @return
    //  * @throws Exception
    //  */
    // private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
    //     // 生成一个可信任的随机数源
    //     SecureRandom sr = new SecureRandom();
    //     // 从原始密钥数据创建DESKeySpec对象，也就是创建秘钥的秘钥内容
    //     DESKeySpec dks = new DESKeySpec(key);
    //     // 密钥工厂用来将密钥(类型 Key 的不透明加密密钥)转换为密钥规范(底层密钥材料的透明表示形式)，反之亦然。秘密密钥工厂只对秘密(对称)密钥进行操作。
    //     // 这里改为使用单例模式
    //     //SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
    //     //根据提供的密钥规范(密钥材料)生成 SecretKey(秘钥)对象。
    //     SecretKey securekey = keyFactory.generateSecret(dks);
    //     // Cipher类为加密和解密提供密码功能
    //     Cipher cipher = Cipher.getInstance(DES);
    //     // DECRYPT_MODE用于将 Cipher 初始化为解密模式的常量。
    //     cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
    //     // 正式进行解密操作
    //     return cipher.doFinal(data);
    // }

    public static void main(String[] args) throws Exception {
        CertificateInfo cert = new CertificateInfo();
        cert.setNo("SVR2011300001");
        cert.setSerialNo("ADE8F-46D32-9A1BF-F88C8-7E149");
        cert.setLicenseDate("2020-11-30 17:53:09");//Mon Nov 30 17:53:09 GMT+08:00 2020
        cert.setValidDate("2023-11-30 00:00:00");//Thu Nov 30 00:00:00 GMT+08:00 2023
        cert.setTotalCount(2000);
        cert.setType("comm");
        cert.setParams("");
        cert.setSignature("MCwCFGcgQhOF+SUTmutfFKj6Txx+0BvCAhRwprxVw9q94tx4hsT2nIqE3dinDg==");
        cert.setUserCount(1535);
        cert.setLicenseStatus("VALID");
        String jsonString = JSON.toJSONString(cert);
        System.out.println("json为：" + jsonString);
        String encrypt = aesEncrypt(jsonString, "lifecyclelifecyc");
        System.out.println("加密信息：" + encrypt);
        String decrypt = aesDecrypt(encrypt, "lifecyclelifecyc");
        CertificateInfo info = JSON.parseObject(decrypt, new TypeReference<CertificateInfo>() {});
        System.out.println("解密信息：" + info);
    }

    /**
     * AES加密
     * @param content 明文
     * @param encryptKey 秘钥，必须为16个字符组成
     * @return 密文
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        if (StringUtils.isEmpty(content) || StringUtils.isEmpty(encryptKey)) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        byte[] encryptStr = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        Base64.encodeBase64String(encryptStr);
        return new BASE64Encoder().encode(encryptStr);
    }

    /**
     * AES解密
     * @param content
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String content, String encryptKey) throws Exception {
        byte[] contentNew = Base64.decodeBase64(content);
        SecretKeySpec skeySpec = new SecretKeySpec(encryptKey.getBytes(StandardCharsets.UTF_8), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); // "算法/模式/补码方式"
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        return new String(cipher.doFinal(contentNew));
    }
}
