package pp.pokemon.pm.common.enums.file;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum HandbookType {

    DETAIL(1, "detail/"),
    LIST(2, "list/"),
    ;

    HandbookType(Integer type, String path){
        this.type = type;
        this.path = path;
    }

    private Integer type;

    private String path;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Map<Integer, String> getMap(){
        Map<Integer, String> map = new HashMap<>();
        Arrays.stream(HandbookType.values())
                .forEach(handbookType -> map.put(handbookType.getType(), handbookType.getPath()));
        return map;
    }

}
