package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.pkmSkill;

public interface pkmSkillMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(pkmSkill record);

    int insertSelective(pkmSkill record);

    pkmSkill selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(pkmSkill record);

    int updateByPrimaryKey(pkmSkill record);
}