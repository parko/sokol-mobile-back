package com.sokolmeteo.back.controller;

import com.sokolmeteo.BaseResponse;
import com.sokolmeteo.dao.model.RecordState;
import com.sokolmeteo.sokol.http.SokolHttpException;
import com.sokolmeteo.utils.exception.FileParseException;
import com.sokolmeteo.utils.exception.NoDevicePermission;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(SokolHttpException.class)
    protected ResponseEntity<BaseResponse> handleSokolHttpException(final SokolHttpException e) {
        return new ResponseEntity<>(
                new BaseResponse(RecordState.FAULT.toString(), Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<BaseResponse> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        return new ResponseEntity<>(
                new BaseResponse(RecordState.FAULT.toString(), Collections.singletonList(e.getMessage())), HttpStatus.OK);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<BaseResponse> handleIllegalArgumentException(final IllegalArgumentException e) {
        return new ResponseEntity<>(
                new BaseResponse(RecordState.FAULT.toString(), Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(FileParseException.class)
    protected ResponseEntity<BaseResponse> handleFileParseException(final FileParseException e) {
        return new ResponseEntity<>(
                new BaseResponse(RecordState.FAULT.toString(), Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(NoDevicePermission.class)
    protected ResponseEntity<BaseResponse> handleNoDevicePermission(final NoDevicePermission e) {
        return new ResponseEntity<>(
                new BaseResponse(RecordState.FAULT.toString(), Collections.singletonList(e.getMessage())), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> handleException(final Exception e) {
        return new ResponseEntity<>(
                new BaseResponse(RecordState.FAULT.toString(), Collections.singletonList("Внутренняя ошибка")), HttpStatus.OK);
    }
}
