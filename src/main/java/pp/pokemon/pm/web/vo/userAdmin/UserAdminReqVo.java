package pp.pokemon.pm.web.vo.userAdmin;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserAdminReqVo {

    @NotNull(message = "用户id不能为空")
    private Integer userId;

}
