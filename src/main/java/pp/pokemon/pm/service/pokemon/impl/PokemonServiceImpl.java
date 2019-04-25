package pp.pokemon.pm.service.pokemon.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pp.pokemon.pm.dao.entity.pokemon.PkmPokemon;
import pp.pokemon.pm.dao.mapper.pokemon.PkmPokemonMapper;
import pp.pokemon.pm.service.pokemon.PokemonService;
import pp.pokemon.pm.web.vo.pokemon.BatchInsertPokemonVo;
import pp.pokemon.pm.web.vo.pokemon.QueryAllReqWithPageVo;

import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Autowired
    private PkmPokemonMapper pkmPokemonMapper;

    @Override
    public PageInfo<PkmPokemon> queryAll(QueryAllReqWithPageVo reqVo) {
        PageHelper.startPage(reqVo.getPageNum(), reqVo.getPageSize());
        List<PkmPokemon> list = pkmPokemonMapper.queryAll(reqVo);
        PageInfo<PkmPokemon> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public void batchInsertPokemon(List<BatchInsertPokemonVo> pokemons) {
        if(!CollectionUtils.isEmpty(pokemons)) {
            pokemons.stream().forEach(pokemon -> {
                PkmPokemon pkmPokemon = new PkmPokemon();
                BeanUtils.copyProperties(pokemon, pkmPokemon);
                // 特殊攻击&防御
                pkmPokemon.setsAttack(pokemon.getS_attack());
                pkmPokemon.setsDefense(pokemon.getS_defense());
                pkmPokemon.setEthnicValue(pokemon.getEthnic_value());
                // 属性
                if (!CollectionUtils.isEmpty(pokemon.getProperty())) {
                    pkmPokemon.setPropertyOne(pokemon.getProperty().get(0).getId());
                    if (pokemon.getProperty().size() > 1) {
                        pkmPokemon.setPropertyTwo(pokemon.getProperty().get(1).getId());
                    }
                }
                pkmPokemonMapper.insert(pkmPokemon);
            });
        }
    }
}
