package com.sokolmeteo.back.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sokolmeteo.dto.BaseResponse;
import com.sokolmeteo.sokol.http.HttpInteractionException;
import com.sokolmeteo.sokol.http.dto.SokolResponse;
import com.sokolmeteo.utils.exception.FileParseException;
import com.sokolmeteo.utils.exception.NoDataFoundException;
import com.sokolmeteo.utils.exception.NoDevicePermissionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.*;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<BaseResponse> handleHttpClientErrorException(final HttpClientErrorException e) {
        logger.warn(String.valueOf(e));
        SokolResponse response = parseSokolErrorMessage(e.getMessage());
        return new ResponseEntity<>(
                new BaseResponse("FAULT", response.getCode(), response.getMessage()), HttpStatus.OK);
    }
    @ExceptionHandler(HttpInteractionException.class)
    protected ResponseEntity<BaseResponse> handleHttpInteractionException(final HttpInteractionException e) {
        logger.warn(String.valueOf(e));
        return new ResponseEntity<>(
                new BaseResponse("FAULT", e.getMessage(), e.getLocalMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<BaseResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        logger.warn(String.valueOf(e));
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(FileParseException.class)
    protected ResponseEntity<BaseResponse> handleFileParseException(final FileParseException e) {
        logger.warn(String.valueOf(e));
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(NoDevicePermissionException.class)
    protected ResponseEntity<BaseResponse> handleNoDevicePermissionException(final NoDevicePermissionException e) {
        logger.warn(String.valueOf(e));
        return new ResponseEntity<>(
                new BaseResponse("FAULT", e.getMessage(), e.getLocalMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(NoDataFoundException.class)
    protected ResponseEntity<BaseResponse> handleNoDataFoundException(final NoDataFoundException e) {
        logger.warn(String.valueOf(e));
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<BaseResponse> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        logger.warn(String.valueOf(e));
        String message = "Отсутствует параметр запроса: " + e.getParameterName();
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(message)), HttpStatus.OK);
    }

    @ExceptionHandler(MissingRequestCookieException.class)
    protected ResponseEntity<BaseResponse> handleMissingRequestCookieException(final MissingRequestCookieException e) {
        logger.warn(String.valueOf(e));
        return new ResponseEntity<>(
                new BaseResponse("FAULT", e.getMessage(), "Отсутствует Cookie запроса: JSESSIONID"), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<BaseResponse> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException e) {
        logger.warn(String.valueOf(e));
        String message = String.format("Неверный тип данных параметра %s - ожидается %s", e.getName(), e.getRequiredType());
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(message)), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        logger.warn(String.valueOf(e));
        List<String> errors = new ArrayList<>();
        for (ObjectError error : e.getBindingResult().getAllErrors())
            errors.add(error.getDefaultMessage());
        return new ResponseEntity<>(
                new BaseResponse("FAULT", errors), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> handleException(final Exception e) {
        logger.warn(String.valueOf(e));
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList("Внутренняя ошибка")), HttpStatus.OK);
    }

    private SokolResponse parseSokolErrorMessage(String sokolMessage) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(sokolMessage, "[]");
            String status = tokenizer.nextToken();
            if (tokenizer.hasMoreTokens()) {
                String messages = tokenizer.nextToken();
                return new ObjectMapper().readValue(messages, SokolResponse.class);
            }
        } catch (JsonProcessingException ignore) { }

        return new SokolResponse("No message available");
    }
}