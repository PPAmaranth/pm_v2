package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.PkmAbility;

public interface PkmAbilityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PkmAbility record);

    int insertSelective(PkmAbility record);

    PkmAbility selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PkmAbility record);

    int updateByPrimaryKey(PkmAbility record);
}