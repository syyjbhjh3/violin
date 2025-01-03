package com.api.kubernetes.cluster.controller;

import com.api.kubernetes.cluster.model.dto.KubernetesDTO;
import com.api.kubernetes.cluster.service.ClusterService;
import com.api.kubernetes.common.model.dto.ResultDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/cluster")
@RequiredArgsConstructor
public class ClusterController {

    private final ClusterService clusterService;

    @PostMapping("/connect")
    public ResultDTO connect(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.connect(kubernetesDTO);
    }

    @PostMapping("/status")
    public ResultDTO status(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.datail(kubernetesDTO);
    }

    @PostMapping("/create")
    public ResultDTO create(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.create(kubernetesDTO);
    }

    @PostMapping("/update")
    public ResultDTO update(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.update(kubernetesDTO);
    }

    @PostMapping("/delete")
    public ResultDTO delete(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.delete(kubernetesDTO);
    }

    @PostMapping("/retrieve")
    public ResultDTO retrieve(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.retrieve(kubernetesDTO);
    }

    @PostMapping("/detail")
    public ResultDTO datail(@RequestBody KubernetesDTO kubernetesDTO) {
        return clusterService.datail(kubernetesDTO);
    }
}
