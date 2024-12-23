package com.api.kubernetes.cluster.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_CLUSTER")
public class ClusterEntity {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "GENDER")
	private String gender;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "SALT")
	private String salt;

	public ClusterEntity(String type, String id, String name, String password, String gender, String phone, String email, String address, String salt) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.salt = salt;
	}
}
