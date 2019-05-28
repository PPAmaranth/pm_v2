package pp.pokemon.pm.service.user;

import pp.pokemon.pm.web.vo.user.*;

public interface UserService {

    void register(UserRegistrationReqVo reqVo);

    UserLoginRespVo login(UserLoginReqVo reqVo);

    UserInfoRespVo info();

    void editInfo(UserEditInfoReqVo reqVo);

    void editPassword(UserEditPasswordReqVo reqVo);
}
