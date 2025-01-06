package com.api.kubernetes.cluster.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.model.entity.KubeConfigEntity;
import com.api.kubernetes.cluster.repo.KubeConfigRepository;
import com.api.kubernetes.common.model.dto.KubernetesDTO;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.cluster.service.ClusterService;
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
    private final KubeConfigRepository kubeConfigRepository;

    /* Util */
    private final WebClient webClient;

    public ResultDTO connect(KubernetesDTO kubernetesDTO) {
        try {
            String kubeconfigData = kubernetesDTO.getKubeconfigData();
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
                    .url(kubernetesDTO.getUrl())
                    .userId(kubernetesDTO.getUserId())
                    .type(kubernetesDTO.getType())
                    .status(Status.ENABLE)
                    .build();

            ClusterEntity clusterEntity = clusterRepository.save(createClutserDTO.toClusterEntity());

            if (clusterEntity != null) {
                /* KubeConfig Save */
                KubernetesDTO createKubeConfigDTO = KubernetesDTO.builder()
                        .clusterId(clusterEntity.getClusterId())
                        .kubeconfigName(kubernetesDTO.getKubeconfigName())
                        .kubeconfigData(kubernetesDTO.getKubeconfigData())
                        .kubeconfigType(kubernetesDTO.getKubeconfigType())
                        .status(Status.ENABLE)
                        .build();

                kubeConfigRepository.save(createKubeConfigDTO.toKubeConfigEntity());
            }
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
                    .url(kubernetesDTO.getUrl())
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
                    .url(clusterEntity.getUrl())
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

        List<UUID> clusterIds = clusterEntities.stream()
                .map(ClusterEntity::getClusterId)
                .collect(Collectors.toList());

        List<KubeConfigEntity> kubeConfigEntities = kubeConfigRepository.findByClusterIdIn(clusterIds);

        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_SEARCH_SUCCESS.getMessage(), kubeConfigEntities);
    }

    public ResultDTO detail(KubernetesDTO kubernetesDTO) {
        Optional<ClusterEntity> clusterEntity = Optional.ofNullable(clusterRepository.findByClusterId(kubernetesDTO.getClusterId()));
        List<KubeConfigEntity> kubeConfigEntities = null;

        if (clusterEntity.isPresent()) {
            kubeConfigEntities = kubeConfigRepository.findByClusterId(clusterEntity.get().getClusterId());
        }
        return new ResultDTO<>(Status.SUCCESS, Message.CLUSTER_SEARCH_SUCCESS.message, kubeConfigEntities);
    }
}
