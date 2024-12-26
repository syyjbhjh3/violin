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

    private final RedisTemplate<String, KubernetesClient> redisTemplate;

    private final ClusterRepository clusterRepository;

    private final KubeConfigRepository kubeConfigRepository;

    private final Map<Integer, Map<Integer, KubernetesClient>> userClusterClients = new HashMap<>();

    public KubernetesClient connectToCluster(int userId, UUID clusterId) {
        List<KubeConfigEntity> kubeConfigEntities = kubeConfigRepository.findByClusterId(clusterId);

        if (kubeConfigEntities.isEmpty()) {
            throw new RuntimeException("KubeConfig Not found for ClusterId: " + clusterId);
        }

        for (KubeConfigEntity kubeConfigEntity : kubeConfigEntities) {
            String redisKey = clusterId + ":" + kubeConfigEntity.getKubeConfigId();

            KubernetesClient client = redisTemplate.opsForValue().get(redisKey);

            if (client != null) {
                return client;
            } else {

            }

            String kubeconfigData = kubeConfigEntity.getKubeConfigData();

            try {
                KubernetesClient newClient = new KubernetesClientBuilder()
                        .withConfig(kubeconfigData)
                        .build();

                redisTemplate.opsForValue().set(redisKey, newClient);

                userClusterClients
                        .computeIfAbsent(userId, k -> new HashMap<>())
                        .put(clusterId, newClient);

                return newClient;

            } catch (KubernetesClientException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to connect to cluster", e);
            }
        }
    }

    // 클러스터별 연결 상태 확인
    public KubernetesClient getClusterClient(int userId, int clusterId) {
        return userClusterClients.getOrDefault(userId, new HashMap<>()).get(clusterId);
    }

    // 클러스터 연결 종료 및 Redis에서 삭제
    public void disconnectCluster(int userId, int clusterId) {
        String redisKey = "user:" + userId + ":cluster:" + clusterId;
        KubernetesClient client = userClusterClients.getOrDefault(userId, new HashMap<>()).remove(clusterId);

        if (client != null) {
            // 클러스터 연결 종료
            client.close();
            // Redis에서 연결 정보 삭제
            redisTemplate.delete(redisKey);
        }
    }

    // 사용자별로 모든 클러스터 연결 종료
    public void disconnectAllClusters(int userId) {
        Map<Integer, KubernetesClient> userClusters = userClusterClients.getOrDefault(userId, new HashMap<>());
        for (Map.Entry<Integer, KubernetesClient> entry : userClusters.entrySet()) {
            KubernetesClient client = entry.getValue();
            if (client != null) {
                client.close();
            }
        }
        // Redis 및 사용자 클러스터 연결 정보 초기화
        redisTemplate.delete("user:" + userId + ":*");
        userClusterClients.remove(userId);
    }
}
