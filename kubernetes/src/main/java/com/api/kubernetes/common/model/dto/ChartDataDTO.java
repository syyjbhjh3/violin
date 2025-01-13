package com.api.kubernetes.common.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChartDataDTO {
    private String clusterName;
    private long currentData;
    private long totalData;
}
