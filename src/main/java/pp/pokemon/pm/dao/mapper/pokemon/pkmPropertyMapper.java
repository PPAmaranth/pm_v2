package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.pkmProperty;

public interface pkmPropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(pkmProperty record);

    int insertSelective(pkmProperty record);

    pkmProperty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(pkmProperty record);

    int updateByPrimaryKey(pkmProperty record);
}