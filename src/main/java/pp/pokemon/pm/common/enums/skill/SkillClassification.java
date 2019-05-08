package pp.pokemon.pm.common.enums.skill;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum SkillClassification {

    PHYSICAL(1, "物理"),
    SPECIAL(2, "特攻"),
    TRANSFORMATIONAL(3, "变化"),
    ;

    SkillClassification(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map<Integer, String> getMap(){
        Map<Integer, String> map = new HashMap<>();
        Arrays.stream(SkillClassification.values())
                .forEach(skillClassification -> map.put(skillClassification.getId(), skillClassification.getName()));
        return map;
    }

}
