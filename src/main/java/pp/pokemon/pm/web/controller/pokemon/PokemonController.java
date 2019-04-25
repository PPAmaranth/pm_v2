package pp.pokemon.pm.web.controller.pokemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.service.pokemon.PokemonService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.web.vo.pokemon.BatchInsertPokemonVo;
import pp.pokemon.pm.web.vo.pokemon.QueryAllReqWithPageVo;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
public class PokemonController extends BaseController {

    @Autowired
    private PokemonService pokemonService;

    @RequestMapping(value = "/queryAll", method = {RequestMethod.POST})
    public DefaultApiResult queryAll(@RequestBody QueryAllReqWithPageVo reqVo){
        return success(pokemonService.queryAll(reqVo));
    }

    @RequestMapping(value = "/batchInsertPokemon", method = {RequestMethod.POST})
    public DefaultApiResult batchInsertPokemon(@RequestBody List<BatchInsertPokemonVo> pokemons){
        pokemonService.batchInsertPokemon(pokemons);
        return success();
    }
}
