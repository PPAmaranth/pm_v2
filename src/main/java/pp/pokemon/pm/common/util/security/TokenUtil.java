package pp.pokemon.pm.common.util.security;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Component;
import pp.pokemon.pm.common.aop.userAccess.UserAccessDetail;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    /**
     * 服务器登录账号缓存
     */
    private static Map<String, Cache<String, UserAccessDetail>> map;

    public static Map<String, Cache<String, UserAccessDetail>> getMap() {
        if (null == map) {
            map = new HashMap<>();
        }
        return map;
    }
}
