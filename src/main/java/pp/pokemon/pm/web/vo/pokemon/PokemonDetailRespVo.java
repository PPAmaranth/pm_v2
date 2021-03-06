package pp.pokemon.pm.web.vo.pokemon;

import lombok.Data;
import pp.pokemon.pm.dao.vo.pokemon.EvolutionSkillRespVo;
import pp.pokemon.pm.dao.vo.pokemon.MachineSkillRespVo;

import java.util.List;

@Data
public class PokemonDetailRespVo {

    private Integer id;

    private Integer illustrationBookId;

    private String cnName;

    private String jpName;

    private String enName;

    private Integer hp;

    private Integer attack;

    private Integer defense;

    private Integer sAttack;

    private Integer sDefense;

    private Integer propertyOne;

    private String propertyOneName;

    private Integer propertyTwo;

    private String propertyTwoName;

    private Integer speed;

    private Integer ethnicValue;

    private String imgUrl;

    // 进化前精灵id
    private Integer beforeId;

    // 进化条件
    private String condition;

    private List<EvolutionRelationshipRespVo> evolutionRelationship;

    private List<EvolutionSkillRespVo> evolutionSkills;

    private List<MachineSkillRespVo> machineSkills;
}
