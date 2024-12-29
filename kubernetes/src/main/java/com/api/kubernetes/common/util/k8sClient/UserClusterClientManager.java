package com.api.kubernetes.common.util.k8sClient;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.model.entity.KubeConfigEntity;
import com.api.kubernetes.cluster.repo.ClusterRepository;
import com.api.kubernetes.cluster.repo.KubeConfigRepository;
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

    private final KubeConfigRepository kubeConfigRepository;

    private final Map<Integer, Map<Integer, KubernetesClient>> userClusterClients = new HashMap<>();

    public void initCluster(String userId) {
        List<ClusterEntity> clusterEntities = clusterRepository.findByUserId(userId);

        for (ClusterEntity clusterEntity : clusterEntities) {
            List<KubeConfigEntity> kubeConfigEntities = kubeConfigRepository.findByClusterId(clusterEntity.getClusterId());

            for (KubeConfigEntity kubeConfigEntity : kubeConfigEntities) {
                if (kubeConfigEntities.isEmpty()) {
                    continue;
                } else {
                    String redisKey = clusterEntity.getClusterId() + ":" + kubeConfigEntity.getKubeConfigId();
                    redisTemplate.opsForValue().set(redisKey, kubeConfigEntity.getKubeConfigData());
                }
            }
        }
    }

    public KubernetesClient getClusterClient(UUID clusterId, String kubeConfigId) {
        String kubeconfigData = redisTemplate.opsForValue().get(clusterId + ":" + kubeConfigId);

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
