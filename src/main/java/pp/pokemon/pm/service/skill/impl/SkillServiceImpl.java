package pp.pokemon.pm.service.skill.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.enums.common.YesNo;
import pp.pokemon.pm.common.enums.skill.SkillClassification;
import pp.pokemon.pm.common.message.SkillMessage;
import pp.pokemon.pm.common.util.collection.CollectionUtil;
import pp.pokemon.pm.dao.entity.pokemon.Property;
import pp.pokemon.pm.dao.entity.pokemon.Skill;
import pp.pokemon.pm.dao.mapper.pokemon.PropertyMapper;
import pp.pokemon.pm.dao.mapper.pokemon.SkillMapper;
import pp.pokemon.pm.dao.vo.property.PropertyListReqVo;
import pp.pokemon.pm.dao.vo.skill.BatchAddSkillReqVo;
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
    public Skill add(AddSkillReqVo reqVo) {
        Skill skill = new Skill();
        BeanUtils.copyProperties(reqVo, skill);
        skillMapper.insert(skill);
        return skill;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Skill edit(EditSkillReqVo reqVo) {
        Skill skill = getSkill(reqVo.getId());
        BeanUtils.copyProperties(reqVo, skill);
        skillMapper.updateByPrimaryKey(skill);
        return skill;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(DeleteSkillReqVo reqVo) {
        // 验参 : 技能存在
        Skill skill = getSkill(reqVo.getId());
        skillMapper.deleteByPrimaryKey(skill.getId());
    }

    @Override
    public SkillDetailRespVo detail(SkillDetailReqVo reqVo) {
        Skill skill = getSkillWithoutValidation(reqVo.getId());
        SkillDetailRespVo respVo = new SkillDetailRespVo();
        BeanUtils.copyProperties(skill, respVo);
        // 属性名
        respVo.setPropertyName(getProperty(skill.getProperty()));
        // 分类名
        respVo.setClassificationName(getClassification(skill.getClassification()));
        return respVo;
    }

    @Override
    public PageInfo<SimpleSkillListRespVo> simpleList(SimpleSkillListReqVo reqVo) {
        PageHelper.startPage(reqVo.getPageNum(), reqVo.getPageSize());
        List<SimpleSkillListRespVo> respVos = skillMapper.simpleQueryByParam(reqVo);
        return new PageInfo<>(respVos);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(List<BatchAddSkillReqVo> reqVos) {
        List<Property> properties = propertyMapper.selectByParam(new PropertyListReqVo());

        if (CollectionUtil.isNotEmpty(reqVos)) {
            reqVos.stream().forEach(reqVo -> {
                Skill skill = new Skill();
                skill.setCnName(reqVo.getCn_name());
                skill.setJpName(reqVo.getJp_name());
                skill.setEnName(reqVo.getEn_name());
                // property
                Integer property = properties.stream()
                        .filter(prop -> prop.getName().equals(reqVo.getPropery()))
                        .map(Property::getId)
                        .findAny()
                        .orElse(null);
                skill.setProperty(property);
                // classification
                if (SkillClassification.PHYSICAL.getName().equals(reqVo.getClassfication())) {
                    skill.setClassification(SkillClassification.PHYSICAL.getId());
                }
                if (SkillClassification.SPECIAL.getName().equals(reqVo.getClassfication())) {
                    skill.setClassification(SkillClassification.SPECIAL.getId());
                }
                if (SkillClassification.TRANSFORMATIONAL.getName().equals(reqVo.getClassfication())) {
                    skill.setClassification(SkillClassification.TRANSFORMATIONAL.getId());
                }
                // 其他属性
                skill.setPower(reqVo.getPower());
                skill.setHitProbability(reqVo.getHit_probability());
                skill.setPp(reqVo.getPp());
                // 默认非技能机技能
                skill.setIsMachineSkill(YesNo.NO.getId());
                skillMapper.insert(skill);
            });
        }
    }


    /**
     * 根据skill_id获取skill, 如果skill不存在则抛出错误
     */
    private Skill getSkill(Integer skillId) {
        return Optional.ofNullable(skillMapper.selectByPrimaryKey(skillId))
                .orElseThrow(() -> new RetException(SkillMessage.INVALID_SKILL_CODE, SkillMessage.INVALID_SKILL_MSG));
    }

    /**
     * 根据skill_id获取skill, 如果skill_id为null则返回空skill对象, 如果skill不存在则抛出错误
     */
    private Skill getSkillWithoutValidation(Integer skillId) {
        if (null == skillId) {
            return new Skill();
        }
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
