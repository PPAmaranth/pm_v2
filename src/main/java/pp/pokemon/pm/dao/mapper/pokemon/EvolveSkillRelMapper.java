package pp.pokemon.pm.dao.mapper.pokemon;

import org.apache.ibatis.annotations.Param;
import pp.pokemon.pm.dao.entity.pokemon.EvolveSkillRel;
import pp.pokemon.pm.dao.vo.pokemon.EvolutionSkillRespVo;

import java.util.List;

public interface EvolveSkillRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(EvolveSkillRel record);

    int insertSelective(EvolveSkillRel record);

    EvolveSkillRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(EvolveSkillRel record);

    int updateByPrimaryKey(EvolveSkillRel record);

    List<EvolutionSkillRespVo> selectSkillsByPokemonId(@Param("pokemonId") Integer pokemonId);

    int deleteByPokemonId(@Param("pokemonId") Integer pokemonId);
}