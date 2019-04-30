package pp.pokemon.pm.web.vo.pokemon;

import lombok.Data;

@Data
public class PokemonAttachmentRespVo {

    private Integer id;

    private Integer attachmentId;

    private Integer type;

    private String typeName;

    private Integer kind;

    private String kindName;

    private String fileUri;

}
