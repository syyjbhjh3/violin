package com.api.kubernetes.cluster.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClusterResourceSummaryDTO {
    private int totalClusters;
    private int totalNodes;
    private int totalNamespaces;
    private int totalPods;
    private int totalServices;
    private int totalDeployments;
}
