package com.flipkart.learning.models;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.serde.annotation.Serdeable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
