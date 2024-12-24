package com.api.kubernetes.cluster.repo;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClusterRepository extends JpaRepository<ClusterEntity, String> {

    ClusterEntity findByClusterId(UUID clusterId);
}
