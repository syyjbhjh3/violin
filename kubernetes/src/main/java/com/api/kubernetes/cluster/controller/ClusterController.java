package com.api.kubernetes.cluster.controller;

import com.api.kubernetes.cluster.model.dto.ClusterDTO;
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
    public ResultDTO connect(@RequestBody ClusterDTO clusterDTO) {
        return clusterService.connect(clusterDTO);
    }

    @PostMapping("/create")
    public ResultDTO create(@RequestBody ClusterDTO clusterDTO) {
        return clusterService.create(clusterDTO);
    }

    @PostMapping("/update")
    public ResultDTO update(@RequestBody ClusterDTO clusterDTO) {
        return clusterService.update(clusterDTO);
    }

    @PostMapping("/delete")
    public ResultDTO delete(@RequestBody ClusterDTO clusterDTO) {
        return clusterService.delete(clusterDTO);
    }

    @PostMapping("/retrieve")
    public ResultDTO retrieve(@RequestBody ClusterDTO clusterDTO) {
        return clusterService.retrieve(clusterDTO);
    }

    @PostMapping("/detail")
    public ResultDTO datail(@RequestBody ClusterDTO clusterDTO) {
        return clusterService.datail(clusterDTO);
    }
}
