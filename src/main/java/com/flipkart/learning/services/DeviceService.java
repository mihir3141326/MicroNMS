package com.flipkart.learning.services;

import com.flipkart.learning.models.Device;
import com.flipkart.learning.models.DeviceResponse;
import com.flipkart.learning.repositories.DeviceRepository;
import jakarta.inject.Singleton;

import java.util.List;
import java.util.Optional;

@Singleton
public class DeviceService {

    private final DeviceRepository deviceRepository;

    // Micronaut automatically creates the repository object and injects into this class
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device createDevice(Device device) {
        return deviceRepository.insert(device);
    }

    public Optional<Device> getDeviceByIp(String ip) {
        return deviceRepository.findById(ip);
    }

    public boolean updateDevice(String ip, String username, String password, long port, long pingInterval) {
        if (deviceRepository.existsById(ip)) {
            //TODO update device info in scheduler for monitoring
            deviceRepository.updateByIp(ip, username, password, port, pingInterval);
            return true;
        }
        return false;
    }

    public boolean deleteDevice(String ip) {
        if (deviceRepository.existsById(ip)) {
            deviceRepository.deleteById(ip);
            return true;
        }
        return false;
    }

    public List<DeviceResponse> getAllDevices() {

        // fetches all the data include sensitive info like username and password but don't send the info in the response
        List<Device> allDevices = deviceRepository.findAll();

        return allDevices.stream()
                .map(device -> new DeviceResponse(
                        device.getIp(),
                        device.getTag(),
                        device.getPort(),
                        device.getPingIntervalInSec()
                ))
                .toList();
    }
}