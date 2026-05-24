package com.flipkart.learning.repositories;

import com.flipkart.learning.models.Device;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import org.jspecify.annotations.NonNull;

import java.util.List;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface DeviceRepository extends CrudRepository<Device, String> {

    Device insert(@NonNull Device device);

    void updateByIp(
            @Id String ip,
            String username,
            String password,
            long port,
            long pingIntervalInSec
    );

    // Add this inside DeviceRepository
    List<Device> findAll();
}