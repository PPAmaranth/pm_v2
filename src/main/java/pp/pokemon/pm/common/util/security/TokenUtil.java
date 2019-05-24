package pp.pokemon.pm.common.util.security;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Component;
import pp.pokemon.pm.common.aop.memberAccess.MemberAccessDetail;

import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private static Map<String, Cache<String, MemberAccessDetail>> map;

    public static Map<String, Cache<String, MemberAccessDetail>> getMap() {
        if (null == map) {
            map = new HashMap<>();
        }
        return map;
    }
}
