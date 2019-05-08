package pp.pokemon.pm.web.vo.skill;

import lombok.Data;


@Data
public class SkillDetailRespVo {

    private Integer id;

    private String cnName;

    private String jpName;

    private String enName;

    private Integer property;

    private Integer classification;

    private Integer power;

    private Integer hitProbability;

    private Integer pp;

    private Integer isMachineSkill;

    private String machineSkillCode;
}
