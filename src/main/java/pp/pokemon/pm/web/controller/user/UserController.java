package pp.pokemon.pm.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.common.util.BeanValidators;
import pp.pokemon.pm.service.user.UserService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.web.vo.user.UserLoginReqVo;
import pp.pokemon.pm.web.vo.user.UserRegistrationReqVo;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public DefaultApiResult register(@RequestBody UserRegistrationReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        userService.register(reqVo);
        return success();
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public DefaultApiResult login(@RequestBody UserLoginReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        return success(userService.login(reqVo));
    }
}
