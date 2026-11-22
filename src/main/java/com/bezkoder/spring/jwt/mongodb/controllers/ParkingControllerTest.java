package com.bezkoder.spring.jwt.mongodb.controllers;

import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.MediaType;

import com.bezkoder.spring.jwt.mongodb.models.Parking;
import com.bezkoder.spring.jwt.mongodb.models.User;
import com.bezkoder.spring.jwt.mongodb.payload.request.LoginRequest;
import com.bezkoder.spring.jwt.mongodb.payload.response.JwtResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ParkingControllerTest {
	
	  protected MockMvc mvc;
	   @Autowired
	   WebApplicationContext webApplicationContext;

	   @Before
	   public  void setUp() {
	      mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	   }
	   protected String mapToJson(Object obj) throws JsonProcessingException {
	      ObjectMapper objectMapper = new ObjectMapper();
	      return objectMapper.writeValueAsString(obj);
	   }
	   protected <T> T mapFromJson(String json, Class<T> clazz)
	      throws JsonParseException, JsonMappingException, IOException {
	      
	      ObjectMapper objectMapper = new ObjectMapper();
	      return objectMapper.readValue(json, clazz);
	   }
	   
	   @Test
	   public void getAllParkings() throws Exception {
	      String uri = "http://localhost:8080/api/parking/getAllParkings";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      System.out.println("HEEEEEEEEEEEEEEEEEEEEEEEEEEEERE!!!!!!!!!!!!!!!!!!!!!");
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      
	   }
	   
	   
	   @Test
	   public void createParking() throws Exception {
	      String uri = "http://localhost:8080/api/parking/addParking";
	      Parking parking = new Parking();
	      parking.setNbr_places(100);
	      parking.setLocation("marsa");
	      parking.setArea("sidi abd el aziz");
	      parking.setPhone("94338185");
	      parking.setPrice("1000");
	      parking.setType("private-parking");
	      parking.setUserId("5fe33a97ac2a2b0fd75f003a");
	      String inputJson = this.mapToJson(parking);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      assertEquals(content, "{\"message\":\"parking added successfully\"}");
	   }

}
