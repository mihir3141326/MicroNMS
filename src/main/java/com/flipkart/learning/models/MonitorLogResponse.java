package com.flipkart.learning.models;

import io.micronaut.serde.annotation.Serdeable;

@Serdeable
public record MonitorLogResponse(
        Long id,
        long timestamp,
        double cpuUsage,
        double memoryUsage,
        double diskUsage,
        long uptime
) {
}