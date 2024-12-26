package com.api.kubernetes.cluster.model.dto;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ClusterDTO {
    private UUID clusterId;
    private String clusterName;
    private String type;
    private String url;
    private String userId;
    private String status;

    public ClusterEntity toEntity() {
        return new ClusterEntity(this.clusterId, this.clusterName, this.type,  this.url, this.userId, this.status);
    }

    public void updateClusterStatus(String status) {
        this.status = status;
    }
}