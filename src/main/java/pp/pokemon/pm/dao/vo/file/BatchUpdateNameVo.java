package pp.pokemon.pm.dao.vo.file;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class BatchUpdateNameVo {

    @NotNull(message = "id不为空")
    private Integer id;

    @Length(max = 50, message = "名字长度50")
    private String cn_name;

    @Length(max = 50, message = "名字长度50")
    private String jp_name;

    @Length(max = 50, message = "名字长度50")
    private String en_name;
}
