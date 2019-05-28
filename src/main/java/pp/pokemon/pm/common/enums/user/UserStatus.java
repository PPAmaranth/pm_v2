package pp.pokemon.pm.common.enums.user;

public enum UserStatus {

    ENABLED(1,"启用"),
    DISABLED(0, "禁用"),
    ;

    private Integer value;

    private String description;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    UserStatus(Integer value, String description) {
        this.value = value;
        this.description = description;
    }
}
