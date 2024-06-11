package com.tui.proof.ws.controller;

import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tui.proof.model.AddressDTO;
import com.tui.proof.model.PurchaseDTO;
import com.tui.proof.service.PrivateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.*;

@Log4j2
@RestController
@RequestMapping("/auth")
public class PrivateController {
	
	private final PrivateService orderService;

	PrivateController(PrivateService orderService) {
		this.orderService = orderService;
	}
	
    @Operation(summary = "Gets purchases", description = "Gets purchases given part of a client first name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/orders")
	public ResponseEntity<List<PurchaseDTO>> findPurchasesByName(@RequestParam("firstName") String firstName) {
		log.info("/orders?firstName="+firstName);
		return new ResponseEntity<>(orderService.findPurchasesByName(firstName), HttpStatus.OK);
	}
	
    @Operation(summary = "Gets direction", description = "Gets direction for a purchase code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/orders/{code}")
	public ResponseEntity<AddressDTO> getAddressByPurchaseCode(@PathVariable("code") String code) {
		log.info("/orders/orders/"+code);
		return new ResponseEntity<>(orderService.findAddressByPurchaseCode(code), HttpStatus.OK);
		
	}
  
}
