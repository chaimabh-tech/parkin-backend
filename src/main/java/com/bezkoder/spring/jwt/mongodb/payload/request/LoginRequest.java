package com.bezkoder.spring.jwt.mongodb.payload.request;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;

public class LoginRequest {
	@Id
	private String id;
	
	@NotBlank
	private String username;

	@NotBlank
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
