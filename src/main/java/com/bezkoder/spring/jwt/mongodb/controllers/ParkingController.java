package com.bezkoder.spring.jwt.mongodb.controllers;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jwt.mongodb.models.ERole;
import com.bezkoder.spring.jwt.mongodb.models.Parking;
import com.bezkoder.spring.jwt.mongodb.models.Role;
import com.bezkoder.spring.jwt.mongodb.models.User;
import com.bezkoder.spring.jwt.mongodb.payload.request.ParkingRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.jwt.mongodb.repository.ParkingRepository;
import com.bezkoder.spring.jwt.mongodb.repository.UserRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/parking")

public class ParkingController {
	public static final String ACCOUNT_SID = "EXEMPLE_NOT_PUBLISH"; 
    public static final String AUTH_TOKEN = "EXEMPLE_NOT_PUBLISH"; 

		
		
	
	@Autowired
	ParkingRepository parkingRepository;
	
	@Autowired
	UserRepository userRepository;
	
	private static final Logger l = LogManager.getLogger(ParkingController.class);
	
	
	@PostMapping("/addParking")
	public ResponseEntity<?> addParking(@Valid @RequestBody ParkingRequest parkingRequest) {
		l.info("In  AddParking()  "); 
		if (parkingRepository.existsByLocation(parkingRequest.getLocation())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: parking Location already exists!"));
		}
		
		
		String user_id= parkingRequest.getUserId();
		User user=new User();
		
		if(user_id==null)
		{
			l.error("This User doesn't exist");
			user = userRepository.findById(user_id)
					.orElseThrow(() -> new RuntimeException("Error: user is not found."));
			
		}else
		{
			l.info("User found ");
			user = userRepository.findById(user_id)
					.orElseThrow(() -> new RuntimeException("Error: user is not found."));
		}
		if(user!=null)
		{
			user_id=user.getId();
		}
		// Create new parking
		l.info("Satrted filling the parking data");
		Parking park = new Parking(parkingRequest.getLocation(),
				parkingRequest.getPhone(),
				parkingRequest.getType(),
				parkingRequest.getNbr_places(),
				parkingRequest.getPrice(),
				parkingRequest.getUserId(),
				parkingRequest.getArea());
		
		parkingRepository.save(park);
		l.info("Parking successfully added !");
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message.creator(new PhoneNumber("+21694338185"), new PhoneNumber("+19165426399 "),
		         "Thank you for adding your parking to our list !").create();
		l.info("Text successfully sent !");
		return ResponseEntity.ok(new MessageResponse("parking added successfully"));
	}
	
	@GetMapping("/getParkings")
	public List<Parking> getParkings(@Param("userId") String userId) {
		
		
		List<Parking> parkings = new ArrayList<>();
		
		
		parkings = parkingRepository.findByUserId(userId);
				
	
        return parkings;
	}
	
	@GetMapping("/getAllParkings")
	public List<Parking> getAllParkings() {
		
		
		List<Parking> parkings = new ArrayList<>();
		
		
		parkings = parkingRepository.findAll();
				
	
        return parkings;
	}
	
	
	
	@GetMapping("/getParkingsByArea")
	public List<Parking> getParkingsByArea(@Param("area") String area) {
		
		
		List<Parking> parkings = new ArrayList<>();
		
		
		parkings = parkingRepository.findByArea(area);
				
	
        return parkings;
	}
	
	
	@GetMapping("/getParkingsByDisponibility")
	public List<Parking> getParkingsByDisponibility(@Param("arrival") DateTime arrival,@Param("departure") DateTime departure) {
		
		List<Parking> parkings = new ArrayList<>();
		
		
	
        return parkings;
	}
	
	
	
	

}
