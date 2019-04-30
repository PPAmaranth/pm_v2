package pp.pokemon.pm.service.pokemon;

import com.github.pagehelper.PageInfo;
import pp.pokemon.pm.dao.vo.file.BatchInsertPokemonVo;
import pp.pokemon.pm.dao.vo.pokemon.QueryAllPokemonReqVo;
import pp.pokemon.pm.dao.vo.pokemon.PokemonAttachmentReqVo;
import pp.pokemon.pm.web.vo.pokemon.PokemonAttachmentRespVo;
import pp.pokemon.pm.web.vo.pokemon.QueryPokemonRespVo;

import java.util.List;

public interface PokemonService {
    PageInfo<QueryPokemonRespVo> pokemonList(QueryAllPokemonReqVo reqVo);

    void batchInsertPokemon(List<BatchInsertPokemonVo> pokemons);

    List<PokemonAttachmentRespVo> getPokemonAttachment(PokemonAttachmentReqVo reqVo);
}
