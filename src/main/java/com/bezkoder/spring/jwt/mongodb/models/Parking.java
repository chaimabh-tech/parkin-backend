package com.bezkoder.spring.jwt.mongodb.models;


import java.util.HashSet;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "TableParking")
public class Parking {

	  @Id
	  private String id;

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

	  @NotBlank
	  private String  userId;
	  
	  
	  @NotBlank
	  private String  area;
	  
	  
	  
	public Parking() {
		
	}


	public Parking(String id,
			 String location,  String phone,   String type, int nbr_places,
			 String price, String userId,String  area) {
		super();
		this.id = id;
		this.type = type;
		this.location = location;
		this.phone = phone;
		this.nbr_places = nbr_places;
		this.price = price;
		this.userId=userId;
		this.area=area;
	}


	public Parking( String location, String phone, String type,
			   int nbr_places,  String price ,String userId,String  area) {
		super();
		this.type = type;
		this.location = location;
		this.phone = phone;
		this.nbr_places = nbr_places;
		this.price = price;
		this.userId=userId;
		this.area=area;
	}
	
	


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getLocation() {
		return location;
	}


	public void setLocation(String localisation) {
		this.location = localisation;
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
