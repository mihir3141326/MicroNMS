package com.flipkart.learning.models;

import io.micronaut.serde.annotation.Serdeable;

// made this separate class because device info must not include username and password
@Serdeable
public record DeviceResponse(
        String ip,
        String tag,
        long port,
        long pingIntervalInSec
) {
    // A Record automatically generates the constructor and getters for us
}