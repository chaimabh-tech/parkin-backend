package com.bezkoder.spring.jwt.mongodb.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "reclamation")
public class Reclamation {


	  @Id
	  private String id;

	  @NotBlank
	  @Size(max = 20)
	  private String login_user;


	  
	  @NotBlank
	  @Size(max = 50)
	  
	  private String email_user;
	  
	  
	  @NotBlank	  
	  private String description;
	  
	  @NotBlank
	  @Size(max = 50)
	  
	  private String parkingId;
	  
	  @NotBlank
	  @Size(max = 50)
	  
	  private String subject;
	  
	  

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

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




	public Reclamation(@NotBlank @Size(max = 20) String login_user, @NotBlank @Size(max = 50) String email_user,
			@NotBlank String description, @NotBlank @Size(max = 50) String parkingId,
			@NotBlank @Size(max = 50) String subject) {
		super();
		this.login_user = login_user;
		this.email_user = email_user;
		this.description = description;
		this.parkingId = parkingId;
		this.subject = subject;
	}

	public Reclamation() {
		super();
	}

	@Override
	public String toString() {
		return "Reclamation [id=" + id + ", login_user=" + login_user + ", email_user=" + email_user + ", description=" + description + ", parkingId=" + parkingId + "]";
	}
	  


}
