package pp.pokemon.pm.common.enums.file;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PokemonKind {

    COMMON(1, "common/"),
    RARE(2, "rare/"),
    ;

    PokemonKind(Integer kind, String path){
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
        Arrays.stream(PokemonKind.values())
                .forEach(pokemonKind -> map.put(pokemonKind.getKind(), pokemonKind.getPath()));
        return map;
    }

}
