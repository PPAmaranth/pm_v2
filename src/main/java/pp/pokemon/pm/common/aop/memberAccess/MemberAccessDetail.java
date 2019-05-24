package pp.pokemon.pm.common.aop.memberAccess;

import lombok.Data;

@Data
public class MemberAccessDetail {

    private Integer userId;

    private Integer loginExpireSeconds;

    private Integer rememberMe;

}
