package pp.pokemon.pm.dao.vo.skill;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BatchAddSkillReqVo {

    private String cn_name;

    private String jp_name;

    private String en_name;

    private String propery;

    private String classfication;

    private String power;

    private String hit_probability;

    @JsonProperty("PP")
    private String pp;
}
