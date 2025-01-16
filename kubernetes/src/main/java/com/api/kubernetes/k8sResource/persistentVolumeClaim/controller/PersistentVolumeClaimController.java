package com.api.kubernetes.k8sResource.persistentVolumeClaim.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.persistentVolumeClaim.service.PersistentVolumeClaimService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/persistentVolumeClaim")
@RequiredArgsConstructor
public class PersistentVolumeClaimController {

    private final PersistentVolumeClaimService persistentVolumeClaimService;

    @GetMapping({"/cluster"})
    public ResultDTO retrieve(@RequestHeader("X-Cluster-Id") UUID clusterId) {
        return persistentVolumeClaimService.retrieve(clusterId);
    }

    @GetMapping({"/user"})
    public ResultDTO retrieveAll(@RequestHeader("X-User-Id") String userId) {
        return persistentVolumeClaimService.retrieveAll(userId);
    }
}
