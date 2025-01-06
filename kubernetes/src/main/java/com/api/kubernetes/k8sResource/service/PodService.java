package com.api.kubernetes.k8sResource.service;

import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;


@Service
public interface PodService {
    ResultDTO retrieve(Long kubeConfigId);

    ResultDTO retrieveAll(String loginId);
}
