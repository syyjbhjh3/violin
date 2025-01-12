package com.api.kubernetes.k8sResource.pod.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import com.api.kubernetes.k8sResource.pod.model.PodDTO;
import com.api.kubernetes.k8sResource.pod.service.PodService;
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
public class PodServiceImpl implements PodService {

    /* Repository */
    private final ClusterRepository clusterRepository;

    /* Util */
    private final UserClusterClientManager k8sClientManager;

    private List<PodDTO> retrievePodList(UUID clusterId) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterId);
        String clusterName = clusterEntity.getClusterName();

        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterEntity.getClusterId());

        return kubernetesClient.pods()
                .inAnyNamespace()
                .list()
                .getItems()
                .stream()
                .map(pod -> PodDTO.fromPod(pod, clusterName))
                .collect(Collectors.toList());
    }

    public ResultDTO retrieve(UUID clusterId) {
        List<PodDTO> podList = retrievePodList(clusterId);
        return new ResultDTO<>(Status.SUCCESS, Message.POD_SEARCH_SUCCESS.getMessage(), podList);
    }

    public ResultDTO retrieveAll(String loginId) {
        /* 병렬 스트림으로 변경하니 254 -> 166밀리초 */
        List<ClusterEntity> clusterEntities = clusterRepository.findByUserIdAndStatus(loginId, Status.ENABLE);

        List<PodDTO> podList = clusterEntities.stream()
                .parallel()
                .flatMap(clusterEntity -> retrievePodList(clusterEntity.getClusterId()).stream())
                .collect(Collectors.toList());

        return new ResultDTO<>(Status.SUCCESS, Message.POD_SEARCH_SUCCESS.getMessage(), podList);
    }
}
