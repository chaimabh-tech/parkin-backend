package com.bezkoder.spring.jwt.mongodb.controllers;

import java.io.Console;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import javax.validation.Valid;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jwt.mongodb.models.Parking;
import com.bezkoder.spring.jwt.mongodb.models.Reservation;
import com.bezkoder.spring.jwt.mongodb.models.User;
import com.bezkoder.spring.jwt.mongodb.payload.request.ParkingRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.ReservationRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.jwt.mongodb.payload.response.SuccessResponse;
import com.bezkoder.spring.jwt.mongodb.repository.ParkingRepository;
import com.bezkoder.spring.jwt.mongodb.repository.ReservationRepository;
import com.bezkoder.spring.jwt.mongodb.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/reservation")
public class ReservationController {
	public static final String ACCOUNT_SID = "EXEMPLE_NOT_PUBLISH"; 
    public static final String AUTH_TOKEN = "EXEMPLE_NOT_PUBLISH";
	static {
		   Twilio.init(ACCOUNT_SID,AUTH_TOKEN );
		}
	
	@Autowired
	ParkingRepository parkingRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	private static final Logger l = LogManager.getLogger(ReservationController.class);
	
	
	
	@PostMapping("/addReservation")
	public ResponseEntity<?> addReservation(@Valid @RequestBody ReservationRequest reservationRequest) throws ParseException {
		l.info("In  addReservation()  "); 
		Boolean success=false;
		List<Reservation> reservations = new ArrayList<>();

		
		
		String user_id= reservationRequest.getUserId();
		User user=new User();
		
		
		String parking_id= reservationRequest.getParkingId();
		Parking parking =new Parking();
		

		
		
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
		
		if(parking_id==null)
		{
			l.error("This Parking doesn't exist");
			parking = parkingRepository.findById(parking_id)
					.orElseThrow(() -> new RuntimeException("Error: parking is not found."));
			
		}else
		{
			l.info("Parking found ");
			parking = parkingRepository.findById(parking_id)
					.orElseThrow(() -> new RuntimeException("Error: parking is not found."));
		}
		if(parking!=null)
		{
			parking_id=parking.getId();
			reservations=reservationRepository.findByParkingId(parking_id);
		}
		
		 if(reservations!=null)
		 {
			 
		for(Reservation r : reservations)
		{
			if((reservationRequest.getArrival_date().compareTo(r.getArrival_date()) == 0))
					{
				l.info("There are more than one reservation at this time  ");
				if (parking.getNbr_places()!=0)
				{
					l.info("Parking is not full ");
					// Create new reservation
					Reservation reservation = new Reservation(reservationRequest.getType_voiture(),
							reservationRequest.getArrival_date(),
							reservationRequest.getDeparture_date(),
							reservationRequest.getUserId(),
							reservationRequest.getParkingId());
					
					reservationRepository.save(reservation);
					parking.setNbr_places((parking.getNbr_places())-1);
					parkingRepository.save(parking);
					Message.creator(new PhoneNumber("+21694338185"), new PhoneNumber("+19165426399"),
					         "Thank you for adding your parking to our list !").create();
					success=true;
					return ResponseEntity.ok(new SuccessResponse(success));
				}else
				{
					
					return ResponseEntity.ok(new SuccessResponse(success));
				}
				
				
					}else 
					{
						if (parking.getNbr_places()!=0)
						{
							// Create new reservation
							Reservation reservation = new Reservation(reservationRequest.getType_voiture(),
									reservationRequest.getArrival_date(),
									reservationRequest.getDeparture_date(),
									reservationRequest.getUserId(),
									reservationRequest.getParkingId());
							
							reservationRepository.save(reservation);
							parking.setNbr_places((parking.getNbr_places())-1);
							parkingRepository.save(parking);
							Message.creator(new PhoneNumber("+21694338185"), new PhoneNumber("+19165426399"),
							         "Thank you for adding your parking to our list !").create();
							
							success=true;

							return ResponseEntity.ok(new SuccessResponse(success));
						}else
						{
							return ResponseEntity.ok(new SuccessResponse(success));
						}
					}
		}
		 }
		 
			// Create new reservation
				Reservation reservation = new Reservation(reservationRequest.getType_voiture(),
						reservationRequest.getArrival_date(),
						reservationRequest.getDeparture_date(),
						reservationRequest.getUserId(),
						reservationRequest.getParkingId());
				
				reservationRepository.save(reservation);
				parking.setNbr_places((parking.getNbr_places())-1);
				parkingRepository.save(parking);
				Message.creator(new PhoneNumber("+21694338185"), new PhoneNumber("+19165426399"),
				         "Thank you for adding your parking to our list !").create();
				success=true;

				return ResponseEntity.ok(new SuccessResponse(success));
			 
		 
		
		
		
	}
	
	/*
	@DeleteMapping("/delete")
	public void deleteReser() throws ParseException {
		

		 
       List<Reservation> reservations = new ArrayList<>();
		
		
		reservations = reservationRepository.findAll();
		
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss");

		
		
		String datenow = LocalDateTime.now().toString();
		
		
	   Date parsed= (Date) fmt.parseObject(datenow);
		System.out.println("date now "+parsed);
		
		
		 if(reservations!=null)
		 {
			 
		for(int i = 0; i < reservations.size(); i++)
		{
		
		
		String date=reservations.get(i).getDeparture_date().toString();
		String parsed1 = date.replace("GMT+0100 (heure normale d’Afrique de l’Ouest)","");
        String parsed2=parsed1.replace("2021", "");
        String parsed3=parsed2.concat("WAT 2021");
        System.out.println("date............... "+parsed3);
		String dateBD=parsed3.toString();
		 System.out.println("date string bd ....... "+dateBD);
		 Date date1= (Date) fmt.parseObject(dateBD);
			if(parsed.compareTo(date1)>0) {
				
				this.reservationRepository.deleteById(reservations.get(i).getId());
				 ResponseEntity.ok("This reservation is over due , it will be deleted");
				
			}else if (parsed.equals(date1))
			{
				this.reservationRepository.deleteById(reservations.get(i).getId());
				 ResponseEntity.ok("This reservation is over due , it will be deleted");
			}else if (parsed.compareTo(date1)<0)
			{
				System.out.println("reservation en cours"+parsed.compareTo(date1));
		 ResponseEntity.ok("This reservation is still active");
			}
		
	}
		 }
			 ResponseEntity.ok("There are no reservations");
		 
	}*/
	
	@GetMapping("/getReservations")
	public List<Reservation> getParkings() {
		
		
		List<Reservation> reservations = new ArrayList<>();
		
		
		reservations = reservationRepository.findAll();
				
	
        return reservations;
	}
	
}
