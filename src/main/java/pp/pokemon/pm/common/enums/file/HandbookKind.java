package pp.pokemon.pm.common.enums.file;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum HandbookKind {

    COMMON(1, "common/"),
    RARE(2, "rare/"),
    ;

    HandbookKind(Integer kind, String path){
        this.kind = kind;
        this.path = path;
    }

    private Integer kind;

    private String path;

    public Integer getKind() {
        return kind;
    }

    public void setKind(Integer kind) {
        this.kind = kind;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Map<Integer, String> getMap(){
        Map<Integer, String> map = new HashMap<>();
        Arrays.stream(HandbookKind.values())
                .forEach(fileType -> map.put(fileType.getKind(), fileType.getPath()));
        return map;
    }

}
