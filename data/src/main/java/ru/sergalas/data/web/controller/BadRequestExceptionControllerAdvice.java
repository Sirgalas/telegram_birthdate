package ru.sergalas.data.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class BadRequestExceptionControllerAdvice {
    final private MessageSource messageSource;

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ProblemDetail> handleBindException(
            BindException exception,
            Locale locale
    ) {
        ProblemDetail problemDetail = ProblemDetail
            .forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                this.messageSource.getMessage(
                    "errors.400.title",
                    new Object[0],
                    "errors.400.title",
                    locale
                )
            );

        problemDetail.setProperty(
            "errors",
            exception.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                    FieldError::getField,
                    Collectors.mapping(
                        fieldError -> resolveMessage(fieldError, locale),
                        Collectors.toList()
                    )
                )
            )
        );
        return ResponseEntity.badRequest().body(problemDetail);
    }

    private String resolveMessage(FieldError fieldError, Locale locale) {
        String message = messageSource.getMessage(
                fieldError.getDefaultMessage(),
                fieldError.getArguments(),
                fieldError.getDefaultMessage(),
                locale
        );
        return message;
    }
}
