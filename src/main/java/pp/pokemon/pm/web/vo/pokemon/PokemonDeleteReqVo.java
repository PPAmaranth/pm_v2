package pp.pokemon.pm.web.vo.pokemon;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PokemonDeleteReqVo {

    @NotNull(message = "精灵id不能为空")
    private Integer id;

}
