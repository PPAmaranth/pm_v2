package pp.pokemon.pm.web.vo.skill;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteSkillReqVo {

    @NotNull(message = "技能id不能为空")
    private Integer id;

}
