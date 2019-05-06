package pp.pokemon.pm.dao.mapper.pokemon;

import org.apache.ibatis.annotations.Param;
import pp.pokemon.pm.dao.entity.pokemon.MachineSkillRel;
import pp.pokemon.pm.dao.vo.pokemon.MachineSkillRespVo;

import java.util.List;

public interface MachineSkillRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MachineSkillRel record);

    int insertSelective(MachineSkillRel record);

    MachineSkillRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MachineSkillRel record);

    int updateByPrimaryKey(MachineSkillRel record);

    List<MachineSkillRespVo> selectSkillsByPokemonId(@Param("pokemonId") Integer pokemonId);
}