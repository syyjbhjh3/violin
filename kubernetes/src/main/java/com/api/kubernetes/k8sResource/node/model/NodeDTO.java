package com.api.kubernetes.k8sResource.node.model;

import io.fabric8.kubernetes.api.model.Node;
import io.fabric8.kubernetes.api.model.NodeCondition;
import lombok.Builder;
import lombok.Getter;

import java.text.DecimalFormat;
import java.util.List;

@Builder
@Getter
public class NodeDTO {
    private String name;
    private String status;
    private String clusterName;
    private String role;
    private double cpu;
    private String memory;
    private String creationTimestamp;

    public static NodeDTO fromNode(Node node, String clusterName) {
        return NodeDTO.builder()
                .name(node.getMetadata().getName())
                .status(getStatus(node.getStatus().getConditions()) ? "Ready" : "NotReady")
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

    private static String parseMemory(String memoryCapacity) {
        DecimalFormat df = new DecimalFormat("#.##"); // 소수점 2자리 형식

        if (memoryCapacity.endsWith("Ki")) {
            double value = Double.parseDouble(memoryCapacity.replace("Ki", "")) / (1024 * 1024);
            return df.format(value) + " Gi";
        } else if (memoryCapacity.endsWith("Mi")) {
            double value = Double.parseDouble(memoryCapacity.replace("Mi", "")) / 1024;
            return df.format(value) + " Gi";
        } else if (memoryCapacity.endsWith("Gi")) {
            double value = Double.parseDouble(memoryCapacity.replace("Gi", ""));
            return df.format(value) + " Gi";
        }
        return memoryCapacity;
    }

    private static Boolean getStatus(List<NodeCondition> conditions) {
        return conditions.stream()
                .anyMatch(condition -> "Ready".equals(condition.getType()) && "True".equals(condition.getStatus()));
    }
}
