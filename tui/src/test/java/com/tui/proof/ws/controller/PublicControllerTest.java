package com.tui.proof.ws.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;

import org.springframework.test.web.servlet.MockMvc;

import com.tui.proof.configuration.SecurityConfig;
import com.tui.proof.service.PublicService;

@Import(SecurityConfig.class)
@WebMvcTest(PublicController.class)
class PublicControllerTest {
	
	@MockBean
	private PublicService authService;
	
    @Autowired
	private MockMvc mockMvc;
	
    @WithAnonymousUser
	@Test
	void createOrderOk() throws Exception {
	      String user = "{\"deliveryAddressId\" : 1, \"pilotes\":5}";
			mockMvc.perform(post("/public/order")
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
				    .content(user))
				    .andExpect(status().isCreated())
				    .andReturn();
	}
	
    @WithAnonymousUser
	@Test
	void createOrderWrong() throws Exception {
		mockMvc.perform(post("/public/order")).andExpect(status().isBadRequest())
        	.andDo(print());
	}
	
    @WithAnonymousUser
	@Test
	void updateOrderOk() throws Exception {
	      String data = "{\"deliveryAddressId\" : 1, \"pilotes\":5}";
	      mockMvc.perform(put("/public/order")
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON)
	    		  .content(data))
	      		  .andExpect(status().isCreated())
	      		  .andReturn();
	}
    
    @WithAnonymousUser
	@Test
	void updateOrderValidationError() throws Exception {
	      String data = "{\"deliveryAddressId\" : 1, \"pilotes\":1}";
	      mockMvc.perform(put("/public/order")
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON)
	    		  .content(data))
	      		  .andExpect(status().isBadRequest())
	      		  .andReturn();
	}
	
    @WithAnonymousUser
	@Test
	void updateOrderWrong() throws Exception {
		mockMvc.perform(put("/public/order")).andExpect(status().isBadRequest())
        	.andDo(print());
	}
	
}
