package com.api.kubernetes.k8sResource.namespace.model;

import io.fabric8.kubernetes.api.model.Namespace;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NamespaceDTO {
    private String name;
    private String status;
    private String clusterName;
    private String creator;
    private String cpuLimits;
    private String cpuRequests;
    private String memoryLimits;
    private String memoryRequests;
    private String storage;
    private String istioInjection;
    private String creationTimestamp;

    public static NamespaceDTO fromNamespace(Namespace namespace, String clusterName) {
        return NamespaceDTO.builder()
                .name(namespace.getMetadata().getName())
                .status(namespace.getStatus().getPhase())
                .clusterName(clusterName)
                .creator(namespace.getMetadata().getAnnotations() != null
                        ? namespace.getMetadata().getAnnotations().getOrDefault("creator", "unknown")
                        : "unknown")
                .cpuLimits(namespace.getMetadata().getAnnotations() != null
                        ? namespace.getMetadata().getAnnotations().getOrDefault("limits.cpu", "N/A")
                        : "N/A")
                .cpuRequests(namespace.getMetadata().getAnnotations() != null
                        ? namespace.getMetadata().getAnnotations().getOrDefault("requests.cpu", "N/A")
                        : "N/A")
                .memoryLimits(namespace.getMetadata().getAnnotations() != null
                        ? namespace.getMetadata().getAnnotations().getOrDefault("limits.memory", "N/A")
                        : "N/A")
                .memoryRequests(namespace.getMetadata().getAnnotations() != null
                        ? namespace.getMetadata().getAnnotations().getOrDefault("requests.memory", "N/A")
                        : "N/A")
                .storage(namespace.getMetadata().getAnnotations() != null
                        ? namespace.getMetadata().getAnnotations().getOrDefault("storage", "N/A")
                        : "N/A")
                .istioInjection(namespace.getMetadata().getAnnotations() != null
                        ? namespace.getMetadata().getAnnotations().getOrDefault("istio-injection", "disabled")
                        : "disabled")
                .creationTimestamp(namespace.getMetadata().getCreationTimestamp() != null
                        ? namespace.getMetadata().getCreationTimestamp().toString()
                        : null)
                .build();
    }
}
