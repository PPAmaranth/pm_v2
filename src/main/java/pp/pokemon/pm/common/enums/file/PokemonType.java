package pp.pokemon.pm.common.enums.file;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PokemonType {

    DETAIL(1, "detail/"),
    LIST(2, "list/"),
    ;

    PokemonType(Integer type, String path){
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
        Arrays.stream(PokemonType.values())
                .forEach(pokemonType -> map.put(pokemonType.getType(), pokemonType.getPath()));
        return map;
    }

}
