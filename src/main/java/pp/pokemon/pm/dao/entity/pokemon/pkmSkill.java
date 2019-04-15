package pp.pokemon.pm.dao.entity.pokemon;

public class pkmSkill {
    private Integer id;

    private String cnName;

    private String jpName;

    private String enName;

    private String property;

    private String classification;

    private Integer power;

    private Integer hitProbability;

    private Integer pp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getJpName() {
        return jpName;
    }

    public void setJpName(String jpName) {
        this.jpName = jpName;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getHitProbability() {
        return hitProbability;
    }

    public void setHitProbability(Integer hitProbability) {
        this.hitProbability = hitProbability;
    }

    public Integer getPp() {
        return pp;
    }

    public void setPp(Integer pp) {
        this.pp = pp;
    }
}