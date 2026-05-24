package com.flipkart.learning.models;

import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Serdeable
@MappedEntity("monitor_log")
public class MonitorLog {
    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Long id;

    @Relation(Relation.Kind.MANY_TO_ONE)
    private Device device;  // one device can have multiple logs

    private long timestamp;
    private double cpuUsage;
    private double memoryUsage;
    private double diskUsage;
    private long uptime;

    public MonitorLog() {}


}
