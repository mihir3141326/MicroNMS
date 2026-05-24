package com.flipkart.learning.repositories;

import com.flipkart.learning.models.MonitorLog;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface MonitorLogRepository extends CrudRepository<MonitorLog, Long> {

    // Overriding standard methods to include Device data
    @Join("device")
    Optional<MonitorLog> findById(@NonNull Long id);

    @Join("device")
    List<MonitorLog> findAll();

    @Join("device")
    Page<MonitorLog> findByDeviceIp(String ip, Pageable pageable);

    @Join("device")
    List<MonitorLog> findByDeviceIpAndTimestampGreaterThanEquals(String ip, long timestamp);

}