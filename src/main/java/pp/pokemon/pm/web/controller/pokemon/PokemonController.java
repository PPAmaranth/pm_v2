package pp.pokemon.pm.web.controller.pokemon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.common.util.BeanValidators;
import pp.pokemon.pm.dao.vo.pokemon.InsertPokemonReqVo;
import pp.pokemon.pm.dao.vo.pokemon.PokemonDetailReqVo;
import pp.pokemon.pm.service.pokemon.PokemonService;
import pp.pokemon.pm.web.controller.BaseController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;
import pp.pokemon.pm.dao.vo.file.BatchInsertPokemonVo;
import pp.pokemon.pm.dao.vo.pokemon.QueryAllPokemonReqVo;
import pp.pokemon.pm.dao.vo.pokemon.PokemonAttachmentReqVo;

import java.util.List;

@RestController
@RequestMapping("/pokemon")
public class PokemonController extends BaseController {

    @Autowired
    private PokemonService pokemonService;

    @RequestMapping(value = "/pokemonList", method = {RequestMethod.POST})
    public DefaultApiResult queryAll(@RequestBody QueryAllPokemonReqVo reqVo){
        return success(pokemonService.pokemonList(reqVo));
    }

    @RequestMapping(value = "/batchInsertPokemon", method = {RequestMethod.POST})
    public DefaultApiResult batchInsertPokemon(@RequestBody List<BatchInsertPokemonVo> pokemons){
        pokemonService.batchInsertPokemon(pokemons);
        return success();
    }

    @RequestMapping(value = "/getPokemonAttachment", method = {RequestMethod.POST})
    public DefaultApiResult getPokemonAttachment(@RequestBody PokemonAttachmentReqVo reqVo) {
        return success(pokemonService.getPokemonAttachment(reqVo));
    }

    @RequestMapping(value = "/insert", method = {RequestMethod.POST})
    public DefaultApiResult insert(@RequestBody InsertPokemonReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        pokemonService.insert(reqVo);
        return success();
    }

    @RequestMapping(value = "/detail", method = {RequestMethod.POST})
    public DefaultApiResult detail(@RequestBody PokemonDetailReqVo reqVo) {
        BeanValidators.validateWithParameterException(validator, reqVo);
        return success(pokemonService.detail(reqVo));
    }
}
