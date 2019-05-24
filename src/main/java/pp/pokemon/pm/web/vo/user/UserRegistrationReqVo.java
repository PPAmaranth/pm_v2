package pp.pokemon.pm.web.vo.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationReqVo {

    @NotBlank(message = "用户名不能为空")
    @Length(max = 200, message = "用户名长度200")
    private String username;

    private String mobile;

    @Length(max = 200, message = "邮箱长度200")
    private String email;
    
    private String password;

}
