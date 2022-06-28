package fis.quocdb3.ordermanager.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomerNotFoundException extends RuntimeException {
    private String message;
}
