package pp.pokemon.pm.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.common.aop.userAccess.UserAccess;
import pp.pokemon.pm.common.util.BeanValidators;
import pp.pokemon.pm.service.user.UserService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.web.vo.user.UserEditInfoReqVo;
import pp.pokemon.pm.web.vo.user.UserEditPasswordReqVo;
import pp.pokemon.pm.web.vo.user.UserLoginReqVo;
import pp.pokemon.pm.web.vo.user.UserRegistrationReqVo;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public DefaultApiResult register(@RequestBody UserRegistrationReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        userService.register(reqVo);
        return success();
    }

    /**
     * 用户登录, 登录成功返回token
     * @param reqVo
     * @return
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public DefaultApiResult login(@RequestBody UserLoginReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        return success(userService.login(reqVo));
    }

    /**
     * get方法, 根据token获取账号信息
     * @return
     */
    @UserAccess
    @RequestMapping(value = "/info", method = {RequestMethod.GET})
    public DefaultApiResult info() {
        return success(userService.info());
    }

    /**
     * 修改账号信息
     * @param reqVo
     * @return
     */
    @UserAccess
    @RequestMapping(value = "/editInfo", method = {RequestMethod.POST})
    public DefaultApiResult editInfo(@RequestBody UserEditInfoReqVo reqVo){
         BeanValidators.validateWithParameterException(validator, reqVo);
         userService.editInfo(reqVo);
         return success();
    }

    /**
     * 修改密码
     * @param reqVo
     * @return
     */
    @UserAccess
    @RequestMapping(value = "/editPassword", method = {RequestMethod.POST})
    public DefaultApiResult editPassword(@RequestBody UserEditPasswordReqVo reqVo){
        BeanValidators.validateWithParameterException(validator, reqVo);
        userService.editPassword(reqVo);
        return success();
    }
}


