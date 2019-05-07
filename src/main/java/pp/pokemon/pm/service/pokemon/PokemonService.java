package pp.pokemon.pm.service.pokemon;

import com.github.pagehelper.PageInfo;
import pp.pokemon.pm.dao.vo.file.BatchInsertPokemonVo;
import pp.pokemon.pm.dao.vo.pokemon.*;
import pp.pokemon.pm.web.vo.pokemon.*;

import java.util.List;

public interface PokemonService {

    PageInfo<QueryPokemonRespVo> pokemonList(QueryAllPokemonReqVo reqVo);

    void batchInsertPokemon(List<BatchInsertPokemonVo> pokemons);

    List<PokemonAttachmentRespVo> getPokemonAttachment(PokemonAttachmentReqVo reqVo);

    void add(AddPokemonReqVo reqVo);

    PokemonDetailRespVo detail(PokemonDetailReqVo reqVo);

    void edit(EditPokemonReqVo reqVo);

    void delete(PokemonDeleteReqVo reqVo);
}
