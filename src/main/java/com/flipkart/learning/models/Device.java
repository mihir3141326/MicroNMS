package com.flipkart.learning.models;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;

@Serdeable
@MappedEntity("device")
public class Device{
    @Id
    private String ip;

    private String username;
    private String password;
    private String tag;
    private long port;
    private long pingIntervalInSec;

    public Device(){}

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getPort() {
        return port;
    }

    public void setPort(long port) {
        this.port = port;
    }

    public long getPingIntervalInSec() {
        return pingIntervalInSec;
    }

    public void setPingIntervalInSec(long pingIntervalInSec) {
        this.pingIntervalInSec = pingIntervalInSec;
    }
}
