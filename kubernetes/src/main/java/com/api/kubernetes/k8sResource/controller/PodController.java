package com.api.kubernetes.k8sResource.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.service.PodService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/pod")
@RequiredArgsConstructor
public class PodController {

    private final PodService podService;

    @GetMapping({"/{kubeConfigId}", "/{loginId}"})
    public ResultDTO retrieve(
            @PathVariable(required = false) Long kubeConfigId,
            @PathVariable(required = false) String loginId) {

        if (kubeConfigId == null) {
            return podService.retrieveAll(loginId);
        }
        return podService.retrieve(kubeConfigId);
    }
}
