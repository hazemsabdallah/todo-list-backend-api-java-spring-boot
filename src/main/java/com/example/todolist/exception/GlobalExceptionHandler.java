package com.example.todolist.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        Map<String, List<String>> body = new HashMap<>();

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleValidationException(HttpMessageNotReadableException exception) {

        Map<String, List<String>> body = new HashMap<>();
        String errorDetails = exception.getMessage().split(":")[0]; // handle message shown

        if (exception.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ex = (InvalidFormatException) exception.getCause();
            if (ex.getTargetType()!=null && ex.getTargetType().isEnum()) {
                errorDetails = String.format("Invalid enum value: '%s' for the field: '%s'. The value must be one of: %s.",
                        ex.getValue(), ex.getPath().get(ex.getPath().size()-1).getFieldName(), Arrays.toString(ex.getTargetType().getEnumConstants()));
            }
        }

        List<String> errors = new ArrayList<>();
        errors.add(errorDetails);
        body.put("errors", errors);
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class,
            DataIntegrityViolationException.class,
            MethodArgumentTypeMismatchException.class,
            MissingPathVariableException.class
    })
    public ResponseEntity<Object> handleMultipleException(Exception exception) {

        Map<String, List<String>> body = new HashMap<>();
        String errorDetails = exception.getMessage();

        HttpStatus httpStatus;
        switch (exception.getClass().getSimpleName()) {
            case "ResourceNotFoundException":
                httpStatus = HttpStatus.NOT_FOUND;
                break;
            case "DataIntegrityViolationException":
                errorDetails=(exception.getMessage().split("[\\[\\]]"))[1];
                httpStatus = HttpStatus.CONFLICT;
                break;
            default:
                httpStatus = HttpStatus.BAD_REQUEST;
                break;
        }

        List<String> errors = new ArrayList<>();
        errors.add(errorDetails);
        body.put("errors", errors);
        return new ResponseEntity<>(body, httpStatus);
    }
}