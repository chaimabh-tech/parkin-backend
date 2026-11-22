package com.bezkoder.spring.jwt.mongodb.repository;

import java.util.List;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bezkoder.spring.jwt.mongodb.models.Parking;

public interface ParkingRepository extends MongoRepository<Parking, String> {

	boolean existsByLocation(String localisation);
	
	List findByArea(String area);
	
	List findByUserId(String userId);
	

}
