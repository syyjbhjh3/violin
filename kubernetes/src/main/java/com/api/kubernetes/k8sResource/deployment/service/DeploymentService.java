package com.api.kubernetes.k8sResource.deployment.service;

import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface DeploymentService {
    ResultDTO retrieve(UUID clusterId);

    ResultDTO retrieveAll(String loginId);
}
