package pp.pokemon.pm.web.vo.skill;

import lombok.Data;


@Data
public class SkillDetailRespVo {

    private Integer id;

    private String cnName;

    private String jpName;

    private String enName;

    private Integer property;

    private String propertyName;

    private Integer classification;

    private String classificationName;

    private String power;

    private String hitProbability;

    private String pp;

    private String description;

    private Integer isMachineSkill;

    private String machineSkillCode;
}
