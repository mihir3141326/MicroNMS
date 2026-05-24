package com.flipkart.learning.controllers;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Error;

import java.util.Map;

@Controller
public class DefaultErrorHandler {

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