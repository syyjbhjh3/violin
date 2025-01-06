package com.api.kubernetes.k8sResource.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.model.entity.KubeConfigEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.cluster.repo.KubeConfigRepository;
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
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PodServiceImpl implements PodService {

    /* Repository */
    KubeConfigRepository kubeConfigRepository;
    ClusterRepository clusterRepository;

    /* Util */
    UserClusterClientManager k8sClientManager;

    private List<PodDTO> retrievePodListByKubeConfigId(Long kubeConfigId) {
        Optional<KubeConfigEntity> kubeConfigEntity = kubeConfigRepository.findByKubeConfigId(kubeConfigId);

        if (kubeConfigEntity.isPresent()) {
            KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(
                    kubeConfigEntity.get().getClusterId(),
                    kubeConfigEntity.get().getKubeConfigData());

            return kubernetesClient.pods()
                    .inAnyNamespace()
                    .list()
                    .getItems()
                    .stream()
                    .map(PodDTO::fromPod)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public ResultDTO retrieve(Long kubeConfigId) {
        List<PodDTO> podDTOList = retrievePodListByKubeConfigId(kubeConfigId);

        if (!podDTOList.isEmpty()) {
            return new ResultDTO<>(Status.SUCCESS, Message.POD_SEARCH_SUCCESS.getMessage(), podDTOList);
        } else {
            return new ResultDTO<>(Status.SUCCESS, Message.POD_SEARCH_NOT_FOUND.getMessage());
        }
    }

    public ResultDTO retrieveAll(String loginId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findAllByUserId(loginId);
        List<PodDTO> podList = new ArrayList<>();

        List<UUID> clusterIds = clusterEntities.stream()
                .map(ClusterEntity::getClusterId)
                .collect(Collectors.toList());

        List<KubeConfigEntity> kubeConfigEntities = kubeConfigRepository.findByClusterIdIn(clusterIds);

        for (KubeConfigEntity kubeConfig : kubeConfigEntities) {
            List<PodDTO> podDTOList = retrievePodListByKubeConfigId(kubeConfig.getKubeConfigId());
            podList.addAll(podDTOList);
        }

        if (!podList.isEmpty()) {
            return new ResultDTO<>(Status.SUCCESS, Message.POD_SEARCH_SUCCESS.getMessage(), podList);
        } else {
            return new ResultDTO<>(Status.SUCCESS, Message.POD_SEARCH_NOT_FOUND.getMessage());
        }
    }
}
