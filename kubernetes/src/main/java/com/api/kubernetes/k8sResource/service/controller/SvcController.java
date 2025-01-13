package com.api.kubernetes.k8sResource.service.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.service.service.SvcService;
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
public class SvcController {

    private final SvcService svcService;

    @GetMapping({"/cluster/{clusterId}/svc"})
    public ResultDTO retrieve(@PathVariable UUID clusterId) {
        return svcService.retrieve(clusterId);
    }

    @GetMapping({"/svc/{loginId}"})
    public ResultDTO retrieveAll(@PathVariable String loginId) {
        return svcService.retrieveAll(loginId);
    }
}
