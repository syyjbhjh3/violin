package com.api.kubernetes.cluster.service;

import com.api.kubernetes.common.model.dto.KubernetesDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;


@Service
public interface ClusterService {
    ResultDTO status(String userId);

    ResultDTO connect(KubernetesDTO kubernetesDTO);

    ResultDTO create(KubernetesDTO kubernetesDTO);

    ResultDTO update(KubernetesDTO kubernetesDTO);

    ResultDTO delete(KubernetesDTO kubernetesDTO);

    ResultDTO retrieve(String userId);

    ResultDTO detail(KubernetesDTO kubernetesDTO);
}
