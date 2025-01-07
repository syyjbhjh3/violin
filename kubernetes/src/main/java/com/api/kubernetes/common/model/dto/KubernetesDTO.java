package com.api.kubernetes.common.model.dto;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.model.enums.Type;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class KubernetesDTO {
    /* Cluster */
    private UUID clusterId;
    private String clusterName;
    private Type type;
    private String kubeConfigData;
    private String userId;
    private Status status;

    public ClusterEntity toClusterEntity() {
        return new ClusterEntity(this.clusterId, this.clusterName, this.type, this.kubeConfigData, this.userId, this.status);
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}