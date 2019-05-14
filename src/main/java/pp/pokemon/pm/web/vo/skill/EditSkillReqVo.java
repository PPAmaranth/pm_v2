package pp.pokemon.pm.web.vo.skill;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EditSkillReqVo {

    @NotNull(message = "技能id不能为空")
    private Integer id;

    @NotBlank(message = "技能名不能为空")
    @Length(max = 100, message = "技能名长度100")
    private String cnName;

    @Length(max = 100, message = "日文技能名长度100")
    private String jpName;

    @Length(max = 100, message = "英文技能名长度100")
    private String enName;

    @NotNull(message = "技能属性不为空")
    private Integer property;

    @NotNull(message = "技能分类不为空")
    private Integer classification;

    @NotNull(message = "技能威力不为空")
    private Integer power;

    @NotNull(message = "命中几率不为零")
    private Integer hitProbability;

    @NotNull(message = "pp不为空")
    private Integer pp;

    @NotNull(message = "是否技能机技能不为空")
    @Max(value = 1, message = "0否, 1是")
    @Min(value = 0, message = "0否, 1是")
    private Integer isMachineSkill;

    @Length(max = 200, message = "技能机编号长度200")
    private String machineSkillCode;
}
