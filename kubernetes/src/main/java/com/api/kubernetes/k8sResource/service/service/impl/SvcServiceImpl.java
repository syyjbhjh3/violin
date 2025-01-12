package com.api.kubernetes.k8sResource.service.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import com.api.kubernetes.k8sResource.service.model.ServiceDTO;
import com.api.kubernetes.k8sResource.service.service.SvcService;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SvcServiceImpl implements SvcService {

    /* Repository */
    private final ClusterRepository clusterRepository;

    /* Util */
    private final UserClusterClientManager k8sClientManager;

    private List<ServiceDTO> retrieveNamespaceList(UUID clusterId) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterId);
        String clusterName = clusterEntity.getClusterName();

        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterEntity.getClusterId());

        return kubernetesClient.services()
                .list()
                .getItems()
                .stream()
                .map(svc -> ServiceDTO.fromService(svc, clusterName))
                .collect(Collectors.toList());
    }

    public ResultDTO retrieve(UUID clusterId) {
        List<ServiceDTO> namespaceList = retrieveNamespaceList(clusterId);
        return new ResultDTO<>(Status.SUCCESS, Message.PERSISTENTVOLUME_SEARCH_SUCCESS.getMessage(), namespaceList);
    }

    public ResultDTO retrieveAll(String loginId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findByUserIdAndStatus(loginId, Status.ENABLE);

        List<ServiceDTO> persistentVolumeList = clusterEntities.stream()
                .parallel()
                .flatMap(clusterEntity -> retrieveNamespaceList(clusterEntity.getClusterId()).stream())
                .collect(Collectors.toList());

        return new ResultDTO<>(Status.SUCCESS, Message.PERSISTENTVOLUME_SEARCH_SUCCESS.getMessage(), persistentVolumeList);
    }

}
