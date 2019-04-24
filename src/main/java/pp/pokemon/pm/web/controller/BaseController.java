package pp.pokemon.pm.web.controller;

import org.springframework.web.bind.annotation.RestController;
import pp.pokemon.pm.web.vo.base.DefaultApiResult;

import javax.validation.Validation;
import javax.validation.Validator;

@RestController
public class BaseController {
    /**
     * 验参
     */
    protected static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    /**
     * 返参封装
     */
    public static DefaultApiResult success(){
        return new DefaultApiResult();
    }

    public static DefaultApiResult success(Object result){
        return new DefaultApiResult(result);
    }

    public static DefaultApiResult failure(String code, String msg){
        return new DefaultApiResult(code, msg);
    }

}
