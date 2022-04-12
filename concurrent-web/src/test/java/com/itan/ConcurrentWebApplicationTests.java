package com.itan;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

@SpringBootTest
class ConcurrentWebApplicationTests {
    private final Long TIME_OUT = 1000 * 60 * 60L;

    private final String CERTIFICATE_KEY = "CERTIFICATE:INFO";

    @Autowired
    JedisPool jedisPool;

    @Test
    void encode() throws Exception {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("no", 1);
        map.put("serialNo", "HSDNSNGHSJ9999");
        map.put("licenseDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        map.put("totalCount", 10000);
        map.put("userCount", 4682);
        map.put("licenseStatus", "VALID");
        String data = map.toString();
        System.out.println("未加密的：" + data);
        //根据key生成密钥
        DESKeySpec keySpec = new DESKeySpec(CERTIFICATE_KEY.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        //加密
        Cipher cipher = Cipher.getInstance("des");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
        byte[] cipherData = cipher.doFinal(data.getBytes());
        String encode = new BASE64Encoder().encode(cipherData);
        System.out.println("加密后的: " + encode);

        //放入缓存
        Jedis jedis = jedisPool.getResource();
        jedis.psetex(CERTIFICATE_KEY, TIME_OUT, encode);

        //解密
        byte[] bytes = new BASE64Decoder().decodeBuffer(encode);
        SecureRandom random = new SecureRandom();
        cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
        byte[] plainData = cipher.doFinal(bytes);
        String decrypt = new String(plainData);
        System.out.println("解密后的: " + decrypt);
    }

    @Test
    void decrypt() throws Exception {
        //根据key生成密钥
        DESKeySpec keySpec = new DESKeySpec(CERTIFICATE_KEY.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("des");
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        System.out.println(secretKey.getEncoded());
        //获取缓存信息
        Jedis jedis = jedisPool.getResource();
        String cipherData = jedis.get(CERTIFICATE_KEY);
        System.out.println(cipherData);
        byte[] bytes = new BASE64Decoder().decodeBuffer(cipherData);
        //解密
        Cipher cipher = Cipher.getInstance("des");
        SecureRandom random = new SecureRandom();
        cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
        byte[] plainData = cipher.doFinal(bytes);
        String decrypt = new String(plainData);
        System.out.println("解密后的: " + decrypt);
    }

}
