package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.EvolveSkillRel;

public interface EvolveSkillRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EvolveSkillRel record);

    int insertSelective(EvolveSkillRel record);

    EvolveSkillRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EvolveSkillRel record);

    int updateByPrimaryKey(EvolveSkillRel record);
}