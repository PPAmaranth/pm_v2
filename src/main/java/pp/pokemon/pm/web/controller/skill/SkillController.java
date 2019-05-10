package pp.pokemon.pm.web.controller.skill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.common.util.BeanValidators;
import pp.pokemon.pm.dao.vo.skill.BatchAddSkillReqVo;
import pp.pokemon.pm.dao.vo.skill.QuerySkillListReqVo;
import pp.pokemon.pm.dao.vo.skill.SimpleSkillListReqVo;
import pp.pokemon.pm.service.skill.SkillService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.web.vo.skill.*;

import java.util.List;

@RestController
@RequestMapping("/skill")
public class SkillController extends BaseController {

    @Autowired
    private SkillService skillService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST})
    public DefaultApiResult list(@RequestBody QuerySkillListReqVo reqVo) {
        return success(skillService.list(reqVo));
    }

    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public DefaultApiResult add(@RequestBody AddSkillReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        skillService.add(reqVo);
        return success();
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public DefaultApiResult edit(@RequestBody EditSkillReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        skillService.edit(reqVo);
        return success();
    }

    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public DefaultApiResult delete(@RequestBody DeleteSkillReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        skillService.delete(reqVo);
        return success();
    }

    @RequestMapping(value = "/detail", method = {RequestMethod.POST})
    public DefaultApiResult detail(@RequestBody SkillDetailReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        return success(skillService.detail(reqVo));
    }

    @RequestMapping(value = "/simpleList", method = {RequestMethod.POST})
    public DefaultApiResult simpleList(@RequestBody SimpleSkillListReqVo reqVo){
        return success(skillService.simpleList(reqVo));
    }

    @RequestMapping(value = "batchAdd", method = {RequestMethod.POST})
    public DefaultApiResult batchAdd(@RequestBody List<BatchAddSkillReqVo> reqVos) {
        BeanValidators.validateWithParameterException(validator, reqVos);
        skillService.batchAdd(reqVos);
        return success();
    }
}
