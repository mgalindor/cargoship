package com.mk.space.cargoship.adapters.prim.rest;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mk.space.cargoship.core.exceptions.BusinessErrorException;
import com.mk.space.cargoship.core.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.web.bind.annotation.RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerAdvice {

  public static final String LOCATION_FIELD = "location";
  public static final String HTTP_MESSAGE_NOT_READABLE = "http.message.not.readable.exception";
  public static final String INVALID_FORMAT = "invalid.format.exception";
  public static final String JSON_MAPPING = "json.mapping.exception";
  public static final String JSON_PARSE = "json.parse.exception";
  public static final String HTTP_MEDIA_TYPE_NOT_SUPPORTED =
          "http.media.type.not.supported.exception";
  public static final String CONSTRAINT_VIOLATION = "constraint.violation.exception";
  public static final String METHOD_ARGUMENT_NOT_VALID = "method.argument.not.valid.exception";

  private final MessageSource messageSource;

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail onHttpMessageNotReadableException(HttpMessageNotReadableException e,
          HttpServletRequest request) {
    String message = Match(e.getCause()).of(
            Case($(instanceOf(HttpMessageNotReadableException.class)),
                    cause -> getLocalizedMessage(HTTP_MESSAGE_NOT_READABLE)),
            // Eg. Could not parse a date
            Case($(instanceOf(InvalidFormatException.class)),
                    (Function<? super InvalidFormatException, String>) cause -> getLocalizedMessage(
                            INVALID_FORMAT, getPath(cause.getPath()),
                            cause.getLocation().getLineNr(), cause.getLocation().getColumnNr())),
            // Error in body missing , or ' instead "
            Case($(instanceOf(JsonMappingException.class)),
                    (Function<? super JsonMappingException, String>) cause -> getLocalizedMessage(
                            JSON_MAPPING, getPath(cause.getPath()), cause.getLocation().getLineNr(),
                            cause.getLocation().getColumnNr())),
            //Error  or ' instead "
            Case($(instanceOf(JsonParseException.class)),
                    (Function<? super JsonParseException, String>) cause -> getLocalizedMessage(
                            JSON_PARSE, cause.getLocation().getLineNr(),
                            cause.getLocation().getColumnNr())),
            Case($(), cause -> getLocalizedMessage(HTTP_MESSAGE_NOT_READABLE)));
    if (e.getCause() != null) {
      log.info("HttpMessageNotReadableException cause:[{}] message:[{}]",
              e.getCause().getClass().getName(), e.getCause().getMessage());
    }

    return defaultErrorResponseBuilder(request, HttpStatus.BAD_REQUEST, message);
  }

  private String getPath(List<JsonMappingException.Reference> references) {
    if (CollectionUtils.isEmpty(references)) {
      return "";
    } else {
      return references.stream().map(ref -> ref.getIndex() != -1 ?
              "[" + ref.getIndex() + "]" :
              "." + ref.getFieldName()).collect(Collectors.joining()).substring(1);
    }
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail onHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e,
          HttpServletRequest request) {
    return defaultErrorResponseBuilder(request, HttpStatus.BAD_REQUEST,
            getLocalizedMessage(HTTP_MEDIA_TYPE_NOT_SUPPORTED));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail onConstraintValidationException(ConstraintViolationException e,
          HttpServletRequest request) {
    // On validating requests with @Valid
    List<LocationResponse> details = e.getConstraintViolations().stream()
            .map(violation -> new LocationResponse(violation.getPropertyPath().toString(),
                    violation.getMessage())).collect(Collectors.toList());

    ProblemDetail problemDetail = defaultErrorResponseBuilder(request, HttpStatus.BAD_REQUEST,
            getLocalizedMessage(CONSTRAINT_VIOLATION));
    problemDetail.setProperty(LOCATION_FIELD, details);
    return problemDetail;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail onMethodArgumentNotValidException(MethodArgumentNotValidException e,
          HttpServletRequest request) {
    // On validating methods with @Validate
    List<LocationResponse> details = e.getBindingResult().getFieldErrors().stream()
            .map(fieldError -> new LocationResponse(fieldError.getField(),
                    fieldError.getDefaultMessage())).collect(Collectors.toList());


    ProblemDetail problemDetail = defaultErrorResponseBuilder(request, HttpStatus.BAD_REQUEST,
            getLocalizedMessage(METHOD_ARGUMENT_NOT_VALID));
    problemDetail.setProperty(LOCATION_FIELD, details);
    return problemDetail;
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ProblemDetail onResourceNotFoundException(ResourceNotFoundException e,
          HttpServletRequest request) {
    return defaultErrorResponseBuilder(request, HttpStatus.BAD_REQUEST,
            getLocalizedMessage(e.getCode()));
  }

  @ExceptionHandler(BusinessErrorException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  public ProblemDetail onBusinessErrorException(BusinessErrorException e,
          HttpServletRequest request) {

    return defaultErrorResponseBuilder(request, HttpStatus.UNPROCESSABLE_ENTITY,
            getLocalizedMessage(e.getCode()));
  }

  @SneakyThrows
  ProblemDetail defaultErrorResponseBuilder(HttpServletRequest request, HttpStatus status,
          String message) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
    problemDetail.setInstance(new URI(request.getServletPath()));
    problemDetail.setTitle(status.getReasonPhrase());
    return problemDetail;
  }

  String getLocalizedMessage(String code, Object... args) {
    Locale local = LocaleContextHolder.getLocale();
    return messageSource.getMessage(code, args, local);
  }


  record LocationResponse(String field, String error) {

  }
}
