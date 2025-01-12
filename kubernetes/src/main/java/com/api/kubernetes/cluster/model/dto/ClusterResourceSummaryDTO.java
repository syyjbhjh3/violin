package com.api.kubernetes.cluster.model.dto;

import com.api.kubernetes.common.model.dto.ChartDataDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ClusterResourceSummaryDTO {
    private int totalClusters;
    private int totalNodes;
    private int totalNamespaces;
    private int totalPods;
    private int totalServices;
    private int totalDeployments;
    private List<ChartDataDTO> nodePieChart;
    private List<ChartDataDTO> podPieChart;
}
