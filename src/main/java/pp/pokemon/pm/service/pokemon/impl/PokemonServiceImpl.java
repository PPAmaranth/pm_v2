package pp.pokemon.pm.service.pokemon.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pp.pokemon.pm.dao.entity.pokemon.PkmPokemon;
import pp.pokemon.pm.dao.mapper.pokemon.PkmPokemonMapper;
import pp.pokemon.pm.service.pokemon.PokemonService;
import pp.pokemon.pm.web.vo.pokemon.QueryAllReqVo;

import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Autowired
    private PkmPokemonMapper pkmPokemonMapper;

    @Override
    public PageInfo<PkmPokemon> queryAll(QueryAllReqVo reqVo) {
        PageHelper.startPage(reqVo.getPageNum(), reqVo.getPageSize());
        List<PkmPokemon> list = pkmPokemonMapper.queryAll(reqVo);
        PageInfo<PkmPokemon> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
