package pp.pokemon.pm.common.util;

import pp.pokemon.pm.common.constant.RetException;
import pp.pokemon.pm.common.message.SystemErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;
import java.util.stream.Collectors;

public class BeanValidators {

    public static void validateWithParameterException(Validator validator, Object object, Class<?>... groups)
            throws ConstraintViolationException {
        Set constraintViolations = validator.validate(object, groups);
        if (!constraintViolations.isEmpty()) {
            String message =  (String)constraintViolations.stream().map(cv -> {
                ConstraintViolation constraintViolation = (ConstraintViolation)cv;
                String str = "["+constraintViolation.getPropertyPath().toString() +"]"+ constraintViolation.getMessageTemplate();
                return str;
            }).collect(Collectors.joining(","));
            throw new RetException(SystemErrorMessage.VALIDATE_PARAMETER_ERROR_CODE, SystemErrorMessage.VALIDATE_PARAMETER_ERROR_MSG + message);
        }
    }
}
