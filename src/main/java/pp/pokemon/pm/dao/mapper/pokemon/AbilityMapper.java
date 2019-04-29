package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.Ability;

public interface AbilityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Ability record);

    int insertSelective(Ability record);

    Ability selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Ability record);

    int updateByPrimaryKey(Ability record);
}