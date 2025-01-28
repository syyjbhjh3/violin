package com.api.kubernetes.k8sResource.service.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.service.service.SvcService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/svc")
@RequiredArgsConstructor
public class SvcController {

    private final SvcService svcService;

    @GetMapping({"/cluster"})
    public ResultDTO retrieve(@RequestHeader("X-Cluster-Id") UUID clusterId) {
        return svcService.retrieve(clusterId);
    }

    @GetMapping({"/user"})
    public ResultDTO retrieveAll(@RequestHeader("X-User-Id") String userId) {
        return svcService.retrieveAll(userId);
    }

    @GetMapping({"/namespaces/{namespace}/{service}"})
    public ResultDTO detail(
            @RequestHeader("X-Cluster-Id") UUID clusterId,
            @PathVariable String namespace,
            @PathVariable String service
    ) {
        return svcService.detail(clusterId, namespace, service);
    }
}
