package pp.pokemon.pm.common.util.security;

import org.springframework.stereotype.Component;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.message.SecurityMessage;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RsaUtil {

    private static Cipher cipher;

    /**
     * static代码块在创建对象后(按顺序)执行
     */
    static {
        try {
            cipher = Cipher.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将字符串进行rsa加密, 返回加密后的String
     * 加完后使用base64进行加密
     * @param publicKey 公钥
     * @param plainText 待加密文本
     * @return
     */
    public static String encrypt(String publicKey, String plainText){
        try {
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
            byte[] bytes = cipher.doFinal(plainText.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            throw new RetException(SecurityMessage.ENCRYPT_FAILURE_CODE, SecurityMessage.ENCRYPT_FAILURE_MSG);
        }
    }

    /**
     * 获取公钥
     * 注意(mac默认生成的.pem)rsa秘钥文件是使用base64编码的, 需要经过解码才能使用
     * @param publicKey
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        // 使用base64对公钥进行解密
        byte[] bytes = Base64.getDecoder().decode(publicKey);
        // 获取公钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     * 将字符串进行rsa解密, 返回解密后的String
     * 加完后使用base64进行加密
     * @param privateKey
     * @param plainText
     * @return
     * @throws Exception
     */
    public static String decrypt(String privateKey, String plainText) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            byte[] bytes = cipher.doFinal(Base64.getDecoder().decode(plainText));
            return new String(bytes);
        } catch (Exception e) {
            throw new RetException(SecurityMessage.DECRYPT_FAILURE_CODE, SecurityMessage.DECRYPT_FAILURE_MSG);
        }
    }

    /**
     * 获取私钥
     * 注意(mac默认生成的.pem)rsa秘钥文件是使用base64编码的, 需要经过解码才能使用
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String privateKey) throws Exception{
        // 使用base64对公钥进行解密
        byte[] bytes = Base64.getDecoder().decode(privateKey);
        // 获取私钥
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }


    public static void main(String[] args) throws Exception {
        String pubKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAOJ+7n3neYQIXT05wPiclY7NvYrQ+8BQ4JxuPrSU0U1EO7pc197OoFGoKSnK+/dvD6GFYucioEMe61KhKyUnXSsCAwEAAQ==";
        String pw = "123456";
        String pw1 = encrypt(pubKey,pw);
        System.out.println(pw1);

        System.out.println("\n");
        String priKey = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEA4n7ufed5hAhdPTnA+JyVjs29itD7wFDgnG4+tJTRTUQ7ulzX3s6gUagpKcr7928PoYVi5yKgQx7rUqErJSddKwIDAQABAkEA1jsBEY1z3nKa3mJWJ9DTlTL86OQqewkEqnGMVfm8zrrHoBZ2I7jhoLd6n8UFV011BWPoJQRDoyX/me6jl/XAIQIhAPexZYTtUSW8tueWuhblUWltGZ1NJZ9Z6KgsGKe+uottAiEA6heLR0WLZihGEbAkN8Pin9VEDZbD7TSy0ryeXK9A0/cCIA9NjXkd9GKBe0dpn4uklVgPHoMCbi2fL2mcd9V9SictAiEA0oG1l7PsNUOGLyXMi/E0/DJV7jpjmg3HuUYwynw2ueUCIQCeiS57r0rFhze4oDGrhQmgutl8vxFqqb64bYhTy7IzOQ==";
        System.out.println(decrypt(priKey, pw1));
    }
}
