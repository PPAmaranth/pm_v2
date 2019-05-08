package pp.pokemon.pm.web.vo.property;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PropertyDetailReqVo {

    @NotNull(message = "属性id不能为空")
    private Integer id;

}
