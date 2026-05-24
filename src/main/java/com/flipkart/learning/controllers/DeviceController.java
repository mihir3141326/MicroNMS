package com.flipkart.learning.controllers;

import com.flipkart.learning.models.Device;
import com.flipkart.learning.models.DeviceResponse;
import com.flipkart.learning.services.DeviceService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.Optional;

// base url for my class is /device so like localhost:port/devices
@Controller("/devices")
public class DeviceController {

    private final DeviceService deviceService;

    // Micronaut automatically injects your Service here
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    // POST request to http://localhost:8080/devices
    @Post
    public HttpResponse<Device> createDevice(@Body Device device) {
        Device createdDevice = deviceService.createDevice(device);
        return HttpResponse.created(createdDevice); // Returns HTTP 201
    }

    // GET A DEVICE BY IP
    // GET to http://localhost:8080/devices/192.168.1.10
    @Get("/{ip}")
    public HttpResponse<Device> getDevice(String ip) {
        Optional<Device> device = deviceService.getDeviceByIp(ip);

        if(device.isPresent())
            return HttpResponse.ok(device.get());   //200
        else
            return HttpResponse.notFound(); //404

    }

    // UPDATE A DEVICE
    // PUT to http://localhost:8080/devices/192.168.1.10
    @Put("/{ip}")
    public HttpResponse<String> updateDevice(String ip, @Body Device device) {
        boolean updated = deviceService.updateDevice(
                ip,
                device.getUsername(),
                device.getPassword(),
                device.getPort(),
                device.getPingIntervalInSec()
        );

        if (updated) {
            return HttpResponse.ok("Device updated successfully.");
        } else {
            return HttpResponse.notFound("Cannot update: Device not found.");
        }
    }

    // GET to http://localhost:8080/devices
    @Get
    public HttpResponse<List<DeviceResponse>> getAllDevices() {
        List<DeviceResponse> devices = deviceService.getAllDevices();
        return HttpResponse.ok(devices);
    }

    // DELETE A DEVICE
    // DELETE to http://localhost:8080/devices/192.168.1.10
    @Delete("/{ip}")
    public HttpResponse<String> deleteDevice(String ip) {
        boolean deleted = deviceService.deleteDevice(ip);

        if (deleted) {
            return HttpResponse.ok("Device deleted successfully.");
        } else {
            return HttpResponse.notFound("Cannot delete: Device not found.");
        }
    }
}