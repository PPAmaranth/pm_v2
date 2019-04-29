package pp.pokemon.pm.web.vo.pokemon;

import lombok.Data;

@Data
public class QueryPokemonRespVo {

    private Integer id;

    private Integer illustrationBookId;

    private String name;

    private String jpName;

    private String enName;

    private Integer hp;

    private Integer attack;

    private Integer defense;

    private Integer sAttack;

    private Integer sDefense;

    private Integer propertyOne;

    private Integer propertyTwo;

    private Integer speed;

    private Integer ethnicValue;

    private String imgUrl;
}
