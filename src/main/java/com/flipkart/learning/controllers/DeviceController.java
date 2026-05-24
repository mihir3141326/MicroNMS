package com.flipkart.learning.controllers;

import com.flipkart.learning.models.Device;
import com.flipkart.learning.models.DeviceCreateRequest;
import com.flipkart.learning.models.DeviceResponse;
import com.flipkart.learning.models.DeviceUpdateRequest;
import com.flipkart.learning.services.DeviceService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

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
    // @Valid ensures that the constraints defined in the Device Create Request are valid
    @Post("/create")
    public HttpResponse<Device> createDevice(@Body @Valid DeviceCreateRequest request) {

        // Map the safe Data Transfer Object (DTO) back to your database model
        Device newDevice = new Device();
        newDevice.setIp(request.ip());
        newDevice.setUsername(request.username());
        newDevice.setPassword(request.password());
        newDevice.setTag(request.tag());
        newDevice.setPort(request.port());
        newDevice.setPingIntervalInSec(request.pingIntervalInSec());

        Device createdDevice = deviceService.createDevice(newDevice);
        return HttpResponse.created(createdDevice);
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
    public HttpResponse<String> updateDevice(
            // Validates the {ip} in the URL
            @Pattern(regexp = "^([0-9]{1,3}\\.){3}[0-9]{1,3}$", message = "Invalid IP address format") String ip,
            // Validates the JSON body
            @Body @Valid DeviceUpdateRequest request) {

        // If we reach here, BOTH the URL IP and the JSON body are valid.
        boolean updated = deviceService.updateDevice(
                ip,
                request.username(),
                request.password(),
                request.port(),
                request.pingIntervalInSec()
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