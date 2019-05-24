package pp.pokemon.pm.service.user;

import pp.pokemon.pm.web.vo.user.UserLoginReqVo;
import pp.pokemon.pm.web.vo.user.UserLoginRespVo;
import pp.pokemon.pm.web.vo.user.UserRegistrationReqVo;

public interface UserService {

    void register(UserRegistrationReqVo reqVo);

    UserLoginRespVo login(UserLoginReqVo reqVo);

}
