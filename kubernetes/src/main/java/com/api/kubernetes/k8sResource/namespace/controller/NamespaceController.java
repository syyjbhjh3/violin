package com.api.kubernetes.k8sResource.namespace.controller;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.k8sResource.namespace.service.NamespaceService;
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
}
