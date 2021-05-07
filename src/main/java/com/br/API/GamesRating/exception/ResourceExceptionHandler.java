package com.br.API.GamesRating.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardException> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardException error = new StandardException(HttpStatus.NOT_FOUND.value(), "Não foi possível encontrar objeto", e.getMessage(), System.currentTimeMillis(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardException<List<String>>> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        StandardException<List<String>> error = new StandardException(
                HttpStatus.BAD_REQUEST.value(),
                e.getBindingResult().getAllErrors().stream().map(objectError -> objectError.getDefaultMessage()).collect(Collectors.toList()),
                "Erro de validação", Calendar.getInstance().getTimeInMillis(),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ObjectNotSaveException.class)
    public ResponseEntity<StandardException<String>> objectNotSave(ObjectNotSaveException e, HttpServletRequest request) {
        StandardException<String> exception = new StandardException<String>(HttpStatus.CONFLICT.value(), "Erro de parametro", e.getMessage(), System.currentTimeMillis(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception);
    }

}