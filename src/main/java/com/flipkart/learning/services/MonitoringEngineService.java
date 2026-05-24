package com.flipkart.learning.services;

import com.flipkart.learning.models.Device;
import com.flipkart.learning.models.MonitorLog;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import jakarta.inject.Singleton;
import org.jspecify.annotations.NonNull;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

@Singleton
public class MonitoringEngineService implements ApplicationEventListener<ServerStartupEvent> {

    private final MonitorLogService logService;
    private final DeviceService deviceService;

    // The Thread Pool: Handles up to 20 concurrent simulated SSH connections
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(20);

    // A map to track running tasks to cancel tasks or update or delete
    private final Map<String, ScheduledFuture<?>> activeTasks = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public MonitoringEngineService(MonitorLogService logService, DeviceService deviceService) {
        this.logService = logService;
        this.deviceService = deviceService;
    }

    // Runs automatically when your Micronaut server finishes booting up
    // so that existing devices made before starting the server (if any)
    // are automatically added for monitoring
    @Override
    public void onApplicationEvent(@NonNull ServerStartupEvent event) {
        System.out.println("Starting Monitoring Engine...");
        deviceService.getAllDevices().forEach(deviceResp -> {
            deviceService.getDeviceByIp(deviceResp.ip()).ifPresent(this::startOrUpdateMonitoring);
        });
    }

    // start or update monitoring of devices
    public void startOrUpdateMonitoring(Device device) {
        // If a task is already running for this IP or during an update, cancel it first
        stopMonitoring(device.getIp());

        Runnable monitoringTask = () -> {
            try {
                // stimulating "SSH" into the device and get data
                MonitorLog simulatedLog = fetchSimulatedMetrics();

                // Save it to the database
                logService.addLogToDevice(device.getIp(), simulatedLog);
                System.out.println("Ingested metrics for IP: " + device.getIp());

            } catch (Exception e) {
                System.err.println("Failed to fetch metrics for " + device.getIp() + ": " + e.getMessage());
            }
        };

        // Schedule the task to run repeatedly based on the device's specific interval
        ScheduledFuture<?> futureTask = scheduler.scheduleAtFixedRate(
                monitoringTask,
                0, // Start immediately
                device.getPingIntervalInSec(), // The updated interval requested by the user
                TimeUnit.SECONDS
        );

        // Save the task mapped to the ip address so we can stop/update it later
        activeTasks.put(device.getIp(), futureTask);
        System.out.println("Monitoring active for IP: " + device.getIp() + " (Every " + device.getPingIntervalInSec() + "s)");
    }

    // stops the monitoring of given ip
    public void stopMonitoring(String ip) {
        ScheduledFuture<?> activeTask = activeTasks.remove(ip);
        if (activeTask != null) {
            activeTask.cancel(false); // Stop the loop!
            System.out.println("Monitoring stopped for IP: " + ip);
        }
    }

    // random metric data generation
    private MonitorLog fetchSimulatedMetrics() {
        MonitorLog log = new MonitorLog();

        // Generate random usage between 10.00% and 99.99%
        log.setCpuUsage(Math.round((99 * random.nextDouble()) * 100.0) / 100.0);
        log.setMemoryUsage(Math.round((99 * random.nextDouble()) * 100.0) / 100.0);
        log.setDiskUsage(Math.round(((99 * random.nextDouble())) * 100.0) / 100.0);

        // current timestamp of monitoring
        log.setTimestamp(System.currentTimeMillis());

        // Mock uptime
        log.setUptime(System.currentTimeMillis() / 1000L);

        return log;
    }
}