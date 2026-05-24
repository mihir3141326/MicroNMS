package com.flipkart.learning.models;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Serdeable
public record DeviceUpdateRequest(
        @NotBlank(message = "Username cannot be empty")
        String username,

        @NotBlank(message = "Password cannot be empty")
        String password,

        String tag,

        @Positive(message = "Port must be a positive number")
        long port,

        @Min(value = 5, message = "Ping interval must be at least 5 seconds")
        @Max(value = 3600, message = "Ping interval cannot exceed 3600 seconds")
        long pingIntervalInSec
) {}