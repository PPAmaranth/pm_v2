package pp.pokemon.pm.web.vo.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserLoginReqVo {

    @NotBlank(message = "用户名/邮箱/手机不能为空")
    @Length(max = 200, message = "用户名/邮箱/手机长度200")
    private String username;

    @NotBlank(message = "密码不为空")
    private String password;

    @NotNull(message = "是否记住用户登录参数不能为空")
    @Max(value = 1, message = "记住用户登录1, 不记住0")
    @Min(value = 0, message = "记住用户登录1, 不记住0")
    private Integer rememberMe;
}
