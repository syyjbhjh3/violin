package com.api.kubernetes.k8sResource.service.service;

import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface SvcService {
    ResultDTO retrieve(UUID clusterId);

    ResultDTO retrieveAll(String loginId);
}
