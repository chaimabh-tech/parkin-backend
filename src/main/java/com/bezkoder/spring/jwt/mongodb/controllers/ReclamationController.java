package com.bezkoder.spring.jwt.mongodb.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jwt.mongodb.models.Parking;
import com.bezkoder.spring.jwt.mongodb.models.Reclamation;
import com.bezkoder.spring.jwt.mongodb.models.Reservation;
import com.bezkoder.spring.jwt.mongodb.models.User;
import com.bezkoder.spring.jwt.mongodb.payload.request.ReclamationRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.ReservationRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.SuccessResponse;
import com.bezkoder.spring.jwt.mongodb.repository.ParkingRepository;
import com.bezkoder.spring.jwt.mongodb.repository.ReclamationRepository;
import com.bezkoder.spring.jwt.mongodb.repository.ReservationRepository;
import com.bezkoder.spring.jwt.mongodb.repository.UserRepository;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reclamation")
public class ReclamationController {
	
	@Autowired
	ReclamationRepository reclamationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ParkingRepository parkingRepository;
	
	private static final Logger l = LogManager.getLogger(ReclamationController.class);
	
	
	@PostMapping("/addReclamation")
	public ResponseEntity<?> addReclamation(@Valid @RequestBody ReclamationRequest reclamationRequest) {
		
		l.info("In  addReclamation()  "); 
		
		Boolean success=false;
		List<Reclamation> reclamations = new ArrayList<>();

		
		
		String user_email= reclamationRequest.getEmail_user();
		User user=new User();
		
		
		String parking_id= reclamationRequest.getParkingId();
		Parking parking =new Parking();
		

		
		if(user_email==null)
		{
			l.error("This User Email doesn't exist");
			user = userRepository.findByEmail(user_email);
			
		}else
		{
			l.info("User Email found ");
			user = userRepository.findByEmail(user_email);
		}
		if(user!=null)
		{
			user_email=user.getEmail();
		}
		
		if(parking_id==null)
		{
			parking = parkingRepository.findById(parking_id)
					.orElseThrow(() -> new RuntimeException("Error: parking is not found."));
			
		}else
		{
			parking = parkingRepository.findById(parking_id)
					.orElseThrow(() -> new RuntimeException("Error: parking is not found."));
		}
		if(parking!=null)
		{
			parking_id=parking.getId();
			reclamations=reclamationRepository.findByParkingId(parking_id);
		}
		
		
					// Create new reservation
		          l.info("Satrted filling the reclamation data");
					Reclamation reclamation = new Reclamation(reclamationRequest.getLogin_user(),
							reclamationRequest.getEmail_user(),
							reclamationRequest.getDescription(),
							reclamationRequest.getParkingId(),
							reclamationRequest.getSubject()
							);
					
					reclamationRepository.save(reclamation);
					l.info("Reclamation successfully added !");
					success=true;
				return ResponseEntity.ok(new SuccessResponse(success));
			 
		 
		
		
		
	}
	
	
	@GetMapping("/getReclam/{userId}")
	public List<Reclamation> getReclam(@PathVariable String userId) {
		
		
		List<Parking> parkings = new ArrayList<>();
		
		List<Reclamation> reclamations = new ArrayList<>();
		
		List<Reclamation> reclamationsUserCo = new ArrayList<>();
		
		
		parkings = parkingRepository.findByUserId(userId);
		
		reclamations=reclamationRepository.findAll();
		
		
		for(Parking p : parkings)
		{
			for(Reclamation r : reclamations)
			{
				if((p.getId()).compareTo(r.getParkingId())==0)
				{
					reclamationsUserCo.add(r);
				}
			}
		}
				
	
        return reclamationsUserCo;
	}
	

}
