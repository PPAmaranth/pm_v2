package pp.pokemon.pm.common.aop.memberAccess;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface MemberAccess {

    /**
     * 登录严格模式, 默认为true, token不存在则抛出错误; false时无token也可以调用接口
     */
    boolean value() default true;

}
