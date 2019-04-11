package pp.pokemon.pm.common.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.web.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RetException.class)
    @ResponseBody
    public Object RetErrorHandler(HttpServletRequest req, RetException e) throws Exception {
        return BaseController.failure(e.getRetCode(), e.getResMsg());
    }
}
