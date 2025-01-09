package com.api.kubernetes.k8sResource.persistentVolume.model;

import io.fabric8.kubernetes.api.model.PersistentVolume;
import io.fabric8.kubernetes.api.model.Quantity;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@Getter
public class PersistentVolumeDTO {
    private String name;
    private String status;
    private String clusterName;
    private String persistentVolumeClaim;
    private String capacity;
    private String creationTimestamp;

    public static PersistentVolumeDTO fromPersistentVolume(PersistentVolume pv, String clusterName) {
        return PersistentVolumeDTO.builder()
                .name(pv.getMetadata().getName())
                .status(pv.getStatus().getPhase())
                .clusterName(clusterName)
                .persistentVolumeClaim(Optional.ofNullable(pv.getSpec().getClaimRef())
                        .map(claimRef -> claimRef.getName())
                        .orElse("N/A"))
                .capacity(Optional.ofNullable(pv.getSpec().getCapacity())
                        .map(capacity -> capacity.getOrDefault("storage", new Quantity("0Gi")).getAmount())
                        .orElse("0"))
                .creationTimestamp(Optional.ofNullable(pv.getMetadata().getCreationTimestamp())
                        .map(Object::toString)
                        .orElse(null))
                .build();
    }

}
