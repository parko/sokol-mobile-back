package com.sokolmeteo.back.controller;

import com.sokolmeteo.BaseResponse;
import com.sokolmeteo.sokol.http.SokolHttpException;
import com.sokolmeteo.utils.exception.FileParseException;
import com.sokolmeteo.utils.exception.NoDataFoundException;
import com.sokolmeteo.utils.exception.NoDevicePermissionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(SokolHttpException.class)
    protected ResponseEntity<BaseResponse> handleSokolHttpException(final SokolHttpException e) {
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<BaseResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(FileParseException.class)
    protected ResponseEntity<BaseResponse> handleFileParseException(final FileParseException e) {
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(NoDevicePermissionException.class)
    protected ResponseEntity<BaseResponse> handleNoDevicePermissionException(final NoDevicePermissionException e) {
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(NoDataFoundException.class)
    protected ResponseEntity<BaseResponse> handleNoDataFoundException(final NoDataFoundException e) {
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    protected ResponseEntity<BaseResponse> handleMissingServletRequestParameterException(final MissingServletRequestParameterException e) {
        String message = "Отсутствует параметра запроса: " + e.getParameterName();
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(message)), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(final MethodArgumentTypeMismatchException e) {
        String message = String.format("Неверный тип данных параметра %s - ожидается %s", e.getName(), e.getRequiredType());
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList(message)), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> handleException(final Exception e) {
        return new ResponseEntity<>(
                new BaseResponse("FAULT", Collections.singletonList("Внутренняя ошибка")), HttpStatus.OK);
    }
}
