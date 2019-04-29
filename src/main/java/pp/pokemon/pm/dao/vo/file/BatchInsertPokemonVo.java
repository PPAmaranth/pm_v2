package pp.pokemon.pm.dao.vo.file;

import lombok.Data;

import java.util.List;

@Data
public class BatchInsertPokemonVo {

    private Integer id;

    private String name;

    private Integer hp;

    private Integer attack;

    private Integer defense;

    private Integer s_attack;

    private Integer s_defense;

    private Integer speed;

    private Integer ethnic_value;

    List<BatchInsertPokemonPropertyVo> property;

    @Data
    public static class BatchInsertPokemonPropertyVo{

        private Integer id;

        private String name;

    }
}
