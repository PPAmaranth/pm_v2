package pp.pokemon.pm.service.user.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.pokemon.pm.common.aop.userAccess.UserAccessDetail;
import pp.pokemon.pm.common.aop.userAccess.RestContext;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.common.YesNo;
import pp.pokemon.pm.common.enums.user.UserStatus;
import pp.pokemon.pm.common.message.SecurityMessage;
import pp.pokemon.pm.common.util.security.EncryptUtil;
import pp.pokemon.pm.common.util.security.RsaUtil;
import pp.pokemon.pm.common.util.security.TokenUtil;
import pp.pokemon.pm.dao.entity.user.User;
import pp.pokemon.pm.dao.mapper.user.UserMapper;
import pp.pokemon.pm.service.user.UserService;
import pp.pokemon.pm.web.vo.user.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private static final String MOBILE_REG = "^[1]{1}[0-9]{10}$";

    private static final String EMAIL_REG = "^\\w+@{1}\\w+\\.{1}";

    @Value("${rsa.key.private}")
    private String privateKey;

    private static final Integer COMMON_LOGIN_SECONDS = 7200;

    private static final Integer REMEMBER_ME_LOGIN_SECONDS = 604800;

    /**
     * 用户注册
     * @param reqVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(UserRegistrationReqVo reqVo) {
        // 验参 : 手机/邮箱格式格式是否正确, 以及是否已经存在
        checkRegistrationParam(reqVo.getUsername(), reqVo.getMobile(), reqVo.getEmail(), null);

        User user = new User();
        BeanUtils.copyProperties(reqVo, user);
        // 密码 : 先用私钥解码然后加密并保存
        String decryptPw = RsaUtil.decrypt(privateKey, reqVo.getPassword());
        user.setPassword(EncryptUtil.encryptPw(decryptPw));
        // 账号状态为启用
        user.setStatus(UserStatus.ENABLED.getValue());
        user.setCreateDate(new Date());
        user.setUpdateDate(user.getCreateDate());

        userMapper.insert(user);
    }


    /**
     * 验参 : 格式是否正确, 以及是否已经存在
     * 对于注册用户, userId为null; 对于编辑用户, userId不为null
     * @param username
     * @param mobile
     * @param email
     * @param userId
     */
    private void checkRegistrationParam(String username, String mobile, String email, Integer userId) {
        // 手机格式为: 以1开头的11位数字
        Matcher mobileMatcher = Pattern.compile(MOBILE_REG).matcher(mobile);
        if (!mobileMatcher.matches()) {
            throw new RetException(SecurityMessage.INCORRECT_MOBILE_PATTERN_CODE, SecurityMessage.INCORRECT_MOBILE_PATTERN_MSG);
        }
        // 邮箱格式为: 任意字符(0-9a-zA-Z_) + @ + 任意字符 + 英文点 + 任意字符
        Matcher emailMatcher = Pattern.compile(EMAIL_REG).matcher(email);
        // matches全匹配, lookingAt匹配开头, find匹配任意位置
        if (!emailMatcher.find()) {
            throw new RetException(SecurityMessage.INCORRECT_EMAIL_PATTERN_CODE, SecurityMessage.INCORRECT_EMAIL_PATTERN_MSG);
        }

        // 用户名是否已存在
        Optional.ofNullable(userMapper.selectExistedByUsername(userId, username))
                .ifPresent(sameUsernameUser->{
                    throw new RetException(SecurityMessage.EXISTED_USERNAME_CODE, SecurityMessage.EXISTED_USERNAME_MSG);
                });
        // 手机是否已存在
        Optional.ofNullable(userMapper.selectExistedByMobile(userId, mobile))
                .ifPresent(sameMobileUser->{
                    throw new RetException(SecurityMessage.EXISTED_MOBILE_CODE, SecurityMessage.EXISTED_MOBILE_MSG);
                });
        // 邮箱是否已存在
        Optional.ofNullable(userMapper.selectExistedByEmail(userId, email))
                .ifPresent(sameEmailUser->{
                    throw new RetException(SecurityMessage.EXISTED_EMAIL_CODE, SecurityMessage.EXISTED_EMAIL_MSG);
                });
    }

    /**
     * 用户登录, 返回token
     * @param reqVo
     * @return
     */
    @Override
    public UserLoginRespVo login(UserLoginReqVo reqVo) {
        // 验参 : 用户存在且用户已启用
        User user = Optional.ofNullable(userMapper.selectByWord(reqVo.getUsername()))
                .filter(u -> u.getStatus().equals(YesNo.YES.getId()))
                .orElseThrow(()->new RetException(SecurityMessage.INVALID_USER_CODE, SecurityMessage.INVALID_USER_MSG));

        // 验参 : 密码正确
        String decryptPw = RsaUtil.decrypt(privateKey,reqVo.getPassword());
        if (StringUtil.isEmpty(decryptPw)) {
            throw new RetException(SecurityMessage.DECRYPT_FAILURE_CODE, SecurityMessage.DECRYPT_FAILURE_MSG);
        }
        String password = EncryptUtil.encryptPw(decryptPw);
        if (!user.getPassword().equals(password)) {
            throw new RetException(SecurityMessage.INCORRECT_PASSWORD_CODE, SecurityMessage.INCORRECT_PASSWORD_MSG);
        }

        // 缓存用户登录信息
        // 创建token
        String token = EncryptUtil.encryptToken(user.getId());

        // 创建登录信息
        Integer seconds = reqVo.getRememberMe().equals(YesNo.YES.getId()) ? REMEMBER_ME_LOGIN_SECONDS : COMMON_LOGIN_SECONDS;

        UserAccessDetail detail = new UserAccessDetail();
        detail.setUserId(user.getId());
        detail.setRememberMe(reqVo.getRememberMe());
        detail.setLoginExpireSeconds(seconds);

        // 创建缓存
        Cache<String, UserAccessDetail> cache = Caffeine.newBuilder()
                .expireAfterWrite(seconds, TimeUnit.SECONDS)
                .build();
        cache.put(token, detail);
        // 更新缓存
        Map<String, Cache<String, UserAccessDetail>> cacheMap = TokenUtil.getMap();
        cacheMap.put(token, cache);

        // 返回token
        UserLoginRespVo respVo = new UserLoginRespVo();
        respVo.setToken(token);
        return respVo;
    }

    /**
     * 根据token, GET方法请求账号信息
     * @return
     */
    @Override
    public UserInfoRespVo info() {
        User user = getUser(RestContext.getUserId());
        UserInfoRespVo respVo = new UserInfoRespVo();
        BeanUtils.copyProperties(user, respVo);
        // 状态名称
        if (UserStatus.ENABLED.getValue().equals(user.getStatus())) {
            respVo.setStatusName(UserStatus.ENABLED.getDescription());
        }
        if (UserStatus.DISABLED.getValue().equals(user.getStatus())) {
            respVo.setStatusName(UserStatus.DISABLED.getDescription());
        }
        return respVo;
    }

    /**
     * 编辑账号信息
     * @param reqVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editInfo(UserEditInfoReqVo reqVo) {
        // 验参 : 手机邮箱用户名格式正确, 且并未使用
        checkRegistrationParam(reqVo.getUsername(), reqVo.getMobile(), reqVo.getEmail(), RestContext.getUserId());
        // 验参 : 用户存在
        User user = getUser(RestContext.getUserId());

        user.setUsername(reqVo.getUsername());
        user.setMobile(reqVo.getMobile());
        user.setEmail(reqVo.getEmail());
        user.setUpdateDate(new Date());
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 修改密码
     * @param reqVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editPassword(UserEditPasswordReqVo reqVo) {
        // 验参 : 用户存在
        User user = getUser(RestContext.getUserId());
        // 验参 : 旧密码正确
        String decryptOldPw = RsaUtil.decrypt(privateKey, reqVo.getOldPassword());
        String encryptOldPw = EncryptUtil.encryptPw(decryptOldPw);
        if (!user.getPassword().equals(encryptOldPw)) {
            throw new RetException(SecurityMessage.INCORRECT_OLD_PASSWORD_CODE, SecurityMessage.INCORRECT_OLD_PASSWORD_MSG);
        }

        String decryptNewPw = RsaUtil.decrypt(privateKey, reqVo.getNewPassword());
        String encryptNewPw = EncryptUtil.encryptPw(decryptNewPw);
        user.setPassword(encryptNewPw);
        user.setUpdateDate(new Date());
        userMapper.updateByPrimaryKey(user);
    }

    /**
     * 根据userId获取user
     * @param userId
     * @return
     */
    private User getUser(Integer userId) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(userId))
                .orElseThrow(() -> new RetException(SecurityMessage.INVALID_USER_CODE, SecurityMessage.INVALID_USER_MSG));
    }

}
