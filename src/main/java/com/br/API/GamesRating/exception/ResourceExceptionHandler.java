package com.br.API.GamesRating.exception;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResourceExceptionHandler {

  @ExceptionHandler(ObjectNotFoundException.class)
  public ResponseEntity<StandardException> objectNotFound(
      ObjectNotFoundException e, HttpServletRequest request) {
    StandardException error =
        new StandardException(
            HttpStatus.NOT_FOUND.value(),
            "Não foi possível encontrar objeto",
            e.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<StandardException<List<String>>> validation(
      MethodArgumentNotValidException e, HttpServletRequest request) {
    StandardException<List<String>> error =
        new StandardException(
            HttpStatus.BAD_REQUEST.value(),
            e.getBindingResult().getAllErrors().stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.toList()),
            "Erro de validação",
            Calendar.getInstance().getTimeInMillis(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(ObjectNotSaveException.class)
  public ResponseEntity<StandardException<String>> objectNotSave(
      ObjectNotSaveException e, HttpServletRequest request) {
    StandardException<String> exception =
        new StandardException<>(
            HttpStatus.CONFLICT.value(),
            "Erro de parametro",
            e.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.CONFLICT).body(exception);
  }

  @ExceptionHandler(FileException.class)
  public ResponseEntity<StandardException> file(FileException e, HttpServletRequest request) {

    StandardException<String> exception =
        new StandardException<>(
            HttpStatus.BAD_REQUEST.value(),
            "Erro de Arquivo",
            e.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
  }

  @ExceptionHandler(AmazonServiceException.class)
  public ResponseEntity<StandardException> amazonService(
      AmazonServiceException e, HttpServletRequest request) {
    HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
    StandardException<String> exception =
        new StandardException<>(
            code.value(),
            "Erro",
            e.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI());
    return ResponseEntity.status(code.value()).body(exception);
  }

  @ExceptionHandler(AmazonClientException.class)
  public ResponseEntity<StandardException> amazonClient(
      AmazonClientException e, HttpServletRequest request) {
    StandardException<String> exception =
        new StandardException<>(
            HttpStatus.BAD_REQUEST.value(),
            "Erro",
            e.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception);
  }

  @ExceptionHandler(AmazonS3Exception.class)
  public ResponseEntity<StandardException> amazonS3(
      AmazonS3Exception e, HttpServletRequest request) {
    StandardException<String> exception =
        new StandardException<>(
            HttpStatus.BAD_REQUEST.value(),
            "Erro",
            e.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception);
  }

  @ExceptionHandler(MaxUploadSizeExceededException.class)
  public ResponseEntity<StandardException> maxupload(
      MaxUploadSizeExceededException e, HttpServletRequest request) {
    StandardException<String> exception =
        new StandardException<>(
            HttpStatus.BAD_REQUEST.value(),
            "Erro",
            e.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(exception);
  }

  @ExceptionHandler(AuthorizationException.class)
  public ResponseEntity<StandardException> objectNotFound(
      AuthorizationException e, HttpServletRequest request) {
    StandardException error =
        new StandardException(
            HttpStatus.FORBIDDEN.value(),
            "Não autorizado",
            e.getMessage(),
            System.currentTimeMillis(),
            request.getRequestURI());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
  }
}
