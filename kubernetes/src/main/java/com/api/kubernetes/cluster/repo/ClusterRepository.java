package com.api.kubernetes.cluster.repo;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.common.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClusterRepository extends JpaRepository<ClusterEntity, UUID> {

    ClusterEntity findByClusterId(UUID clusterId);

    List<ClusterEntity> findAllByStatus(Status status);

    List<UUID> findClusterIdByUserId(String userId);

    List<ClusterEntity> findByUserId(String userId);
}
