package com.bezkoder.spring.jwt.mongodb.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.bezkoder.spring.jwt.mongodb.models.Parking;
import com.bezkoder.spring.jwt.mongodb.models.User;

public class ParkingRequest {

	  @NotBlank
	  @Size(max = 20)
	  private String type;

	  @NotBlank
	  private String location;

	  @NotBlank
	  private String phone;

	  private int nbr_places;

	  @NotBlank
	  private String price;

	  @DBRef
	  private String userId;
	  
	  
	  @NotBlank
	  private String  area;
	  

	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String location) {
		this.location = location;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getNbr_places() {
		return nbr_places;
	}


	public void setNbr_places(int nbr_places) {
		this.nbr_places = nbr_places;
	}


	public String getPrice() {
		return price;
	}


	public void setPrice(String price) {
		this.price = price;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}





	


	
	  
}
