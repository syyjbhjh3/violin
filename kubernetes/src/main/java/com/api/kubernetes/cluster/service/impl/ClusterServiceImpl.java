package com.api.kubernetes.cluster.service.impl;

import com.api.kubernetes.cluster.model.dto.ClusterResourceSummaryDTO;
import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.common.model.dto.KubernetesDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.cluster.service.ClusterService;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusterServiceImpl implements ClusterService {

    /* Repository */
    private final ClusterRepository clusterRepository;

    /* Util */
    private final UserClusterClientManager k8sClientManager;

    public ResultDTO status(String userId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findAllByUserId(userId);

        int totalNodes = 0;
        int totalPods = 0;
        int totalServices = 0;
        int totalDeployments = 0;
        int totalNamespaces = 0;

        for (ClusterEntity clusterEntity : clusterEntities.parallelStream().toList()) {
            KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterEntity.getClusterId());
            totalNodes += kubernetesClient.nodes().list().getItems().size();
            totalNamespaces += kubernetesClient.namespaces().list().getItems().size();
            totalPods += kubernetesClient.pods().inAnyNamespace().list().getItems().size();
            totalServices += kubernetesClient.services().inAnyNamespace().list().getItems().size();
            totalDeployments += kubernetesClient.apps().deployments().inAnyNamespace().list().getItems().size();
        }

        ClusterResourceSummaryDTO totalSummary = ClusterResourceSummaryDTO.builder()
                .totalClusters(clusterEntities.size())
                .totalNodes(totalNodes)
                .totalNamespaces(totalNamespaces)
                .totalPods(totalPods)
                .totalServices(totalServices)
                .totalDeployments(totalDeployments)
                .build();

        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_STATUS_SUCCESS.getMessage(), totalSummary);
    }

    public ResultDTO connect(KubernetesDTO kubernetesDTO) {
        try {
            String kubeconfigData = kubernetesDTO.getKubeConfigData();
            Config config = Config.fromKubeconfig(kubeconfigData);

            try (KubernetesClient client = new DefaultKubernetesClient(config)) {
                String message = client.namespaces().list().getItems().size() > 0
                        ? Message.CLUSTER_CONNECT_SUCCESS.getMessage()
                        : Message.CLUSTER_CONNECT_FAIL.getMessage();
                return new ResultDTO<>(Status.SUCCESS, message);
            }
        } catch (Exception e) {
            log.error("Cluster connection failed: ", e);
            return new ResultDTO<>(Status.ERROR, e.getMessage());
        }
    }

    public ResultDTO create(KubernetesDTO kubernetesDTO) {
        /* Connect Test K8S */
        ResultDTO connResponse = connect(kubernetesDTO);

        if (connResponse.getResult().equals(Status.SUCCESS)) {
            KubernetesDTO createClutserDTO = kubernetesDTO.builder()
                    .clusterName(kubernetesDTO.getClusterName())
                    .kubeConfigData(kubernetesDTO.getKubeConfigData())
                    .userId(kubernetesDTO.getUserId())
                    .type(kubernetesDTO.getType())
                    .status(Status.ENABLE)
                    .build();

            clusterRepository.save(createClutserDTO.toClusterEntity());
        } else {
            return connResponse;
        }
        return new ResultDTO(
                Status.SUCCESS,
                Message.CLUSTER_CREATE_SUCCESS.message);
    }

    public ResultDTO update(KubernetesDTO kubernetesDTO) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(kubernetesDTO.getClusterId());

        if (clusterEntity != null) {
            KubernetesDTO updateDto = kubernetesDTO.builder()
                    .clusterId(clusterEntity.getClusterId())
                    .clusterName(kubernetesDTO.getClusterName())
                    .kubeConfigData(clusterEntity.getKubeConfigData())
                    .userId(clusterEntity.getUserId())
                    .status(clusterEntity.getStatus())
                    .build();

            ClusterEntity updateEntity = updateDto.toClusterEntity();
            clusterRepository.save(updateEntity);

            return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_UPDATE_SUCCESS.message);
        } else {
            return new ResultDTO<>(Status.FAIL, Message.CLUSTER_UPDATE_FAIL.message);
        }
    }

    public ResultDTO delete(KubernetesDTO kubernetesDTO) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(kubernetesDTO.getClusterId());

        if (clusterEntity != null) {
            KubernetesDTO deleteDto = kubernetesDTO.builder()
                    .clusterId(clusterEntity.getClusterId())
                    .clusterName(clusterEntity.getClusterName())
                    .kubeConfigData(clusterEntity.getKubeConfigData())
                    .userId(clusterEntity.getUserId())
                    .status(clusterEntity.getStatus())
                    .build();

            ClusterEntity deleteEntity = deleteDto.toClusterEntity();
            clusterRepository.save(deleteEntity);

            return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_DELETE_SUCCESS.message);
        } else {
            return new ResultDTO<>(Status.FAIL, Message.CLUSTER_DELETE_FAIL.message);
        }
    }

    public ResultDTO retrieve(String userId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findAllByUserId(userId);

        if (clusterEntities.isEmpty()) {
            return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_SEARCH_NOT_FOUND.getMessage());
        }
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_SEARCH_SUCCESS.getMessage(), clusterEntities);
    }

    public ResultDTO detail(KubernetesDTO kubernetesDTO) {
        Optional<ClusterEntity> clusterEntity = Optional.ofNullable(clusterRepository.findByClusterId(kubernetesDTO.getClusterId()));

        if (clusterEntity.isPresent()) {

        }
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_SEARCH_SUCCESS.message, clusterEntity.get());
    }
}
