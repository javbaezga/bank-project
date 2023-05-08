package com.bank.handler;

import com.bank.service.exception.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.UnexpectedTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class AppResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(AppResponseEntityExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        final MethodArgumentNotValidException ex,
        final HttpHeaders headers,
        final HttpStatusCode status,
        final WebRequest request
    ) {
        final Class<?> targetClass = Objects.requireNonNull(ex.getBindingResult().getTarget()).getClass();
        final Map<String, List<String>> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            final String fieldName = ((FieldError) error).getField();
            String field;
            try {
                field = targetClass.getDeclaredField(fieldName).getAnnotation(JsonProperty.class).value();
            } catch (final NoSuchFieldException | SecurityException e) {
                field = fieldName;
            }
            errors
                .computeIfAbsent(field, k -> new ArrayList<>(5))
                .add(error.getDefaultMessage());
        });
        return handleCustomExceptionInternal(
            new Exception("Invalid method argument"),
            request,
            HttpStatus.BAD_REQUEST,
            headers,
            errors
        );
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        final MissingServletRequestParameterException ex,
        final HttpHeaders headers,
        final HttpStatusCode status,
        final WebRequest request
    ) {
        return handleCustomExceptionInternal(
            new Exception("Missing servlet request parameter"),
            request,
            HttpStatus.BAD_REQUEST,
            headers,
            Map.of(ex.getParameterName(), List.of("parameter is missing"))
        );
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(
        final ConstraintViolationException ex,
        final WebRequest request
    ) {
        final Map<String, List<String>> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation ->
            errors
                .computeIfAbsent(violation.getPropertyPath().toString(), k -> new ArrayList<>(5))
                .add(violation.getMessage())
        );
        return handleCustomExceptionInternal(
            new Exception("Constraint violation"),
            request,
            HttpStatus.BAD_REQUEST,
            new HttpHeaders(),
            errors
        );
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
        final MethodArgumentTypeMismatchException ex,
        final WebRequest request
    ) {
        return handleCustomExceptionInternal(
            new Exception("Method argument type mismatch"),
            request,
            HttpStatus.BAD_REQUEST,
            new HttpHeaders(),
            Map.of(
                ex.getName(),
                List.of(String.format(
                    "should be of type %s",
                    Objects.requireNonNull(ex.getRequiredType()).getName()
                ))
            )
        );
    }

    protected <E extends Exception> ResponseEntity<Object> handleCustomExceptionInternal(
        final E ex,
        final WebRequest request,
        final HttpStatusCode status,
        final HttpHeaders headers,
        final Map<String, List<String>> errors
    ) {
        return handleExceptionInternal(
            ex,
            new AppProblem(status, ex.getLocalizedMessage(), errors),
            headers,
            status,
            request
        );
    }

    @ExceptionHandler({
        ClientDisabledException.class,
        AccountDisabledException.class,
        DailyQuotaExceededException.class,
        NotAvailableBalanceException.class,
        ResourceBadRequestException.class,
        ResourceNotFoundException.class,
        UnexpectedTypeException.class,
        SQLIntegrityConstraintViolationException.class
    })
    public ResponseEntity<Object> handleCustomException(final Exception ex, final WebRequest request) {
        HttpStatus status = null;
        final Map<String, List<String>> errors;
        if (ex instanceof DailyQuotaExceededException dailyQuotaExceededException) {
            errors = Map.of("quota", List.of(String.format("available %s", dailyQuotaExceededException.getDailyBalance())));
        } else if (ex instanceof NotAvailableBalanceException notAvailableBalanceException) {
            errors = Map.of("balance", List.of(String.format("available %s", notAvailableBalanceException.getBalance())));
        } else if (ex instanceof ClientDisabledException clientDisabledException) {
            errors = Map.of("client", List.of(String.format("%s disabled", clientDisabledException.getClient().getFullName())));
        } else if (ex instanceof AccountDisabledException accountDisabledException) {
            errors = Map.of("account", List.of(String.format("%s disabled", accountDisabledException.getAccount().getNumber())));
        } else if (ex instanceof ResourceNotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errors = Map.of();
        } else {
            errors = Map.of();
        }
        return handleCustomExceptionInternal(
            ex,
            request,
            status != null ? status : HttpStatus.BAD_REQUEST,
            new HttpHeaders(),
            errors
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
        final Exception ex,
        @Nullable Object body,
        final HttpHeaders headers,
        final HttpStatusCode statusCode,
        final WebRequest request
    ) {
        logger.error("APPLICATION EXCEPTION", ex);
        if (!(body instanceof AppProblem)) {
            body = new AppProblem(
                statusCode,
                ex.getLocalizedMessage(),
                Map.of()
            );
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    record AppProblem(int status, String message, Map<String, List<String>> errors) {
        public AppProblem(final HttpStatusCode statusCode, final String message, final Map<String, List<String>> errors) {
            this(statusCode.value(), message, errors);
        }
    }
}
