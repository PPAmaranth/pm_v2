package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.PkmSkill;

public interface PkmSkillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PkmSkill record);

    int insertSelective(PkmSkill record);

    PkmSkill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PkmSkill record);

    int updateByPrimaryKey(PkmSkill record);
}