package pp.pokemon.pm.dao.vo.pokemon;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EvolutionSkillVo {

    @NotNull(message = "技能id不能为空")
    private Integer id;

    @NotNull(message = "技能学习等级不能为空")
    private Integer level;
}
