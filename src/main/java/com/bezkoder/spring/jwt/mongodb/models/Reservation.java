package com.bezkoder.spring.jwt.mongodb.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Document(collection = "reservation")
public class Reservation {
	
	  @Id
	  private String id;

	  @NotBlank
	  @Size(max = 20)
	  private String type_voiture;

	  @NotBlank
	  @Size(max = 120)
	  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sss")
	  //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	  private String arrival_date;

	  @NotBlank
	  @Size(max = 120)
	  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.sss")
	  //@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	  private String departure_date;
	  
	  @NotBlank
	  @Size(max = 50)
	  
	  private String userId;
	  
	  	  
	  @NotBlank
	  @Size(max = 50)
	  
	  private String parkingId;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getType_voiture() {
		return type_voiture;
	}


	public void setType_voiture(String type_voiture) {
		this.type_voiture = type_voiture;
	}


	public String getArrival_date() {
		return arrival_date;
	}


	public void setArrival_date(String arrival_date) {
		this.arrival_date = arrival_date;
	}


	public String getDeparture_date() {
		return departure_date;
	}


	public void setDeparture_date(String departure_date) {
		this.departure_date = departure_date;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getParkingId() {
		return parkingId;
	}


	public void setParkingId(String parkingId) {
		this.parkingId = parkingId;
	}


	public Reservation(String id, @NotBlank @Size(max = 20) String type_voiture,
			@NotBlank @Size(max = 120) String arrival_date, @NotBlank @Size(max = 120) String departure_date,
			@NotBlank @Size(max = 50) String userId, @NotBlank @Size(max = 50) String parkingId) {
		super();
		this.id = id;
		this.type_voiture = type_voiture;
		this.arrival_date = arrival_date;
		this.departure_date = departure_date;
		this.userId = userId;
		this.parkingId = parkingId;
	}


	public Reservation(@NotBlank @Size(max = 20) String type_voiture, @NotBlank @Size(max = 120) String arrival_date,
			@NotBlank @Size(max = 120) String departure_date, @NotBlank @Size(max = 50) String userId,
			@NotBlank @Size(max = 50) String parkingId) {
		super();
		this.type_voiture = type_voiture;
		this.arrival_date = arrival_date;
		this.departure_date = departure_date;
		this.userId = userId;
		this.parkingId = parkingId;
	}


	public Reservation() {
		
	}


	
	
	  

}
