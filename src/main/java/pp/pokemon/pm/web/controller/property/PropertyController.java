package pp.pokemon.pm.web.controller.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.common.util.BeanValidators;
import pp.pokemon.pm.dao.vo.property.PropertyListReqVo;
import pp.pokemon.pm.service.property.PropertyService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.web.vo.property.BatchAddPropertyReqVo;
import pp.pokemon.pm.web.vo.property.PropertyDetailReqVo;

import java.util.List;

@RestController
@RequestMapping("/property")
public class PropertyController extends BaseController {

    @Autowired
    private PropertyService propertyService;

    @RequestMapping(value = "/batchAddProperty", method = {RequestMethod.POST})
    public DefaultApiResult batchAddProperty(@RequestBody List<BatchAddPropertyReqVo> reqVos) {
        BeanValidators.validateWithParameterException(validator, reqVos);
        propertyService.batchAddProperty(reqVos);
        return success();
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public DefaultApiResult list(@RequestBody PropertyListReqVo reqVo) {
        return success(propertyService.list(reqVo));
    }

    @RequestMapping(value = "/detail", method = {RequestMethod.POST})
    public DefaultApiResult detail(@RequestBody PropertyDetailReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        return success(propertyService.detail(reqVo));
    }
}
