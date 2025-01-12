package com.api.kubernetes.k8sResource.persistentVolumeClaim.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.persistentVolume.service.PersistentVolumeService;
import com.api.kubernetes.k8sResource.persistentVolumeClaim.service.PersistentVolumeClaimService;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
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
public class PersistentVolumeClaimController {

    private final PersistentVolumeClaimService persistentVolumeClaimService;

    @GetMapping({"/cluster/{clusterId}/persistentVolumeClaim"})
    public ResultDTO retrieve(@PathVariable UUID clusterId) {
        return persistentVolumeClaimService.retrieve(clusterId);
    }

    @GetMapping({"/persistentVolumeClaim/{loginId}"})
    public ResultDTO retrieveAll(@PathVariable String loginId) {
        return persistentVolumeClaimService.retrieveAll(loginId);
    }
}
