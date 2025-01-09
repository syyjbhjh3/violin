package com.api.kubernetes.k8sResource.persistentVolumeClaim.model;

import io.fabric8.kubernetes.api.model.PersistentVolumeClaim;
import io.fabric8.kubernetes.api.model.Quantity;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class PersistentVolumeClaimDTO {
    private String name;
    private String status;
    private String clusterName;
    private String namespace;
    private String persistentVolume;
    private String requestedCapacity;
    private String capacity;
    private String creationTimestamp;

    public static PersistentVolumeClaimDTO fromPersistentVolumeClaim(PersistentVolumeClaim pvc, String clusterName) {
        return PersistentVolumeClaimDTO.builder()
                .name(pvc.getMetadata().getName())
                .status(pvc.getStatus().getPhase())
                .clusterName(clusterName)
                .namespace(pvc.getMetadata().getNamespace())
                .persistentVolume(pvc.getSpec().getVolumeName())
                .requestedCapacity(Optional.ofNullable(pvc.getSpec().getResources().getRequests())
                        .map(requests -> requests.getOrDefault("storage", new Quantity("0Gi")).getAmount())
                        .orElse("0"))
                .capacity(Optional.ofNullable(pvc.getStatus().getCapacity())
                        .map(capacity -> capacity.getOrDefault("storage", new Quantity("0Gi")).getAmount())
                        .orElse("N/A"))
                .creationTimestamp(Optional.ofNullable(pvc.getMetadata().getCreationTimestamp())
                        .map(Object::toString)
                        .orElse(null))
                .build();
    }

}
