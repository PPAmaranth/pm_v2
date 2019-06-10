package pp.pokemon.pm.service.user;

import pp.pokemon.pm.web.vo.userAdmin.UserAdminReqVo;

public interface UserAdminService {
    void enable(UserAdminReqVo reqVo);

    void disable(UserAdminReqVo reqVo);
}
