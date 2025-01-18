package com.api.kubernetes.k8sResource.persistentVolume.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.persistentVolume.service.PersistentVolumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/persistentVolume")
@RequiredArgsConstructor
public class PersistentVolumeController {

    private final PersistentVolumeService persistentVolumeService;

    @GetMapping({"/cluster"})
    public ResultDTO retrieve(@RequestHeader("X-Cluster-Id") UUID clusterId) {
        return persistentVolumeService.retrieve(clusterId);
    }

    @GetMapping({"/user"})
    public ResultDTO retrieveAll(@RequestHeader("X-User-Id") String userId) {
        return persistentVolumeService.retrieveAll(userId);
    }
}
