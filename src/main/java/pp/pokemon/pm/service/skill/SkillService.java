package pp.pokemon.pm.service.skill;

import com.github.pagehelper.PageInfo;
import pp.pokemon.pm.dao.vo.skill.QuerySkillListReqVo;
import pp.pokemon.pm.dao.vo.skill.SimpleSkillListReqVo;
import pp.pokemon.pm.dao.vo.skill.SimpleSkillListRespVo;
import pp.pokemon.pm.web.vo.skill.*;

public interface SkillService {
    PageInfo<SkillListRespVo> list(QuerySkillListReqVo reqVo);

    void add(AddSkillReqVo reqVo);

    void edit(EditSkillReqVo reqVo);

    void delete(DeleteSkillReqVo reqVo);

    SkillDetailRespVo detail(SkillDetailReqVo reqVo);

    PageInfo<SimpleSkillListRespVo> simpleList(SimpleSkillListReqVo reqVo);
}
