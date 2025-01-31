package com.api.kubernetes.k8sResource.namespace.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.k8sResource.namespace.model.NamespaceDTO;
import com.api.kubernetes.k8sResource.namespace.service.NamespaceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/namespace")
@RequiredArgsConstructor
public class NamespaceController {

    private final NamespaceService namespaceService;

    @GetMapping({"/cluster"})
    public ResultDTO retrieve(@RequestHeader("X-Cluster-Id") UUID clusterId) {
        return namespaceService.retrieve(clusterId);
    }

    @GetMapping({"/user"})
    public ResultDTO retrieveAll(@RequestHeader("X-User-Id") String userId) {
        return namespaceService.retrieveAll(userId);
    }

    @GetMapping({"/{namespace}"})
    public ResultDTO detail(
            @RequestHeader("X-Cluster-Id") UUID clusterId,
            @PathVariable String namespace) {
        return namespaceService.detail(clusterId, namespace);
    }

    @PostMapping
    public ResultDTO create(@RequestBody NamespaceDTO namespaceDTO) {
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_CREATE_SUCCESS.getMessage());
    }
    @PutMapping({"{namespace}"})
    public ResultDTO update(@RequestBody NamespaceDTO namespaceDTO) {
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_CREATE_SUCCESS.getMessage());
    }
    @DeleteMapping({"{namespace}"})
    public ResultDTO delete(@RequestBody NamespaceDTO namespaceDTO) {
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_CREATE_SUCCESS.getMessage());
    }
}
