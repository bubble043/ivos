package edu.fdu.ivos.base.exception;

import edu.fdu.ivos.base.response.JsonResult;
import edu.fdu.ivos.base.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 * @ControllerAdvice annotation: Marks this class as a global exception handler, capable of handling exceptions thrown by controllers.
 *   1. When an exception is thrown in a Controller, it first checks if a global exception handler is defined.
 *   2. If defined, it tries to match the exception with a handler method.
 *   3. If a match is found, that method is executed to handle the exception.
 *
 * @RestControllerAdvice is a composite annotation equivalent to @ResponseBody + @ControllerAdvice.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /*
     * @ExceptionHandler annotation: Marks a method as an exception handler.
     *   1. First tries to find a method matching the exact exception type.
     *   2. If not found, searches for a handler for its parent class.
     *   3. If still not found, falls back to Spring MVC's default exception handling.
     */

    @ExceptionHandler
    public JsonResult doHandleServiceException(ServiceException ex) {
        // Handle custom business exception and log the error message
        log.error("ServiceException: " + ex.getStatusCode().getMsg());
        return new JsonResult(ex.getStatusCode());
    }

    /**
     * Handler for IllegalArgumentException
     *
     * @param ex the exception object
     * @return JsonResult
     */
    @ExceptionHandler
    public JsonResult doHandleIllegalArgumentException(IllegalArgumentException ex) {
        String message = ex.getMessage();
        log.error("IllegalArgumentException: " + message);
        return new JsonResult(StatusCode.OPERATION_FAILED, message);
    }

    /**
     * Handler for RuntimeException
     *
     * @param ex the exception object
     * @return JsonResult
     */
    @ExceptionHandler
    public JsonResult doHandleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        log.error("RuntimeException: " + message);
        return new JsonResult(StatusCode.OPERATION_FAILED, message);
    }

    /**
     * Handler for MethodArgumentNotValidException
     * --- Specifically handles exceptions thrown when validating POJO parameters ---
     * 1. Thrown by Spring Validation framework.
     * 2. Occurs when POJO field constraints are not satisfied.
     */
    @ExceptionHandler
    public JsonResult doHandleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getFieldError().getDefaultMessage();
        log.error("MethodArgumentNotValidException: " + message);
        return new JsonResult(StatusCode.VALIDATE_ERROR, message);
    }

    /**
     * Handler for ConstraintViolationException
     * --- Specifically handles exceptions thrown when validating individual request parameters ---
     * 1. Thrown by Spring Validation framework.
     * 2. Occurs when method-level parameter constraints are not satisfied.
     */
    @ExceptionHandler
    public JsonResult doHandleConstraintViolationException(ConstraintViolationException ex) {
        // Example: "selectById.id: ID must be between 200 and 500" → "ID must be between 200 and 500"
        String message = ex.getMessage().split(":")[1].trim();
        log.error("ConstraintViolationException: " + message);
        return new JsonResult(StatusCode.VALIDATE_ERROR, message);
    }
}
