package com.example.serverktpm.exception;

import com.zulo.zuloserver.common.WrapResponseStatus;
import com.zulo.zuloserver.response.WrapResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class HandelException extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public WrapResponse resourceException(Exception ex){
        ex.printStackTrace();
        return WrapResponse.error(WrapResponseStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public WrapResponse resourceRuntimeException(RuntimeException ex){
        ex.printStackTrace();
        return WrapResponse.error(WrapResponseStatus.SERVICE_ERROR,ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public WrapResponse resourceNotFoundException(NotFoundException ex){
        return  WrapResponse.error(WrapResponseStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public WrapResponse resourceServiceException(ServiceException ex){
        return  WrapResponse.error(WrapResponseStatus.SERVICE_ERROR, ex.getMessage());
    }

}
