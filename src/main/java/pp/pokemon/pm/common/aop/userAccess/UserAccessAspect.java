package pp.pokemon.pm.common.aop.userAccess;

import com.github.benmanes.caffeine.cache.Cache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.message.SecurityMessage;
import pp.pokemon.pm.common.util.security.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
@Order(value = -99)
public class UserAccessAspect {

    private final static String TOKEN_HEADER = "X-Auth-Token";

    /**
     * 根据x-auth-token获取用户登录信息, 并将用户id保存在RestContext中以备随时使用
     * @param point
     * @param userAccess
     */
    @Before("@annotation(userAccess)")
    public void beforeTest(JoinPoint point, UserAccess userAccess) {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        UserAccessDetail detail = getAccessDetail(request, userAccess.value());
        RestContext.put("userId", detail.getUserId());
    }

    private UserAccessDetail getAccessDetail(HttpServletRequest request, boolean strict) {
        // 获取token
        String token = request.getHeader(TOKEN_HEADER);

        // 通过tokenMap查询token是否过期
        Map<String, Cache<String, UserAccessDetail>> map = TokenUtil.getMap();
        Cache cache = map.get(token);
        if (null == cache && strict) {
            throw new RetException(SecurityMessage.INVALID_TOKEN_CODE, SecurityMessage.INVALID_TOKEN_MSG);
        }

        Object detail = cache.getIfPresent(token);
        if (null == detail) {
            throw new RetException("detail错误", SecurityMessage.INVALID_TOKEN_MSG);
        }
        return (UserAccessDetail) detail;
    }

    // TODO ticket: 防止重复调用关键接口
    // TODO @Around: token或权限不正确时不记录日志


    /**
     * 清理RestContext信息
     * @param userAccess
     */
    @After("@annotation(userAccess)")
    public void afterTest(UserAccess userAccess){
        RestContext.clear();
    }
}
