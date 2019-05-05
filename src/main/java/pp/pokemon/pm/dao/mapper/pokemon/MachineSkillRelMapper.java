package pp.pokemon.pm.dao.mapper.pokemon;

import pp.pokemon.pm.dao.entity.pokemon.MachineSkillRel;

public interface MachineSkillRelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MachineSkillRel record);

    int insertSelective(MachineSkillRel record);

    MachineSkillRel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MachineSkillRel record);

    int updateByPrimaryKey(MachineSkillRel record);
}