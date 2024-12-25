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
    private String url;
    private String userId;
    private String status;
    private String useYn;

    public ClusterEntity toEntity() {
        return new ClusterEntity(this.clusterId, this.clusterName, this.url, this.userId, this.status, this.useYn);
    }

    public void updateClusterUseYn(String useYn) {
        this.useYn = useYn;
    }

    public void updateClusterStatus(String useYn) {
        this.useYn = useYn;
    }
}