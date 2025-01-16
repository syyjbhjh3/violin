package com.api.kubernetes.k8sResource.pod.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.pod.service.PodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/pod")
@RequiredArgsConstructor
public class PodController {

    private final PodService podService;

    @GetMapping({"/cluster"})
    public ResultDTO retrieve(@RequestHeader("X-Cluster-Id") UUID clusterId) {
        return podService.retrieve(clusterId);
    }

    @GetMapping({"/user"})
    public ResultDTO retrieveAll(@RequestHeader("X-User-Id") String userId) {
        return podService.retrieveAll(userId);
    }
}
