package com.bezkoder.spring.jwt.mongodb.payload.request;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ReservationRequest {

	

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


	
	  
	  
	  
	  
}
