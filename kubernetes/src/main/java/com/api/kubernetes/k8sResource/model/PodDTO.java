package com.api.kubernetes.k8sResource.model;

import io.fabric8.kubernetes.api.model.Pod;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class PodDTO {
    private String name;
    private String namespace;
    private String phase;
    private String podIP;
    private String hostIP;
    private String nodeName;
    private String startTime;
    private int restartCount;
    private List<String> conditions;
    private List<ContainerDTO> containers;
    private List<String> volumes;

    public static PodDTO fromPod(Pod pod) {
        return PodDTO.builder()
                .name(pod.getMetadata().getName())
                .namespace(pod.getMetadata().getNamespace())
                .phase(pod.getStatus().getPhase())
                .podIP(pod.getStatus().getPodIP())
                .nodeName(pod.getSpec().getNodeName())
                .startTime(pod.getStatus().getStartTime() != null ? pod.getStatus().getStartTime().toString() : null)
                .restartCount(pod.getStatus().getContainerStatuses().stream().mapToInt(containerStatus -> containerStatus.getRestartCount()).sum())
                .conditions(pod.getStatus().getConditions().stream().map(condition -> condition.getType()).collect(Collectors.toList()))
                .containers(pod.getSpec().getContainers().stream()
                        .map(container -> ContainerDTO.builder()
                                .name(container.getName())
                                .image(container.getImage())
                                .imagePullPolicy(container.getImagePullPolicy().toString())
                                .ports(container.getPorts().stream().map(port -> port.getContainerPort().toString()).collect(Collectors.toList()))
                                .commands(container.getCommand())
                                .args(container.getArgs())
                                .build())
                        .collect(Collectors.toList()))
                .volumes(pod.getSpec().getVolumes().stream().map(volume -> volume.getName()).collect(Collectors.toList()))
                .build();
    }
}
