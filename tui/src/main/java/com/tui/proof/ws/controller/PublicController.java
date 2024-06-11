package com.tui.proof.ws.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tui.proof.model.PurchaseDTO;
import com.tui.proof.service.PublicService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/public")
public class PublicController {

	private final PublicService authService;
	
	PublicController(PublicService authService) {
		this.authService = authService;
	}
	
    @Operation(summary = "Create a pilotes order", description = "Create a pilotes order, choosing between 5, 10 or 15 pilotes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/order")
	public ResponseEntity<PurchaseDTO> createOrder(@Valid @RequestBody PurchaseDTO order) {
		log.info("/auth/order");
		authService.savePurchase(order);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

    @Operation(summary = "Update a pilotes order", description = "During the 5 minutes following the creation of the order it will be"
    		+ " allowed to update the order data; after that time it will not be possible to modify any data of"
    		+ " the order because Miquel will be occupied cooking the pilotes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
	@PutMapping("/order")
	public ResponseEntity<PurchaseDTO> updateOrder(@Valid @RequestBody PurchaseDTO purchaseDTO) {
		log.info("/auth/order");
		authService.updatePurchase(purchaseDTO);
		return new ResponseEntity<>(purchaseDTO, HttpStatus.CREATED);
	}
	
}
