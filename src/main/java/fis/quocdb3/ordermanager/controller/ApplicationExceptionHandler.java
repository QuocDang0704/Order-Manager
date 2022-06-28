package fis.quocdb3.ordermanager.controller;

import fis.quocdb3.ordermanager.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(value = {
            CustomerNotFoundException.class
    })
    protected ResponseEntity<ErrorMessage> handleCustomerNotFoundException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder().code("NOT_FOUND").message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = {
            OrderNotFoundException.class
    })
    protected ResponseEntity<ErrorMessage> handleOrderNotFoundException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder().code("NOT_FOUND").message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = {
            ProductNotFoundException.class
    })
    protected ResponseEntity<ErrorMessage> handleProductNotFoundException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder().code("NOT_FOUND").message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = {
            ProductQuantityNotEngoughException.class
    })
    protected ResponseEntity<ErrorMessage> handleProductQuantityNotEngoughException(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder().code("NOT_FOUND").message(exception.getMessage()).build());
    }

    @ExceptionHandler(value = {
            OrderCorrectStatus.class
    })
    protected ResponseEntity<ErrorMessage> handleOrderCorrectStatus(Exception exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder().code("NOT_FOUND").message(exception.getMessage()).build());
    }

}
