package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.Pokemon;
import pp.pokemon.pm.dao.vo.pokemon.QueryAllPokemonReqVo;

import java.util.List;

public interface PokemonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Pokemon record);

    int insertSelective(Pokemon record);

    Pokemon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Pokemon record);

    int updateByPrimaryKey(Pokemon record);

    List<Pokemon> queryByParam(QueryAllPokemonReqVo reqVo);
}