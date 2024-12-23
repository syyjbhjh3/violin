package com.api.kubernetes.cluster.model.dto;

import com.api.kubernetes.cluster.model.entity.ClusterEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ClusterDTO {
    private final String type;
    private final String id;
    private final String password;
    private final String name;
    private final String gender;
    private final String phone;
    private final String email;
    private final String address;
    private final String salt;

    public ClusterEntity toEntity() {
        return new ClusterEntity(this.type, this.id, this.name, this.password, this.gender, this.phone, this.email, this.address, this.salt);
    }
}