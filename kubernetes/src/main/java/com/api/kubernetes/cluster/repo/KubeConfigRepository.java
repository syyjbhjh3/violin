package com.api.kubernetes.cluster.repo;

import com.api.kubernetes.cluster.model.entity.KubeConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface KubeConfigRepository extends JpaRepository<KubeConfigEntity, Long> {
    List<KubeConfigEntity> findByClusterIdIn(List<UUID> clusterIds);

    List<KubeConfigEntity> findByClusterId(UUID clusterId);

    Optional<KubeConfigEntity> findByKubeConfigId(Long kubeConfigId);
}
