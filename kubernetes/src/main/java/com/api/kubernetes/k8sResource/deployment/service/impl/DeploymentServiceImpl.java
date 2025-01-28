package com.api.kubernetes.k8sResource.deployment.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import com.api.kubernetes.k8sResource.deployment.model.DeploymentDTO;
import com.api.kubernetes.k8sResource.deployment.service.DeploymentService;
import io.fabric8.kubernetes.api.model.apps.Deployment;
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
public class DeploymentServiceImpl implements DeploymentService {

    /* Repository */
    private final ClusterRepository clusterRepository;

    /* Util */
    private final UserClusterClientManager k8sClientManager;

    private List<DeploymentDTO> retrieveNamespaceList(UUID clusterId) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterId);
        String clusterName = clusterEntity.getClusterName();

        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterEntity.getKubeConfigData());

        return kubernetesClient.apps()
                .deployments()
                .list()
                .getItems()
                .stream()
                .map(deployment -> DeploymentDTO.fromDeployment(deployment, clusterName))
                .collect(Collectors.toList());
    }

    public ResultDTO retrieve(UUID clusterId) {
        List<DeploymentDTO> namespaceList = retrieveNamespaceList(clusterId);
        return new ResultDTO<>(Status.SUCCESS, Message.DEPLOYMENT_SEARCH_SUCCESS.getMessage(), namespaceList);
    }

    public ResultDTO retrieveAll(String loginId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findByUserIdAndStatus(loginId, Status.ENABLE);

        List<DeploymentDTO> deploymentList = clusterEntities.stream()
                .parallel()
                .flatMap(clusterEntity -> retrieveNamespaceList(clusterEntity.getClusterId()).stream())
                .collect(Collectors.toList());

        return new ResultDTO<>(Status.SUCCESS, Message.DEPLOYMENT_SEARCH_SUCCESS.getMessage(), deploymentList);
    }

    public ResultDTO detail(UUID clusterId, String namespace, String deploymentName) {
        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterId);

        Deployment deployment = kubernetesClient.apps().deployments().inNamespace(namespace).withName(deploymentName).get();
        return new ResultDTO<>(Status.SUCCESS, Message.DEPLOYMENT_SEARCH_SUCCESS.getMessage(), deployment);
    }

}
