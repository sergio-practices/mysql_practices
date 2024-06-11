package com.tui.proof.ws.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.tui.proof.service.AsyncService;

@WebMvcTest(AsyncController.class)
class AsyncControllerTest {
	
	@MockBean
	private AsyncService asyncService;
    
    @Autowired
    private WebTestClient webClient;
    
	@WithMockUser(roles={"ADMIN"})
	@Test
	void findPurchasesByName(){
    	webClient.get().uri("/async/stats")
		.exchange()
		.expectStatus().isCreated();
	}
	
}
