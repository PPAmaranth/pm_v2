package pp.pokemon.pm.web.vo.pokemon;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class EditPokemonReqVo {

    @NotNull(message = "精灵id不为空")
    private Integer id;

    private Integer illustrationBookId;

    @NotBlank(message = "精灵名称不能为空")
    private String name;

    private String jpName;

    private String enName;

    @NotNull(message = "hp不能为空")
    private Integer hp;

    @NotNull(message = "攻击力不能为空")
    private Integer attack;

    @NotNull(message = "防御力不能为空")
    private Integer defense;

    @NotNull(message = "特殊攻击力不能为空")
    private Integer sAttack;

    @NotNull(message = "特殊防御力不能为空")
    private Integer sDefense;

    @NotNull(message = "属性1不能为空")
    private Integer propertyOne;

    private Integer propertyTwo;

    @NotNull(message = "速度不能为空")
    private Integer speed;

    @NotNull(message = "种族值不能为空")
    private Integer ethnicValue;

    private EvolutionRelationshipReqVo evolutionRelationship;

    private List<EvolutionSkillReqVo> evolutionSkills;

    @Size(max = 50, message = "技能机技能数量最多50")
    private List<MachineSkillReqVo> machineSkills;
}
