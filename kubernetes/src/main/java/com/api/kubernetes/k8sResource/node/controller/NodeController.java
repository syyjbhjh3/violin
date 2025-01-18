package com.api.kubernetes.k8sResource.node.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.node.service.NodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/node")
@RequiredArgsConstructor
public class NodeController {

    private final NodeService nodeService;

    @GetMapping({"/cluster"})
    public ResultDTO retrieve(@RequestHeader("X-Cluster-Id") UUID clusterId) {
        return nodeService.retrieve(clusterId);
    }

    @GetMapping({"/user"})
    public ResultDTO retrieveAll(@RequestHeader("X-User-Id") String userId) {
        return nodeService.retrieveAll(userId);
    }
}
