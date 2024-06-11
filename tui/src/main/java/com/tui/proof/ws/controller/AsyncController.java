package com.tui.proof.ws.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tui.proof.service.AsyncService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequestMapping("/async")
public class AsyncController {

	private final AsyncService fluxService;
	
	AsyncController(AsyncService fluxService) {
		this.fluxService = fluxService;
	}
	
    @Operation(summary = "Gets stats", description = "Get stats about purchases in async mode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"), 
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/stats")
    public Mono<ResponseEntity<?>> getStats() {
    	log.info("/async/stats");
    	fluxService.getStats();
		return Mono.just(new ResponseEntity<>(HttpStatus.CREATED));
    }
	
}
