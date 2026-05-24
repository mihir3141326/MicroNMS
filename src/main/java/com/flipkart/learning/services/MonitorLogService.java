package com.flipkart.learning.services;

import com.flipkart.learning.models.Device;
import com.flipkart.learning.models.MonitorLog;
import com.flipkart.learning.repositories.DeviceRepository;
import com.flipkart.learning.repositories.MonitorLogRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class MonitorLogService {

    private final MonitorLogRepository logRepository;
    private final DeviceRepository deviceRepository;

    public MonitorLogService(MonitorLogRepository logRepository, DeviceRepository deviceRepository) {
        this.logRepository = logRepository;
        this.deviceRepository = deviceRepository;
    }

    public MonitorLog addLogToDevice(String deviceIp, MonitorLog log) {

        Optional<Device> device = deviceRepository.findById(deviceIp);

        if (device.isPresent()) {
            log.setDevice(device.get());
            if (log.getTimestamp() == 0) {
                log.setTimestamp(System.currentTimeMillis());
            }
            return logRepository.save(log);
        } else {
            throw new IllegalArgumentException("Cannot save log: Device with IP " + deviceIp + " not found!");
        }

    }

    public Page<MonitorLog> getLogsByDevice(String ip, int pageNumber, int pageSize) {
        Pageable pageable = Pageable.from(pageNumber, pageSize);
        return logRepository.findByDeviceIp(ip, pageable);
    }

    public List<MonitorLog> getLogsForLast24Hours(String ip) {
        long twentyFourHoursAgo = System.currentTimeMillis() - (24 * 60 * 60 * 1000L);
        return logRepository.findByDeviceIpAndTimestampGreaterThanEquals(ip, twentyFourHoursAgo);
    }

    public List<MonitorLog> getLogsForLastWeek(String ip) {
        // 7 days * 24 hours * 60 mins * 60 secs * 1000 milliseconds
        long oneWeekAgo = System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000L);
        return logRepository.findByDeviceIpAndTimestampGreaterThanEquals(ip, oneWeekAgo);
    }

    public Optional<MonitorLog> getLatestLogForDevice(String ip) {
        return logRepository.findFirstByDeviceIpOrderByTimestampDesc(ip);
    }
}