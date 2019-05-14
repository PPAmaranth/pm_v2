package pp.pokemon.pm.dao.vo.pokemon;

import lombok.Data;
import pp.pokemon.pm.web.vo.base.BaseReqWithPageVo;

@Data
public class QueryAllPokemonReqVo extends BaseReqWithPageVo {

    private String word;

}
