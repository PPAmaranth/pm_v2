package pp.pokemon.pm.common.util.security;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Component
public class EncryptUtil {

    private static final String PW_SALT = "72079DF0F42725B83CE79CCA4AA64DCF";


    /**
     * 加密明文, 使用md5加密后, 截取前10位结果, 与salt拼接后再进行sha-1加密
     * @param password
     * @return
     */
    public static String encryptPw(String password){
        return SHA1(MD5(password).substring(0,10).concat(PW_SALT)).toUpperCase();
    }

    /**
     * 创建加密token, 将用户id+时间戳使用md5加密后, 使用sha-1加密, 取前十位
     * @param userId
     * @return
     */
    public static String encryptToken(Integer userId) {
        return SHA1(MD5(userId.toString().concat(LocalDateTime.now().toString()))).substring(0,10);
    }

    /**
     *
     * @param text
     * @return
     */
    public static String SHA1(String text) {
        MessageDigest sha1Digest = null;
        try {
            // 获得SHA-1摘要算法的 MessageDigest 对象
            sha1Digest = MessageDigest.getInstance("SHA-1");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 使用指定的字节更新摘要
        sha1Digest.update(text.getBytes());
        // 获得密文
        byte[] messageDigest = sha1Digest.digest();
        // 将密文转换为十六进制模式
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < messageDigest.length; i++) {
            String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (shaHex.length() < 2) {
                sb.append(0);
            }
            sb.append(shaHex);
        }
        return sb.toString();
    }

    private static String MD5(String text) {
        MessageDigest md5Digest = null;
        try {
            // 获得MD5摘要算法的 MessageDigest 对象
            md5Digest = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 使用指定的字节更新摘要
        md5Digest.update(text.getBytes());
        // 获得密文
        byte[] messageDigest = md5Digest.digest();
        // 将密文转换为十六进制模式
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < messageDigest.length; i++) {
            String md5Hex = Integer.toHexString(messageDigest[i] & 0xFF);
            if (md5Hex.length() < 2) {
                sb.append(0);
            }
            sb.append(md5Hex);
        }
        return sb.toString();
    }
}
