package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.pkmAbility;

public interface pkmAbilityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(pkmAbility record);

    int insertSelective(pkmAbility record);

    pkmAbility selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(pkmAbility record);

    int updateByPrimaryKey(pkmAbility record);
}