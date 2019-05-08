package pp.pokemon.pm.dao.vo.skill;

import lombok.Data;
import pp.pokemon.pm.web.vo.base.BaseReqWithPageVo;

@Data
public class QuerySkillListReqVo extends BaseReqWithPageVo {

    private String word;

    private Integer property;

    private Integer classification;

    private Integer isMachineSkill;

}
