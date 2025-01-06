package com.api.kubernetes.pod.controller;

import com.api.kubernetes.common.model.dto.KubernetesDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.pod.service.PodService;
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

    @PostMapping("/retrieve")
    public ResultDTO retrieve(@RequestBody KubernetesDTO kubernetesDTO) {
        return podService.retrieve(kubernetesDTO);
    }

}
