package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.PkmProperty;

public interface PkmPropertyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PkmProperty record);

    int insertSelective(PkmProperty record);

    PkmProperty selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PkmProperty record);

    int updateByPrimaryKey(PkmProperty record);
}