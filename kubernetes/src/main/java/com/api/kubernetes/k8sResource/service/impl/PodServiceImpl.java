package com.api.kubernetes.k8sResource.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import com.api.kubernetes.k8sResource.model.PodDTO;
import com.api.kubernetes.k8sResource.service.PodService;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

    /* Repository */
    private final ClusterRepository clusterRepository;

    /* Util */
    private final UserClusterClientManager k8sClientManager;

    private List<PodDTO> retrievePodList(UUID clusterId) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterId);

        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterEntity.getClusterId());

        return kubernetesClient.pods()
                .inAnyNamespace()
                .list()
                .getItems()
                .stream()
                .map(PodDTO::fromPod)
                .collect(Collectors.toList());
    }

    public ResultDTO retrieveClusterPod(UUID clusterId) {
        List<PodDTO> podList = retrievePodList(clusterId);
        return new ResultDTO<>(Status.SUCCESS, Message.POD_SEARCH_NOT_FOUND.getMessage(), podList);
    }

    public ResultDTO retrieveAll(String loginId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findByUserId(loginId);
        List<PodDTO> podList = new ArrayList<>();

        for (ClusterEntity clusterEntity : clusterEntities) {
            List<PodDTO> podDTOList = retrievePodList(clusterEntity.getClusterId());
            podList.addAll(podDTOList);
        }
        return new ResultDTO<>(Status.SUCCESS, Message.POD_SEARCH_NOT_FOUND.getMessage(), podList);
    }
}
