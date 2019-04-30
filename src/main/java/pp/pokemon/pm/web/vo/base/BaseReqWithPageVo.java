package pp.pokemon.pm.web.vo.base;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class BaseReqWithPageVo {

    @Min(value = 1, message = "取值1~2147483647")
    @Max(value = 2147483647, message = "取值1~2147483647")
    private Integer pageNum = 1;

    @Min(value = 1, message = "取值1~2147483647")
    @Max(value = 2147483647, message = "取值1~2147483647")
    private Integer pageSize = 15;

    private String orderBy;

    private String sort;

}
