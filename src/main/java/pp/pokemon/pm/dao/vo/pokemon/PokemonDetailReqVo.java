package pp.pokemon.pm.dao.vo.pokemon;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PokemonDetailReqVo {

    @NotNull(message = "精灵id不能为空")
    private Integer id;
}
