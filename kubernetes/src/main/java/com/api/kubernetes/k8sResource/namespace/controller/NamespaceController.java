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
@RequiredArgsConstructor
public class NamespaceController {

    private final NamespaceService namespaceService;

    @GetMapping({"/cluster/{clusterId}/namespace"})
    public ResultDTO retrieve(@PathVariable UUID clusterId) {
        return namespaceService.retrieve(clusterId);
    }

    @GetMapping({"/namespace/{loginId}"})
    public ResultDTO retrieveAll(@PathVariable String loginId) {
        return namespaceService.retrieveAll(loginId);
    }

    @PostMapping({"/namespace"})
    public ResultDTO create(@RequestBody NamespaceDTO namespaceDTO) {
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_CREATE_SUCCESS.getMessage());
    }
    @PutMapping({"/namespace/{namespace}"})
    public ResultDTO update(@RequestBody NamespaceDTO namespaceDTO) {
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_CREATE_SUCCESS.getMessage());
    }
    @DeleteMapping({"/namespace/{namespace}"})
    public ResultDTO delete(@RequestBody NamespaceDTO namespaceDTO) {
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_CREATE_SUCCESS.getMessage());
    }
}
