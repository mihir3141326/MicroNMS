package com.flipkart.learning.models;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.*;

@Serdeable
public record DeviceCreateRequest(

        @NotBlank(message = "IP address is required")
        @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Invalid IP address format")
        String ip,

        @NotBlank(message = "Username cannot be empty")
        String username,

        @NotBlank(message = "Password cannot be empty")
        String password,

        String tag, // Optional, so no annotations needed

        @NotNull(message = "Port cannot be empty")
        @Positive(message = "Port must be a positive number")
        Long port,

        @NotNull(message = "Pint Interval cannot be empty")
        @Min(value = 5, message = "Ping Interval must be at least 5 seconds")
        @Max(value = 3600, message = "Ping Interval cannot exceed 3600 seconds")
        Long pingIntervalInSec
) { }