package com.api.kubernetes.k8sResource.deployment.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.deployment.service.DeploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
public class DeploymentController {

    private final DeploymentService deploymentService;

    @GetMapping({"/cluster/{clusterId}/deployment"})
    public ResultDTO retrieve(@PathVariable UUID clusterId) {
        return deploymentService.retrieve(clusterId);
    }

    @GetMapping({"/deployment/{loginId}"})
    public ResultDTO retrieveAll(@PathVariable String loginId) {
        return deploymentService.retrieveAll(loginId);
    }
}
