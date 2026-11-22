package com.bezkoder.spring.jwt.mongodb.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jwt.mongodb.models.ERole;
import com.bezkoder.spring.jwt.mongodb.models.Role;
import com.bezkoder.spring.jwt.mongodb.repository.RoleRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}

	@GetMapping("/mod")
	@PreAuthorize("hasRole('MODERATOR')")
	public String moderatorAccess() {
		return "Moderator Board.";
	}

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	@Autowired
    private RoleRepository roleRepository;
 
	
	@GetMapping("/getRoles")
	public List<Role> getRoles() {
		List<Role> roles = this.roleRepository.findAll();
		 
        /*String html = "";
        for (Role r : roles) {
            html += r ;
        }*/
 
        return roles;
	}
	
	
	
	@GetMapping("/getRoleByName")
	public Optional<Role> getRoleByName() {
	   
		Optional<Role> role = this.roleRepository.findByName(ERole.ROLE_ADMIN);
		 
        /*String html = "";
        for (Role r : roles) {
            html += r ;
        }*/
 
        return role;
	}
}
