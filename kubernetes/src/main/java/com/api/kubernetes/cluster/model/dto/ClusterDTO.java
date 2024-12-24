package com.api.kubernetes.cluster.model.dto;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ClusterDTO {
    private final UUID clusterId;
    private final String clusterName;
    private final String url;
    private final String userId;
    private final String status;
    private final String useYn;

    public ClusterEntity toEntity() {
        return new ClusterEntity();
    } 
}