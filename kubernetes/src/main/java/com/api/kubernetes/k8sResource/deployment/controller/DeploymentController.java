package com.api.kubernetes.k8sResource.deployment.controller;

import com.api.kubernetes.common.model.dto.ResourceDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Action;
import com.api.kubernetes.common.service.CommonService;
import com.api.kubernetes.k8sResource.deployment.service.DeploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/deployment")
@RequiredArgsConstructor
public class DeploymentController {

    private final DeploymentService deploymentService;

    private final CommonService commonService;

    @GetMapping({"/cluster"})
    public ResultDTO retrieve(@RequestHeader("X-Cluster-Id") UUID clusterId) {
        return deploymentService.retrieve(clusterId);
    }

    @GetMapping({"/user"})
    public ResultDTO retrieveAll(@RequestHeader("X-User-Id") String userId) {
        return deploymentService.retrieveAll(userId);
    }

    @GetMapping({"/namespaces/{namespace}/{deployment}"})
    public ResultDTO detail(
            @RequestHeader("X-Cluster-Id") UUID clusterId,
            @PathVariable String namespace,
            @PathVariable String deployment
    ) {
        return deploymentService.detail(clusterId, namespace, deployment);
    }

    @PostMapping
    public ResultDTO create(@RequestHeader("X-Cluster-Id") UUID clusterId, @RequestBody ResourceDTO resourceDTO) {
        resourceDTO.initInfo(clusterId, Action.CREATE);
        return commonService.resourceProcess(resourceDTO);
    }

    @PutMapping
    public ResultDTO update(@RequestHeader("X-Cluster-Id") UUID clusterId, @RequestBody ResourceDTO resourceDTO) {
        resourceDTO.initInfo(clusterId, Action.UPDATE);
        return commonService.resourceProcess(resourceDTO);
    }

    @DeleteMapping
    public ResultDTO delete(@RequestHeader("X-Cluster-Id") UUID clusterId, @RequestBody ResourceDTO resourceDTO) {
        resourceDTO.initInfo(clusterId, Action.DELETE);
        return commonService.resourceProcess(resourceDTO);
    }
}
