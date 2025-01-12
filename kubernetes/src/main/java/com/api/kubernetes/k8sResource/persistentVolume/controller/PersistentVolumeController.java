package com.api.kubernetes.k8sResource.persistentVolume.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.deployment.service.DeploymentService;
import com.api.kubernetes.k8sResource.persistentVolume.service.PersistentVolumeService;
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
public class PersistentVolumeController {

    private final PersistentVolumeService persistentVolumeService;

    @GetMapping({"/cluster/{clusterId}/persistentVolume"})
    public ResultDTO retrieve(@PathVariable UUID clusterId) {
        return persistentVolumeService.retrieve(clusterId);
    }

    @GetMapping({"/persistentVolume/{loginId}"})
    public ResultDTO retrieveAll(@PathVariable String loginId) {
        return persistentVolumeService.retrieveAll(loginId);
    }
}
