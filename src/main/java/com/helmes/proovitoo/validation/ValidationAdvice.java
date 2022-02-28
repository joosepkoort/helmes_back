package com.helmes.proovitoo.validation;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@ControllerAdvice
public class ValidationAdvice {
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler
    public List<ValidationError> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {
        ValidationErrors errors = new com.helmes.proovitoo.validation.ValidationErrors();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()){
            errors.addError(fieldError);
        }

        return errors.getErrors();
    }
}