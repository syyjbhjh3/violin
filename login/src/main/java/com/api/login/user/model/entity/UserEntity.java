package com.api.login.user.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TB_USER")
public class UserEntity {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "NAME")
	private String name;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "SALT")
	private String salt;

	@CreationTimestamp
	@Column(name = "CREATED_AT")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "UPDATED_AT")
	private LocalDateTime updatedAt;

	public UserEntity(String type, String id, String name, String password, String email, String address, String salt) {
		this.type = type;
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.address = address;
		this.salt = salt;
	}
}
