package pp.pokemon.pm.web.vo.skill;

import lombok.Data;

@Data
public class SkillListRespVo {

    private Integer id;

    private String cnName;

    private String jpName;

    private String enName;

    private Integer property;

    private String propertyName;

    private Integer classification;

    private String classificationName;

    private Integer power;

    private Integer hitProbability;

    private Integer pp;

    private Integer isMachineSkill;

    private String machineSkillCode;

}
