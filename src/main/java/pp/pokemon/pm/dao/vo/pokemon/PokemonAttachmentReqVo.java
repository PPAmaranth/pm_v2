package pp.pokemon.pm.dao.vo.pokemon;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PokemonAttachmentReqVo {

    @NotNull(message = "精灵id不为空")
    private Integer id;

    private Integer type;

    private Integer kind;
}
