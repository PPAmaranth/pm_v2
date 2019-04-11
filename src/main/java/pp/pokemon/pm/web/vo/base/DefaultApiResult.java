package pp.pokemon.pm.web.vo.base;

import lombok.Data;

@Data
public class DefaultApiResult {

    private final static String SUCCESS_CODE = "0";

    private final static String SUCCESS_MSG = "success";

    private String code;

    private String msg;

    private Object result;

    public DefaultApiResult(String code, String msg, Object result){
        this.code = code;
        this.msg = msg;
        this.result = result;
    }

    public DefaultApiResult(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public DefaultApiResult(Object result){
        this(SUCCESS_CODE, SUCCESS_MSG, result);
    }

    public DefaultApiResult(){
        this(SUCCESS_CODE, SUCCESS_MSG, null);
    }


}
