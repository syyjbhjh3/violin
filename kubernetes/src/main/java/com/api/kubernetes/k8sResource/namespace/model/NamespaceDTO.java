package com.api.kubernetes.k8sResource.namespace.model;

import io.fabric8.kubernetes.api.model.Namespace;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class NamespaceDTO {
    private String name;
    private String status;
    private String clusterName;
    private String cpuLimits;
    private String cpuRequests;
    private String memoryLimits;
    private String memoryRequests;
    private String storage;
    private String istioInjection;
    private String creationTimestamp;

    public static NamespaceDTO fromNamespace(Namespace namespace, String clusterName) {
        Map<String, String> annotations = namespace.getMetadata().getAnnotations() != null
                ? namespace.getMetadata().getAnnotations()
                : Map.of();

        return NamespaceDTO.builder()
                .name(namespace.getMetadata().getName())
                .status(namespace.getStatus().getPhase())
                .clusterName(clusterName)
                .cpuLimits(annotations.getOrDefault("limits.cpu", "N/A"))
                .cpuRequests(annotations.getOrDefault("requests.cpu", "N/A"))
                .memoryLimits(annotations.getOrDefault("limits.memory", "N/A"))
                .memoryRequests(annotations.getOrDefault("requests.memory", "N/A"))
                .storage(annotations.getOrDefault("storage", "N/A"))
                .istioInjection(annotations.getOrDefault("istio-injection", "disabled"))
                .creationTimestamp(namespace.getMetadata().getCreationTimestamp() != null
                        ? namespace.getMetadata().getCreationTimestamp().toString()
                        : null)
                .build();
    }
}