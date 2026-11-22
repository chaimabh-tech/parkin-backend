package com.bezkoder.spring.jwt.mongodb.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bezkoder.spring.jwt.mongodb.models.Reservation;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

	List findByParkingId(String parkingId);
	
	
	

}
