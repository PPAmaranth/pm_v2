package pp.pokemon.pm.web.vo.book;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class AddBookReqVo {

    @NotBlank(message = "书名不能为空")
    @Length(max = 50, message = "书名长度50")
    private String name;

    @NotBlank(message = "作者不能为空")
    @Length(max = 50, message = "作者长度50")
    private String author;

    @NotNull(message = "金额不能为空")
    private BigDecimal price;
}
