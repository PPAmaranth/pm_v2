package pp.pokemon.pm.dao.entity.pokemon;

public class Skill {
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

    public Integer getProperty() {
        return property;
    }

    public void setProperty(Integer property) {
        this.property = property;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
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

    public Integer getIsMachineSkill() {
        return isMachineSkill;
    }

    public void setIsMachineSkill(Integer isMachineSkill) {
        this.isMachineSkill = isMachineSkill;
    }

    public String getMachineSkillCode() {
        return machineSkillCode;
    }

    public void setMachineSkillCode(String machineSkillCode) {
        this.machineSkillCode = machineSkillCode;
    }
}