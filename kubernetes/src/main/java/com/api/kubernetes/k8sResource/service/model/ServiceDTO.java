package com.api.kubernetes.k8sResource.service.model;

import io.fabric8.kubernetes.api.model.Service;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@Getter
public class ServiceDTO {
    private String name;
    private String namespace;
    private String clusterName;
    private String type;
    private Map<String, String> labels;
    private Map<String, String> podSelectors;
    private String clusterIP;
    private List<String> ports;
    private String creationTimestamp;

    public static ServiceDTO fromService(Service service, String clusterName) {
        return ServiceDTO.builder()
                .name(service.getMetadata().getName())
                .namespace(service.getMetadata().getNamespace())
                .clusterName(clusterName)
                .type(service.getSpec().getType())
                .labels(service.getMetadata().getLabels() != null
                        ? new HashMap<>(service.getMetadata().getLabels())
                        : Collections.emptyMap())
                .podSelectors(service.getSpec().getSelector() != null
                        ? new HashMap<>(service.getSpec().getSelector())
                        : Collections.emptyMap())
                .clusterIP(service.getSpec().getClusterIP())
                .ports(service.getSpec().getPorts() != null
                        ? service.getSpec().getPorts().stream()
                        .map(port -> port.getPort().toString())
                        .collect(Collectors.toList())
                        : Collections.emptyList())
                .creationTimestamp(service.getMetadata().getCreationTimestamp() != null
                        ? service.getMetadata().getCreationTimestamp().toString()
                        : null)
                .build();
    }

}
