package pp.pokemon.pm.web.vo.pokemon;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EvolutionRelationshipReqVo {

    @NotNull(message = "进化前的精灵id不能为空")
    private Integer beforeId;

    @NotBlank(message = "进化条件不能为空")
    @Length(max = 200, message = "进化条件长度200")
    private String condition;
}
