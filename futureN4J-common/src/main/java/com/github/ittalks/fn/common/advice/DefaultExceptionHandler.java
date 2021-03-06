package com.github.ittalks.fn.common.advice;

import com.github.ittalks.fn.common.advice.exception.ErrorMessage;
import com.github.ittalks.fn.common.advice.exception.NestedException;
import com.github.ittalks.fn.common.result.ErrorCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NestedException.class})
    public ResponseEntity<?> handleNestedException(NestedException e) {
        ErrorMessage eErrorMessage = e.getErrorMessage();
        if (eErrorMessage != null) {
            return new ResponseEntity<>(eErrorMessage, null, eErrorMessage.getHttpStatus());
        } else {
            return new ResponseEntity<>(ErrorCode.BAD_REQUEST, null, ErrorCode.BAD_REQUEST.getHttpStatus());
        }
    }

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(ErrorCode.INTERNAL_SERVER_ERROR, null, ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus());
    }
}
