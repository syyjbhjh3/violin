package com.api.kubernetes.k8sResource.node.service.impl;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Message;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.util.k8sClient.UserClusterClientManager;
import com.api.kubernetes.k8sResource.node.model.NodeDTO;
import com.api.kubernetes.k8sResource.node.service.NodeService;
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
public class NodeServiceImpl implements NodeService {

    /* Repository */
    private final ClusterRepository clusterRepository;

    /* Util */
    private final UserClusterClientManager k8sClientManager;

    private List<NodeDTO> retrieveNodeList(UUID clusterId) {
        ClusterEntity clusterEntity = clusterRepository.findByClusterId(clusterId);
        String clusterName = clusterEntity.getClusterName();

        KubernetesClient kubernetesClient = k8sClientManager.getClusterClient(clusterEntity.getClusterId());

        return kubernetesClient.nodes()
                .list()
                .getItems()
                .stream()
                .map(node -> NodeDTO.fromNode(node, clusterName))
                .collect(Collectors.toList());
    }

    public ResultDTO retrieve(UUID clusterId) {
        List<NodeDTO> nodeList = retrieveNodeList(clusterId);
        return new ResultDTO<>(Status.SUCCESS, Message.NODE_SEARCH_SUCCESS.getMessage(), nodeList);
    }

    public ResultDTO retrieveAll(String loginId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findByUserId(loginId);

        List<NodeDTO> nodeList = clusterEntities.stream()
                .parallel()
                .flatMap(clusterEntity -> retrieveNodeList(clusterEntity.getClusterId()).stream())
                .collect(Collectors.toList());

        return new ResultDTO<>(Status.SUCCESS, Message.NODE_SEARCH_SUCCESS.getMessage(), nodeList);
    }

}
