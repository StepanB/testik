package cz.istep.javatest.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class FrameworkNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(FrameworkNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String frameworkNotFoundHandler(FrameworkNotFoundException ex) {
        return ex.getMessage();
    }
}
