package com.api.kubernetes.common.util.k8sClient;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import io.fabric8.kubernetes.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserClusterClientManager {

    private final RedisTemplate<String, String> redisTemplate;

    private final ClusterRepository clusterRepository;

    private final Map<Integer, Map<Integer, KubernetesClient>> userClusterClients = new HashMap<>();

    public void initCluster(String userId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findAllByUserId(userId);

        for (ClusterEntity clusterEntity : clusterEntities) {
            redisTemplate.opsForValue().set(
                    clusterEntity.getClusterId().toString(),
                    clusterEntity.getKubeConfigData()
            );
        }
    }

    public KubernetesClient getClusterClient(UUID clusterId) {
        //String kubeconfigData = redisTemplate.opsForValue().get(clusterId.toString());
        String kubeconfigData = clusterRepository.findByClusterId(clusterId).getKubeConfigData();

        KubernetesClient newClient = new KubernetesClientBuilder()
                .withConfig(kubeconfigData)
                .build();

        return newClient;
    }

    public void disconnectCluster(UUID clusterId, String kubeConfigId) {
        String redisKey = clusterId + ":" + kubeConfigId;
        redisTemplate.delete(redisKey);
    }

    public void disconnectAllClusters(int userId) {
        Map<Integer, KubernetesClient> userClusters = userClusterClients.getOrDefault(userId, new HashMap<>());
        for (Map.Entry<Integer, KubernetesClient> entry : userClusters.entrySet()) {
            KubernetesClient client = entry.getValue();
            if (client != null) {
                client.close();
            }
        }
        redisTemplate.delete("user:" + userId + ":*");
        userClusterClients.remove(userId);
    }
}
