package com.api.kubernetes.cluster.model.entity;

import com.api.kubernetes.common.model.enums.Status;
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
@Table(name = "TB_KUBECONFIG")
public class KubeConfigEntity {

	@Id
	@Column(name = "KUBECONFIG_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long kubeConfigId;

	@Column(name = "CLUSTER_ID")
	private UUID clusterId;

	@Column(name = "KUBECONFIG_NAME")
	private String kubeConfigName;

	@Column(name = "KUBECONFIG_DATA")
	private String kubeConfigData;

	@Column(name = "KUBECONFIG_TYPE")
	private String kubeConfigType;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private Status status;

	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

	public KubeConfigEntity(UUID clusterId, String kubeConfigName, String kubeConfigData, String kubeConfigType, Status status) {
		this.clusterId = clusterId;
		this.kubeConfigName = kubeConfigName;
		this.kubeConfigData = kubeConfigData;
		this.kubeConfigType = kubeConfigType;
		this.status = status;
	}
}
