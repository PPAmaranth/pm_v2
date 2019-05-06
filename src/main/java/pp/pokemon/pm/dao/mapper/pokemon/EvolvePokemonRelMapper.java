package pp.pokemon.pm.dao.mapper.pokemon;

import org.apache.ibatis.annotations.Param;
import pp.pokemon.pm.dao.entity.pokemon.EvolvePokemonRel;

public interface EvolvePokemonRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EvolvePokemonRel record);

    int insertSelective(EvolvePokemonRel record);

    EvolvePokemonRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EvolvePokemonRel record);

    int updateByPrimaryKey(EvolvePokemonRel record);

    EvolvePokemonRel selectByPokemonId(@Param("pokemonId")Integer pokemonId);

    EvolvePokemonRel selectByBeforeId(@Param("beforeId")Integer beforeId);
}