package com.flipkart.learning.models;

import io.micronaut.data.annotation.*;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity("monitor_log")
public class MonitorLog {
    @Id
    @GeneratedValue(GeneratedValue.Type.IDENTITY)
    private Long id;

    @Relation(Relation.Kind.MANY_TO_ONE)
    @MappedProperty("device_ip")
    private Device device;  // one device can have multiple logs

    private long timestamp;
    private double cpuUsage;
    private double memoryUsage;
    private double diskUsage;
    private long uptime;

    public MonitorLog() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public long getUptime() {
        return uptime;
    }

    public void setUptime(long uptime) {
        this.uptime = uptime;
    }
}
