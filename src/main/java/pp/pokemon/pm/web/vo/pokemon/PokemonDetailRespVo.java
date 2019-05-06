package pp.pokemon.pm.web.vo.pokemon;

import lombok.Data;
import pp.pokemon.pm.dao.vo.pokemon.EvolutionSkillRespVo;
import pp.pokemon.pm.dao.vo.pokemon.MachineSkillRespVo;
import pp.pokemon.pm.web.vo.pokemon.EvolutionRelationshipRespVo;

import java.util.List;

@Data
public class PokemonDetailRespVo {

    private Integer id;

    private Integer illustrationBookId;

    private String name;

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

    private List<EvolutionRelationshipRespVo> evolutionRelationshipRespVos;

    private List<EvolutionSkillRespVo> evolutionSkillRespVos;

    private List<MachineSkillRespVo> machineSkillRespVos;
}
