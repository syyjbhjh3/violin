package com.api.kubernetes.k8sResource.persistentVolumeClaim.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import com.api.kubernetes.k8sResource.persistentVolumeClaim.model.PersistentVolumeClaimDTO;
import com.api.kubernetes.k8sResource.persistentVolumeClaim.service.PersistentVolumeClaimService;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
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
public class PersistentVolumeClaimServiceImpl implements PersistentVolumeClaimService {

    /* Repository */
    private final ClusterRepository clusterRepository;

    /* Util */
    private final UserClusterClientManager k8sClientManager;

    private List<PersistentVolumeClaimDTO> retrieveNamespaceList(UUID clusterId) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterId);
        String clusterName = clusterEntity.getClusterName();

        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterEntity.getKubeConfigData());

        return kubernetesClient.persistentVolumeClaims()
                .list()
                .getItems()
                .stream()
                .map(persistentVolumeClaims -> PersistentVolumeClaimDTO.fromPersistentVolumeClaim(persistentVolumeClaims, clusterName))
                .collect(Collectors.toList());
    }

    public ResultDTO retrieve(UUID clusterId) {
        List<PersistentVolumeClaimDTO> persistentVolumeClaimsList = retrieveNamespaceList(clusterId);
        return new ResultDTO<>(Status.SUCCESS, Message.PERSISTENTVOLUMECLAIM_SEARCH_SUCCESS.getMessage(), persistentVolumeClaimsList);
    }

    public ResultDTO retrieveAll(String loginId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findByUserIdAndStatus(loginId, Status.ENABLE);

        List<PersistentVolumeClaimDTO> persistentVolumeList = clusterEntities.stream()
                .parallel()
                .flatMap(clusterEntity -> retrieveNamespaceList(clusterEntity.getClusterId()).stream())
                .collect(Collectors.toList());

        return new ResultDTO<>(Status.SUCCESS, Message.PERSISTENTVOLUMECLAIM_SEARCH_SUCCESS.getMessage(), persistentVolumeList);
    }

    public ResultDTO detail(UUID clusterId, String persistentVolumeClaimName) {
        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterId);
        PersistentVolumeClaim persistentVolumeClaim = kubernetesClient.persistentVolumeClaims().withName(persistentVolumeClaimName).get();

        return new ResultDTO<>(Status.SUCCESS, Message.PERSISTENTVOLUMECLAIM_SEARCH_SUCCESS.getMessage(), persistentVolumeClaim);
    }
}
