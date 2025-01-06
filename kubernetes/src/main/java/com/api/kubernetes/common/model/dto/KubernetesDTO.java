package com.api.kubernetes.common.model.dto;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.model.entity.KubeConfigEntity;
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
    private String url;
    private String userId;
    private Status status;

    /* KubeConfig */
    private String kubeconfigName;
    private String kubeconfigType;
    private String kubeconfigData;

    public ClusterEntity toClusterEntity() {
        return new ClusterEntity(this.clusterId, this.clusterName, this.type,  this.url, this.userId, this.status);
    }

    public KubeConfigEntity toKubeConfigEntity() {
        return new KubeConfigEntity(this.clusterId, this.kubeconfigName, this.kubeconfigData,  this.kubeconfigType, this.status);
    }

    public void updateStatus(Status status) {
        this.status = status;
    }
}