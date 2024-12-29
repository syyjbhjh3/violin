package com.api.kubernetes.cluster.repo;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ClusterRepository extends JpaRepository<ClusterEntity, UUID> {

    ClusterEntity findByClusterId(UUID clusterId);

    List<ClusterEntity> findAllByStatus(String status);

    List<ClusterEntity> findByUserId(String userId);
}
