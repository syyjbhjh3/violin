package com.api.kubernetes.k8sResource.persistentVolume.service;

import com.api.kubernetes.common.model.dto.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public interface PersistentVolumeService {
    ResultDTO retrieve(UUID clusterId);

    ResultDTO retrieveAll(String loginId);
}
