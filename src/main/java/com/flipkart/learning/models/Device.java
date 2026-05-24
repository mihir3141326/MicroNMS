package com.flipkart.learning.models;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Serdeable
@Entity
public class Device{
    @Id
    private Long IP;
    private String username;
    private String password;
    private String tag;
    private long port;
    private long pingIntervalInSec;

    public Device(){}

    public Long getId() {
        return IP;
    }
}
