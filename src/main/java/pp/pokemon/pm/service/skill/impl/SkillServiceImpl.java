package pp.pokemon.pm.service.skill.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.skill.SkillClassification;
import pp.pokemon.pm.common.message.SkillMessage;
import pp.pokemon.pm.dao.entity.pokemon.Property;
import pp.pokemon.pm.dao.entity.pokemon.Skill;
import pp.pokemon.pm.dao.mapper.pokemon.PropertyMapper;
import pp.pokemon.pm.dao.mapper.pokemon.SkillMapper;
import pp.pokemon.pm.dao.vo.skill.QuerySkillListReqVo;
import pp.pokemon.pm.dao.vo.skill.SimpleSkillListReqVo;
import pp.pokemon.pm.dao.vo.skill.SimpleSkillListRespVo;
import pp.pokemon.pm.service.skill.SkillService;
import pp.pokemon.pm.web.vo.skill.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private PropertyMapper propertyMapper;

    @Override
    public PageInfo<SkillListRespVo> list(QuerySkillListReqVo reqVo) {
        PageHelper.startPage(reqVo.getPageNum(), reqVo.getPageSize());
        List<Skill> skills = skillMapper.queryByParam(reqVo);
        PageInfo<Skill> skillPageInfo = new PageInfo<>(skills);

        List<SkillListRespVo> respVos = skills.stream()
                .map(skill -> {
                    SkillListRespVo respVo = new SkillListRespVo();
                    BeanUtils.copyProperties(skill, respVo);
                    // 属性名
                    respVo.setPropertyName(getProperty(skill.getProperty()));
                    // 分类名
                    respVo.setClassificationName(getClassification(skill.getClassification()));
                    return respVo;
                }).collect(Collectors.toList());
        PageInfo<SkillListRespVo> pageInfo = new PageInfo<>();
        BeanUtils.copyProperties(skillPageInfo, pageInfo);
        pageInfo.setList(respVos);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AddSkillReqVo reqVo) {
        Skill skill = new Skill();
        BeanUtils.copyProperties(reqVo, skill);
        skillMapper.insert(skill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(EditSkillReqVo reqVo) {
        Skill skill = getSkill(reqVo.getId());
        BeanUtils.copyProperties(reqVo, skill);
        skillMapper.updateByPrimaryKey(skill);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteSkillReqVo reqVo) {
        skillMapper.deleteByPrimaryKey(reqVo.getId());
    }

    @Override
    public SkillDetailRespVo detail(SkillDetailReqVo reqVo) {
        Skill skill = getSkill(reqVo.getId());
        SkillDetailRespVo respVo = new SkillDetailRespVo();
        BeanUtils.copyProperties(skill, respVo);
        return respVo;
    }

    @Override
    public PageInfo<SimpleSkillListRespVo> simpleList(SimpleSkillListReqVo reqVo) {
        PageHelper.startPage(reqVo.getPageNum(), reqVo.getPageSize());
        List<SimpleSkillListRespVo> respVos = skillMapper.simpleQueryByParam(reqVo);
        return new PageInfo<>(respVos);
    }


    /**
     * 根据skill_id获取skill
     */
    private Skill getSkill(Integer skillId) {
        return Optional.ofNullable(skillMapper.selectByPrimaryKey(skillId))
                .orElseThrow(() -> new RetException(SkillMessage.INVALID_SKILL_CODE, SkillMessage.INVALID_SKILL_MSG));
    }

    /**
     * 根据property_id获取name
     */
    private String getProperty(Integer propertyId) {
        return Optional.ofNullable(propertyMapper.selectByPrimaryKey(propertyId))
                .map(Property::getName)
                .orElse(null);
    }

    /**
     * 根据classification_id获取name
     */
    private String getClassification(Integer classificationId) {
        Map<Integer, String> map = SkillClassification.getMap();
        return map.get(classificationId);
    }
}
