package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.pkmPokemon;

public interface pkmPokemonMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(pkmPokemon record);

    int insertSelective(pkmPokemon record);

    pkmPokemon selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(pkmPokemon record);

    int updateByPrimaryKey(pkmPokemon record);
}