package pp.pokemon.pm.common.enums.file;


public enum DownloadType {

    PUBLIC("公有下载", 0),
    PRIVATE("私有下载", 1),
    ;

    DownloadType(String description, Integer type){
        this.description = description;
        this.type = type;
    }

    private String description;

    private Integer type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
