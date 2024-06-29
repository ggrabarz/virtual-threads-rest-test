package com.example.restsync.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
public class TomcatMetricsController {
    private final MetricsEndpoint metricsEndpoint;

    @GetMapping(value = "/tomcat/metrics", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamTomcatMetrics() {
        SseEmitter emitter = new SseEmitter();
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                while (true) {
                    SseEventBuilder event = getTomcatMetrics();
                    emitter.send(event);
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (Exception e) {
                emitter.completeWithError(e);
            } finally {
                emitter.complete();
            }
        });

        return emitter;
    }

    private SseEventBuilder getTomcatMetrics() {
        var globalRequests = getMetric("tomcat.global.request");
        var threadsBusy = getMetric("tomcat.threads.busy");
        var threadsMax = getMetric("tomcat.threads.config.max");
        var threadsCurrent = getMetric("tomcat.threads.current");

        return SseEmitter.event()
                .data("""
                        Total requests: %s
                        Threads busy: %s
                        Threads max: %s
                        Threads current: %s""".formatted(
                        globalRequests,
                        threadsBusy,
                        threadsMax,
                        threadsCurrent)
                );
    }

    private Double getMetric(String requiredMetricName) {
        return metricsEndpoint.metric(requiredMetricName, null).getMeasurements().getFirst().getValue();
    }
}
