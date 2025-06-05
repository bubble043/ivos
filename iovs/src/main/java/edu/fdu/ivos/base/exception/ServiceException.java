package edu.fdu.ivos.base.exception;

import edu.fdu.ivos.base.response.StatusCode;
import lombok.Getter;

/* Custom business exception class */
public class ServiceException extends RuntimeException {
    @Getter
    private StatusCode statusCode;

    // Press Alt+Insert and keep hitting Enter to generate the constructor
    public ServiceException(StatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
