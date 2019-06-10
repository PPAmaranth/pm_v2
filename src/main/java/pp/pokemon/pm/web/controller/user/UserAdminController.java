package pp.pokemon.pm.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.common.util.BeanValidators;
import pp.pokemon.pm.service.user.UserAdminService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.web.vo.userAdmin.UserAdminReqVo;

@RestController
@RequestMapping(value = "/userAdmin")
public class UserAdminController extends BaseController {

    @Autowired
    private UserAdminService userAdminService;

    @RequestMapping(value = "/enable", method = {RequestMethod.POST})
    public DefaultApiResult enable(@RequestBody UserAdminReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        userAdminService.enable(reqVo);
        return success();
    }

    @RequestMapping(value = "/disable", method = {RequestMethod.POST})
    public DefaultApiResult disable(@RequestBody UserAdminReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        userAdminService.disable(reqVo);
        return success();
    }


}
