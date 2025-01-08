package com.api.kubernetes.k8sResource.node.model;

import io.fabric8.kubernetes.api.model.Node;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NodeDTO {
    private String name;
    private String status;
    private String clusterName;
    private String role;
    private double cpu;
    private double memory;
    private String creationTimestamp;

    public static NodeDTO fromNode(Node node, String clusterName) {
        return NodeDTO.builder()
                .name(node.getMetadata().getName())
                .status(node.getStatus().getPhase())
                .clusterName(clusterName)
                .role(node.getMetadata().getLabels().get("kubernetes.io/role"))
                .cpu(parseCpu(String.valueOf(node.getStatus().getCapacity().get("cpu"))))
                .memory(parseMemory(String.valueOf(node.getStatus().getCapacity().get("memory"))))
                .creationTimestamp(node.getMetadata().getCreationTimestamp())
                .build();
    }

    private static double parseCpu(String cpuCapacity) {
        return Double.parseDouble(cpuCapacity);
    }

    private static double parseMemory(String memoryCapacity) {
        if (memoryCapacity.endsWith("Gi")) {
            return Double.parseDouble(memoryCapacity.replace("Gi", ""));
        }
        return 0.0;
    }
}
