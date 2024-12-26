package com.api.kubernetes.cluster.model.dto;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import com.api.kubernetes.cluster.model.entity.KubeConfigEntity;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class KubeConfigDTO {
    private long kubeConfigId;
    private UUID clusterId;
    private String kubeConfigName;
    private String kubeConfigData;
    private String kubeConfigType;
    private String status;

    public KubeConfigEntity toEntity() {
        return new KubeConfigEntity(this.clusterId, this.kubeConfigName, this.kubeConfigData, this.kubeConfigType, this.status);
    }

    public void updateKubeConfigStatus(String status) {
        this.status = status;
    }
}