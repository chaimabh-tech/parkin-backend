package com.bezkoder.spring.jwt.mongodb.controllers;

import java.io.IOException;



import java.util.Date;
import java.util.HashSet;

import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jwt.mongodb.models.ERole;
import com.bezkoder.spring.jwt.mongodb.models.Role;
import com.bezkoder.spring.jwt.mongodb.models.User;
import com.bezkoder.spring.jwt.mongodb.payload.request.LoginRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.SignupRequest;
import com.bezkoder.spring.jwt.mongodb.payload.request.UpdateRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.JwtResponse;
import com.bezkoder.spring.jwt.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.jwt.mongodb.repository.RoleRepository;
import com.bezkoder.spring.jwt.mongodb.repository.UserRepository;
import com.bezkoder.spring.jwt.mongodb.security.jwt.JwtUtils;
import com.bezkoder.spring.jwt.mongodb.security.services.UserDetailsImpl;



@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	
	private static final Logger l = LogManager.getLogger(AuthController.class);

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		l.info("In  SignIn : "); 
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
        Boolean success=true;
        l.info("Out of  SignIn. ");
		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles,
												 userDetails.getPhone(),success));
	}

	@PostMapping("/signupUser")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException, IOException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			l.error("This UserName already exists");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			l.error("This Email already exists");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getPhone());

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			l.error("This Role deosn't exist");
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			l.info("User Role exists ");
			strRoles.forEach(role -> {
				switch (role) {
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		l.info("User saved successfully ");
		//EMAIL SENDING
		l.debug("The start of Email sending operation");
		Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("NOT_PUBLISH", "NOT_PUBLISH");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("chaimaa.benhsouna@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
		   msg.setSubject("Welcome to Park'In");
		   msg.setContent("We send this e-mail to welcome you to the Park'In family , "
		   		+ "Your registration on our website has been succussfully done", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("We send this e-mail to welcome you to the Park'In family , "
			   		+ "Your registration on our website has been succussfully done", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   //MimeBodyPart attachPart = new MimeBodyPart();

		   //attachPart.attachFile("C://Users/Chams Ben Alaya/Desktop/PFE-PARKING/PFE-Angular/ParkingAngular/src/assets/images");
		   //multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);   
		   l.debug("Email sending done !");
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	
	
	@PostMapping("/signupCompany")
	public ResponseEntity<?> registerCompany(@Valid @RequestBody SignupRequest signUpRequest) throws AddressException, MessagingException, IOException {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			l.error("This UserName already exists");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			l.error("This Email already exists");
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), 
							 signUpRequest.getEmail(),
							 encoder.encode(signUpRequest.getPassword()),
							 signUpRequest.getPhone());

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			l.error("This Role deosn't exist");
			Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			l.info("User Role exists ");
			strRoles.forEach(role -> {
				switch (role) {
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);
		l.info("Company saved successfully ");
		//EMAIL SENDING
		l.debug("The start of Email sending operation");		Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("NOT_PUBLISH", "NOT_PUBLISH");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("chaimaa.benhsouna@gmail.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(user.getEmail()));
		   msg.setSubject("Welcome to Park'In");
		   msg.setContent("We send this e-mail to welcome you to the Park'In family , "
		   		+ "Your registration on our website has been succussfully done", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("We send this e-mail to welcome you to the Park'In family , "
			   		+ "Your registration on our website has been succussfully done", "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   //MimeBodyPart attachPart = new MimeBodyPart();

		   //attachPart.attachFile("C://Users/Chams Ben Alaya/Desktop/PFE-PARKING/PFE-Angular/ParkingAngular/src/assets/images");
		   //multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);   
		   l.debug("Email sending done !");
		return ResponseEntity.ok(new MessageResponse("Company registered successfully!"));
	}
	
	
	@GetMapping("/profil")
	public ResponseEntity<?> getUser(@Valid @RequestHeader  String accessToken) {
		Boolean test=false;
		if(accessToken!=null) {
			 test=jwtUtils.validateJwtToken(accessToken) ;
		}else
		{
			l.error("No Token !");
			return ResponseEntity.ok(new MessageResponse("No token was assigned!"));
		}
		if(test)
		{
			String username = jwtUtils.getUserNameFromJwtToken(accessToken);
			Optional<User> user=this.userRepository.findByUsername(username);
			l.info("User Found :" + user);
	           return ResponseEntity.ok(user);
		}
		return ResponseEntity.ok(new MessageResponse("No user was found !")); 
		 
           
	}
	
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public ResponseEntity<?> logoutPage (HttpServletRequest request, HttpServletResponse response) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null){    
	        new SecurityContextLogoutHandler().logout(request, response, auth);
	    }
	    return ResponseEntity.ok(new MessageResponse("you are successfully logged out"));
	    		
	}
	
	
	
	
	@PutMapping("updateAdmin/{userId}")
    public User updateAdmin(@RequestBody UpdateRequest updateRequest, @PathVariable String userId) {
		Set<String> strRoles = updateRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		
		User user = new User(updateRequest.getUsername(), 
				updateRequest.getEmail(),
				 encoder.encode(updateRequest.getPassword()),
				 updateRequest.getPhone()
				);
		user.setRoles(roles);
		user.setId(userId);
		userRepository.save(user);
        return user;
    }
	
	
	
	@PutMapping("updateUser/{userId}")
    public User updateUser(@RequestBody UpdateRequest updateRequest, @PathVariable String userId) {
		Set<String> strRoles = updateRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}
		
		
		User user = new User(updateRequest.getUsername(), 
				updateRequest.getEmail(),
				 encoder.encode(updateRequest.getPassword()),
				 updateRequest.getPhone()
				);
		user.setRoles(roles);
		user.setId(userId);
		userRepository.save(user);
        return user;
    }
}
