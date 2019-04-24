package pp.pokemon.pm.web.vo.file;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Data
public class PublicDownloadReqVo {

    @NotNull(message = "附件id不能为空")
    @Max(value = Integer.MAX_VALUE)
    private Integer id;
}
