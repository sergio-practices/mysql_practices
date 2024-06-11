package com.tui.proof.ws.controller;

import java.net.URL;
import java.sql.SQLException;

import org.h2.tools.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.tui.proof.model.AddressDTO;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RestTemplateTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
    URL base;
    @LocalServerPort int port;
	
    @BeforeAll
    public static void initTest() throws SQLException {
        Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
        .start();
    }
    
	@Test
	void greetingShouldReturnDefaultMessage(){
		ResponseEntity<AddressDTO> response = restTemplate.withBasicAuth("spf", "123")
		          .getForEntity("/auth/orders/SP_000001", AddressDTO.class);
		AddressDTO responseBody = response.getBody();
		Assertions.assertEquals(28029, responseBody.getPostcode());
		Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

	}
	
	@Test
	void greetingShouldReturnError(){
		ResponseEntity<AddressDTO> response = restTemplate.withBasicAuth("spf", "123")
		          .getForEntity("/auth/orders/SP_000056", AddressDTO.class);
		AddressDTO responseBody = response.getBody();
		Assertions.assertEquals(0, responseBody.getPostcode());
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}
	
}
