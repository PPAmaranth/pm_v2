package pp.pokemon.pm.web.vo.pokemon;

import lombok.Data;

@Data
public class PokemonAttachmentRespVo {

    private Integer pokemonId;

    private Integer attachmentId;

    private Integer type;

    private Integer kind;

    private String fileUri;

}
