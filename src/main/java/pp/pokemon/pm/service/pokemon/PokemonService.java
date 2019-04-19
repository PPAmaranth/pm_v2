package pp.pokemon.pm.service.pokemon;

import com.github.pagehelper.PageInfo;
import pp.pokemon.pm.dao.entity.pokemon.PkmPokemon;
import pp.pokemon.pm.web.vo.pokemon.BatchInsertPokemonVo;
import pp.pokemon.pm.web.vo.pokemon.QueryAllReqVo;

import java.util.List;

public interface PokemonService {
    PageInfo<PkmPokemon> queryAll(QueryAllReqVo reqVo);

    void batchInsertPokemon(List<BatchInsertPokemonVo> pokemons);
}