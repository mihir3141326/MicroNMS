package com.flipkart.learning.controllers;

import com.flipkart.learning.models.MonitorLog;
import com.flipkart.learning.services.MonitorLogService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller("/monitor/{ip}")
public class MonitorLogController {

    private final MonitorLogService logService;

    public MonitorLogController(MonitorLogService logService) {
        this.logService = logService;
    }

    // Insert a new log (internally used by our plugin to submit monitor log)
    // POST to http://localhost:8080/monitor/192.168.1.10
    @Post
    public HttpResponse<?> addLog(String ip, @Body MonitorLog log) {
        try {
            MonitorLog savedLog = logService.addLogToDevice(ip, log);
            return HttpResponse.created(savedLog);
        } catch (IllegalArgumentException e) {
            // Catches the error if the Device IP doesn't exist in the database!
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    // latest log
    // GET to http://localhost:8080/monitor/192.168.1.10/
    @Get
    public HttpResponse<MonitorLog> getLatestLog(String ip) {
        Optional<MonitorLog> latestLog = logService.getLatestLogForDevice(ip);

        if (latestLog.isPresent()) {
            return HttpResponse.ok(latestLog.get());
        } else {
            return HttpResponse.notFound();
        }
    }

    // Get last 24 hr monitoring logs
    // GET to http://localhost:8080/monitor/192.168.1.10/day
    @Get("/day")
    public HttpResponse<List<MonitorLog>> getLogsForLast24Hours(String ip) {
        List<MonitorLog> logs = logService.getLogsForLast24Hours(ip);
        return HttpResponse.ok(logs);
    }

    // Get last week's logs
    // GET to http://localhost:8080/monitor/192.168.1.10/week
    @Get("/week")
    public HttpResponse<List<MonitorLog>> getLogsForLastWeek(String ip) {
        List<MonitorLog> logs = logService.getLogsForLastWeek(ip);
        return HttpResponse.ok(logs);
    }
}