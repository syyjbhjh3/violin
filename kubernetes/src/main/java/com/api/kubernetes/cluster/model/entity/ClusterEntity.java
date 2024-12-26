package com.api.kubernetes.cluster.model.entity;

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

	@Column(name = "CLUSTER_TYPE")
	private String type;

	@Column(name = "CLUSTER_URL")
	private String url;

	@Column(name = "STATUS")
	private String status;

	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

	public ClusterEntity(UUID clusterId, String clusterName, String type, String url, String userId, String status) {
		this.clusterId = clusterId;
		this.clusterName = clusterName;
		this.type = type;
		this.url = url;
		this.userId = userId;
		this.status = status;
	}
}
