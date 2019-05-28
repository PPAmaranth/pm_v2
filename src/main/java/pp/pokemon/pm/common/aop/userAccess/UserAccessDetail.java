package pp.pokemon.pm.common.aop.userAccess;

import lombok.Data;

@Data
public class UserAccessDetail {

    private Integer userId;

    private Integer loginExpireSeconds;

    private Integer rememberMe;

}
