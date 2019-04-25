package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.PkmPokemon;
import pp.pokemon.pm.web.vo.pokemon.QueryAllReqWithPageVo;

import java.util.List;

public interface PkmPokemonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PkmPokemon record);

    int insertSelective(PkmPokemon record);

    PkmPokemon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PkmPokemon record);

    int updateByPrimaryKey(PkmPokemon record);

    List<PkmPokemon> queryAll(QueryAllReqWithPageVo reqVo);
}