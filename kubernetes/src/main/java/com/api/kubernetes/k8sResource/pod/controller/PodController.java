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
@RequiredArgsConstructor
public class PodController {

    private final PodService podService;

    @GetMapping({"/cluster/{clusterId}/pod"})
    public ResultDTO retrieveClusterPod(@PathVariable UUID clusterId) {
        return podService.retrieveClusterPod(clusterId);
    }

    @GetMapping({"/pod/{loginId}"})
    public ResultDTO retrieveAll(@PathVariable String loginId) {
        return podService.retrieveAll(loginId);
    }
}
