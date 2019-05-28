package pp.pokemon.pm.common.aop.userAccess;

import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.message.SecurityMessage;

import java.util.HashMap;
import java.util.Map;

public class RestContext {

    public final static String USER_ID = "userId";

    public static Integer getUserId() {
        Object userId = get(USER_ID);
        if (null == userId) {
            throw new RetException(SecurityMessage.INVALID_ACCESS_CODE, SecurityMessage.INVALID_ACCESS_MSG);
        }
        return (Integer) userId;
    }

    private static ThreadLocal<RestContext> current = new ThreadLocal<>();

    private Map<String, Object> map = new HashMap<>();

    public static RestContext current(){
        if (null == current.get()) {
            current.set(new RestContext());
        }
        return current.get();
    }

    public static void put(String key, Object value) {
        current().map.put(key, value);
    }

    public static Object get(String key) {
        return current().map.get(key);
    }

    public static void remove(String remove) {
        current().map.remove(remove);
    }

    /***
     * threadLocal.set(null)会调用threadlocal类的清除空value方法, 实现无效key值的清理
     */
    public static void clear() {
        if (null == current.get()) {
            current.set(null);
        }
    }

}
