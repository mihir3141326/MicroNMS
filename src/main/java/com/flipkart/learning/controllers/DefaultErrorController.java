package com.flipkart.learning.controllers;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import java.util.List;
import java.util.Map;

@Controller
public class DefaultErrorController {

    // Catches Constraints Violation Errors thrown in any of the controller (i.e. in any service or repository)
    @Error(global = true, exception = ConstraintViolationException.class)
    public HttpResponse<Map<String, List<String>>> handleConstraintViolationException(HttpRequest<?> request, ConstraintViolationException validationException) {

        System.err.println("Validation Error: " + validationException.getMessage() + '\n' + request.getUri() + ":" + request.getBody());

        // extract error messages we wrote in our DTOs
        List<String> errorMessages = validationException.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        // Wrap in JSON object under the key "errors"
        Map<String, List<String>> responseBody = Map.of("errors", errorMessages);

        // returns 400 Bad Request instead of a 500 Server Error
        return HttpResponse.badRequest(responseBody);
    }

    // This catches any exception thrown
    @Error(global = true, exception = Exception.class)
    public HttpResponse<Map<String, String>> handleGlobalExceptions(HttpRequest<?> request, Exception exception) {

        System.err.println("SYSTEM CRASH: " + exception.getMessage());

        Map<String, String> safeResponse = Map.of(
                "error", "Internal Server Error",
                "message", "An unexpected error occurred. Please try again later."
        );

        return HttpResponse.serverError(safeResponse);
    }
}