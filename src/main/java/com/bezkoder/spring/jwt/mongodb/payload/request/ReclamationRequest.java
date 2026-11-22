package com.bezkoder.spring.jwt.mongodb.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

public class ReclamationRequest {

	  @Id
	  private String id;

	  @NotBlank
	  @Size(max = 20)
	  private String login_user;


	  
	  @NotBlank
	  @Size(max = 50)
	  
	  private String email_user;
	  
	  	  
	  @NotBlank
	  @Size(max = 50)
	  
	  private String subject;
	  
	  @NotBlank	  
	  private String description;
	  
	  @NotBlank
	  @Size(max = 50)
	  
	  private String parkingId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogin_user() {
		return login_user;
	}

	public void setLogin_user(String login_user) {
		this.login_user = login_user;
	}

	public String getEmail_user() {
		return email_user;
	}

	public void setEmail_user(String email_user) {
		this.email_user = email_user;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParkingId() {
		return parkingId;
	}

	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
}
