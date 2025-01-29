package com.api.kubernetes.common.model.dto;

import com.api.kubernetes.common.model.enums.Action;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ResourceDTO {
    private UUID clusterId;
    private String resourceYaml;
    private String namespace;
    private Action action;

    public void initInfo(UUID clusterId, Action action){
        this.clusterId = clusterId;
        this.action = action;
    }
}
