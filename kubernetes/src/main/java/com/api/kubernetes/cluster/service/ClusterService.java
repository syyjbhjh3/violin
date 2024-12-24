package com.api.kubernetes.cluster.service;

import com.api.kubernetes.cluster.model.dto.ClusterDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;


@Service
public interface ClusterService {
    ResultDTO connect(ClusterDTO clusterDTO);

    ResultDTO create(ClusterDTO clusterDTO);

    ResultDTO update(ClusterDTO clusterDTO);

    ResultDTO delete(ClusterDTO clusterDTO);

    ResultDTO retrieve(ClusterDTO clusterDTO);

    ResultDTO datail(ClusterDTO clusterDTO);
}
