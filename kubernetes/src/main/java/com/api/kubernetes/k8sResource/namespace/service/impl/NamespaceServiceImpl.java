package com.api.kubernetes.k8sResource.namespace.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import com.api.kubernetes.k8sResource.namespace.model.NamespaceDTO;
import com.api.kubernetes.k8sResource.namespace.service.NamespaceService;
import io.fabric8.kubernetes.api.model.Namespace;
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
public class NamespaceServiceImpl implements NamespaceService {

    /* Repository */
    private final ClusterRepository clusterRepository;

    /* Util */
    private final UserClusterClientManager k8sClientManager;

    private List<NamespaceDTO> retrieveNamespaceList(UUID clusterId) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterId);
        String clusterName = clusterEntity.getClusterName();

        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterEntity.getKubeConfigData());

        return kubernetesClient.namespaces()
                .list()
                .getItems()
                .stream()
                .map(node -> NamespaceDTO.fromNamespace(node, clusterName))
                .collect(Collectors.toList());
    }

    public ResultDTO retrieve(UUID clusterId) {
        List<NamespaceDTO> namespaceList = retrieveNamespaceList(clusterId);
        return new ResultDTO<>(Status.SUCCESS, Message.NAMESPACE_SEARCH_SUCCESS.getMessage(), namespaceList);
    }

    public ResultDTO retrieveAll(String loginId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findByUserIdAndStatus(loginId, Status.ENABLE);

        List<NamespaceDTO> namespaceList = clusterEntities.stream()
                .parallel()
                .flatMap(clusterEntity -> retrieveNamespaceList(clusterEntity.getClusterId()).stream())
                .collect(Collectors.toList());

        return new ResultDTO<>(Status.SUCCESS, Message.NAMESPACE_SEARCH_SUCCESS.getMessage(), namespaceList);
    }

    public ResultDTO detail(UUID clusterId, String namespaceName) {
        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterId);
        Namespace namespace = kubernetesClient.namespaces().withName(namespaceName).get();

        return new ResultDTO<>(Status.SUCCESS, Message.NAMESPACE_SEARCH_SUCCESS.getMessage(), namespace);
    }
}
