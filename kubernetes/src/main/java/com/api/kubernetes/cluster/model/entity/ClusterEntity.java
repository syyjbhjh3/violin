package com.api.kubernetes.cluster.model.entity;

import com.api.kubernetes.common.model.enums.Status;
import com.api.kubernetes.common.model.enums.Type;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_CLUSTER")
public class ClusterEntity {

	@Id
	@Column(name = "CLUSTER_ID")
	@GeneratedValue(generator = "UUID")
	private UUID clusterId;

	@Column(name = "CLUSTER_NAME")
	private String clusterName;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "KUBECONFIG_DATA")
	private String kubeConfigData;

	@Enumerated(EnumType.STRING)
	@Column(name = "CLUSTER_TYPE")
	private Type type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private Status status;

	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

	public ClusterEntity(UUID clusterId, String clusterName, Type type, String kubeConfigData, String userId, Status status) {
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.type = type;
		this.kubeConfigData = kubeConfigData;
		this.userId = userId;
		this.status = status;
	}
}
