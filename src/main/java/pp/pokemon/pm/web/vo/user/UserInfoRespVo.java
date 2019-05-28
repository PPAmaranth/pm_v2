package pp.pokemon.pm.web.vo.user;

import lombok.Data;

@Data
public class UserInfoRespVo {

    private String username;

    private String mobile;

    private String email;

    private Integer status;

    private String statusName;

}
