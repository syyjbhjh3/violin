package com.api.login.user.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	public UserEntity(String type, String id, String name, String password, String gender, String phone, String email, String address, String salt) {
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
