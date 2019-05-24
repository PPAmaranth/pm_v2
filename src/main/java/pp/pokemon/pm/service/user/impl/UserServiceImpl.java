package pp.pokemon.pm.service.user.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.pokemon.pm.common.aop.memberAccess.MemberAccessDetail;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.common.YesNo;
import pp.pokemon.pm.common.message.SecurityMessage;
import pp.pokemon.pm.common.util.security.EncryptUtil;
import pp.pokemon.pm.common.util.security.RsaUtil;
import pp.pokemon.pm.common.util.security.TokenUtil;
import pp.pokemon.pm.dao.entity.user.User;
import pp.pokemon.pm.dao.mapper.user.UserMapper;
import pp.pokemon.pm.service.user.UserService;
import pp.pokemon.pm.web.vo.user.UserLoginReqVo;
import pp.pokemon.pm.web.vo.user.UserLoginRespVo;
import pp.pokemon.pm.web.vo.user.UserRegistrationReqVo;

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
        user.setStatus(YesNo.YES.getId());
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

        MemberAccessDetail detail = new MemberAccessDetail();
        detail.setUserId(user.getId());
        detail.setRememberMe(reqVo.getRememberMe());
        detail.setLoginExpireSeconds(seconds);

        // 创建缓存
        Cache<String, MemberAccessDetail> cache = Caffeine.newBuilder()
                .expireAfterWrite(seconds, TimeUnit.SECONDS)
                .build();
        cache.put(token, detail);
        // 更新缓存
        Map<String, Cache<String, MemberAccessDetail>> cacheMap = TokenUtil.getMap();
        cacheMap.put(token, cache);

        // 返回token
        UserLoginRespVo respVo = new UserLoginRespVo();
        respVo.setToken(token);
        return respVo;
    }

}
