package com.tui.proof.ws.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.tui.proof.configuration.SecurityConfig;
import com.tui.proof.service.PrivateService;

@Import(SecurityConfig.class)
@WebMvcTest(PrivateController.class)
class PrivateControllerTest {
	
	@MockBean
	private PrivateService orderService;
	
    @Autowired
	private MockMvc mockMvc;
    
    @WithMockUser(roles={"ADMIN"})
	@Test
	void findPurchasesByName() throws Exception {
		mockMvc.perform(get("/auth/orders?firstName=S")).andExpect(status().isOk())
        	.andDo(print());
	}
	
    @WithMockUser(roles={"ADMIN"})
	@Test
	void getAddressByPurchaseCode() throws Exception {
		mockMvc.perform(get("/auth/orders/SP_000001")).andExpect(status().isOk())
          .andDo(print());
	}
	
}
