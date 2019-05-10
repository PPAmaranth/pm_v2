package pp.pokemon.pm.dao.entity.pokemon;

public class Skill {
    private Integer id;

    private String cnName;

    private String jpName;

    private String enName;

    private Integer property;

    private Integer classification;

    private String power;

    private String hitProbability;

    private String pp;

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

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getHitProbability() {
        return hitProbability;
    }

    public void setHitProbability(String hitProbability) {
        this.hitProbability = hitProbability;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
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