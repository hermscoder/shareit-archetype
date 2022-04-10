package ${package}.presentation;

import ${package}.utils.commons.exception.BadRequestException;
import ${package}.utils.commons.exception.InvalidParameterException;
import ${package}.utils.commons.response.ApiErrors;
import ${package}.utils.commons.response.Error;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;


@ControllerAdvice
@EnableWebMvc
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**TODO Create ApiError where we can have a single error or multiple errors like the following examples:
     * {
     *     "error": "auth-0001",
     *     "message": "Incorrect username and password",
     *     "detail": "Ensure that the username and password included in the request are correct",
     *     "help": "https://example.com/help/error/auth-0001"
     * }
     *
     * {
     *     "errors": [
     *         {
     *             "error": "auth-0001",
     *             "message": "Incorrect username and password",
     *             "detail": "Ensure that the username and password included in the request are correct",
     *             "help": "https://example.com/help/error/auth-0001"
     *         },
     *         ...
     *     ]
     * }
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrors apiErrors = new ApiErrors();
        BindingResult bindingResult = ex.getBindingResult();

        List<ObjectError> errors = bindingResult.getGlobalErrors();
        for(ObjectError objectError : errors) {
            apiErrors.addError(new Error(objectError.getCode(), objectError.getDefaultMessage(), ""));
        }
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for(FieldError fieldError : fieldErrors) {
            apiErrors.addError(new Error(fieldError.getCode(), String.format(fieldError.getDefaultMessage(),  fieldError.getField()), ""));
        }

        Object bodyObj = apiErrors.getErrors().isEmpty() || apiErrors.getErrors().size() > 1 ? apiErrors : apiErrors.getErrors().get(0);
        return new ResponseEntity<>(bodyObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { BadRequestException.class })
    protected ResponseEntity<Object> handleConflict(BadRequestException ex, WebRequest request) {
        return handleExceptionInternal(ex, new Error(ex.getClass().getSimpleName(), ex.getMessage(), ""), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Exception exception = getExpectedCauseException(ex, List.of(InvalidParameterException.class));

        return this.handleExceptionInternal(ex, new Error(exception.getClass().getSimpleName(), exception.getMessage(), ""), headers, status, request);
    }


    private Exception getExpectedCauseException(Exception exception, List<Class> expectedExceptionClasses) {
        Exception expectedException = exception;
        while(expectedException != null) {
            if(expectedExceptionClasses.contains(expectedException.getClass())) {
                return expectedException;
            }
            expectedException = (Exception) expectedException.getCause();
        }
        return expectedException != null ? expectedException : exception;
    }

}
