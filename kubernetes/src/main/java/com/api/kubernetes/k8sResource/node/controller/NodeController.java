package com.api.kubernetes.k8sResource.node.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.node.service.NodeService;
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
public class NodeController {

    private final NodeService nodeService;

    @GetMapping({"/cluster/{clusterId}/node"})
    public ResultDTO retrieveClusterNode(@PathVariable UUID clusterId) {
        return nodeService.retrieveClusterNode(clusterId);
    }

    @GetMapping({"/node/{loginId}"})
    public ResultDTO retrieveAll(@PathVariable String loginId) {
        return nodeService.retrieveAll(loginId);
    }
}
