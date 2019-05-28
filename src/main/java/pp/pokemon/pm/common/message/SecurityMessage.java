package pp.pokemon.pm.common.message;

public class SecurityMessage {

    public static final String INVALID_TOKEN_CODE = "50001";
    public static final String INVALID_TOKEN_MSG = "token失效";

    public static final String INVALID_ACCESS_CODE = "50002";
    public static final String INVALID_ACCESS_MSG = "账户信息获取失败";

    public static final String INVALID_USER_CODE = "50003";
    public static final String INVALID_USER_MSG = "用户不可用";

    public static final String INCORRECT_MOBILE_PATTERN_CODE = "50004";
    public static final String INCORRECT_MOBILE_PATTERN_MSG = "错误的手机格式";

    public static final String INCORRECT_EMAIL_PATTERN_CODE = "50005";
    public static final String INCORRECT_EMAIL_PATTERN_MSG = "错误的邮箱格式";

    public static final String EXISTED_USERNAME_CODE = "50006";
    public static final String EXISTED_USERNAME_MSG = "用户名已存在";

    public static final String EXISTED_MOBILE_CODE = "50007";
    public static final String EXISTED_MOBILE_MSG = "手机已存在";

    public static final String EXISTED_EMAIL_CODE = "50008";
    public static final String EXISTED_EMAIL_MSG = "邮箱已存在";

    public static final String ENCRYPT_FAILURE_CODE = "50009";
    public static final String ENCRYPT_FAILURE_MSG = "加密失败";

    public static final String DECRYPT_FAILURE_CODE = "50010";
    public static final String DECRYPT_FAILURE_MSG = "解密失败";

    public static final String INCORRECT_PASSWORD_CODE = "50011";
    public static final String INCORRECT_PASSWORD_MSG = "密码错误";

    public static final String INCORRECT_OLD_PASSWORD_CODE = "50012";
    public static final String INCORRECT_OLD_PASSWORD_MSG = "旧密码错误";

}
