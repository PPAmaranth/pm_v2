package pp.pokemon.pm.common.enums.file;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum  FileModule {

    ILLUSTRATED_HANDBOOK(1, "illustratedHandbook/"),
            ;

    FileModule(Integer module, String path){
        this.module = module;
        this.path = path;
    }

    private Integer module;

    private String path;

    public Integer getModule() {
        return module;
    }

    public void setModule(Integer module) {
        this.module = module;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static Map<Integer, String> getMap(){
        Map<Integer, String> map = new HashMap<>();
        Arrays.stream(FileModule.values())
                .forEach(fileModule -> map.put(fileModule.getModule(), fileModule.getPath()));
        return map;
    }

}
