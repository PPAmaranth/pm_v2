package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.Skill;

public interface SkillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Skill record);

    int insertSelective(Skill record);

    Skill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Skill record);

    int updateByPrimaryKey(Skill record);
}