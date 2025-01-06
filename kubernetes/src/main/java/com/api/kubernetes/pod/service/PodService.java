package com.api.kubernetes.pod.service;

import com.api.kubernetes.common.model.dto.KubernetesDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;


@Service
public interface PodService {
    ResultDTO retrieve(KubernetesDTO kubernetesDTO);
}
