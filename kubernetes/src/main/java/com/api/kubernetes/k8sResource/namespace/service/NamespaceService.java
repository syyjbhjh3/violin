package com.api.kubernetes.k8sResource.namespace.service;

import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface NamespaceService {
    ResultDTO retrieve(UUID clusterId);

    ResultDTO retrieveAll(String loginId);
}
