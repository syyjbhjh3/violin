package com.api.kubernetes.k8sResource.node.service;

import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface NodeService {
    ResultDTO retrieve(UUID clusterId);

    ResultDTO retrieveAll(String loginId);

    ResultDTO detail(UUID clusterId, String nodeName);
}
