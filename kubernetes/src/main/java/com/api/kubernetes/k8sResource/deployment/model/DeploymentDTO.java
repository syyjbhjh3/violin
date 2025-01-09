package com.api.kubernetes.k8sResource.deployment.model;

import com.api.kubernetes.k8sResource.pod.model.ContainerDTO;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@Getter
public class DeploymentDTO {
    private String name;
    private String namespace;
    private String clusterName;
    private Integer replicas;
    private Integer availableReplicas;
    private Map<String, String> labels;
    private Map<String, String> selectors;
    private List<ContainerDTO> containers;
    private String creationTimestamp;

    public static DeploymentDTO fromDeployment(Deployment deployment, String clusterName) {
        return DeploymentDTO.builder()
                .name(deployment.getMetadata().getName())
                .namespace(deployment.getMetadata().getNamespace())
                .clusterName(clusterName)
                .replicas(Optional.ofNullable(deployment.getSpec().getReplicas()).orElse(0))
                .availableReplicas(Optional.ofNullable(deployment.getStatus().getAvailableReplicas()).orElse(0))
                .labels(Optional.ofNullable(deployment.getMetadata().getLabels()).orElse(Collections.emptyMap()))
                .selectors(Optional.ofNullable(deployment.getSpec().getSelector().getMatchLabels()).orElse(Collections.emptyMap()))
                .containers(Optional.ofNullable(deployment.getSpec().getTemplate().getSpec().getContainers())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(container -> ContainerDTO.builder()
                                .name(container.getName())
                                .image(container.getImage())
                                .build())
                        .collect(Collectors.toList()))
                .creationTimestamp(Optional.ofNullable(deployment.getMetadata().getCreationTimestamp())
                        .map(Object::toString)
                        .orElse(null))
                .build();
    }
}
