package com.tui.proof.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import com.tui.proof.entity.Address;
import com.tui.proof.entity.Purchase;
import com.tui.proof.model.AddressDTO;
import com.tui.proof.model.PurchaseDTO;
import com.tui.proof.repository.AddressRepository;
import com.tui.proof.repository.PurchaseRepository;
import com.tui.proof.service.PrivateService;

@SpringBootTest(classes = {PrivateService.class})
public class PrivateServiceTest {

    @Autowired
    PrivateService orderService;
	
    @MockBean
    AddressRepository addressRepository;
    
    @MockBean
    PurchaseRepository purchaseRepository;
	
    @Test
	void findAddressByPurchaseCodeOk() {
    	String code = "CODE";
    	
    	Address address = new Address(1, null, null, "Av. Asturias", 28029, "Madrid", "Spain");
    	Purchase purchase = new Purchase(code,1,1.33, address);
    	Optional<Purchase> optPurchase = Optional.of(purchase);
    	Mockito.when(purchaseRepository.findByCode(anyString())).thenReturn(optPurchase);
		
    	Optional<Address> optAddress = Optional.of(address);
    	Mockito.when(addressRepository.findById(anyInt())).thenReturn(optAddress);
		
    	AddressDTO addressDTO = orderService.findAddressByPurchaseCode(code);
			
		assertEquals(addressDTO.getCity(), address.getCity());
	}
    
    @Test
	void findAddressByPurchaseCodeWrong() {
    	String code = "ID_000001";
		try {   	
			orderService.findAddressByPurchaseCode(code);
		}catch(RuntimeException ex) {
			assertNotNull(ex);
		}
	}
    
    @Test
	void findAddressByAdressIdWrong() {
    	String code = "ID_000001";
    	Purchase purchase = new Purchase(code,1,1.33, null);
    	Optional<Purchase> optPurchase = Optional.of(purchase);
    	Mockito.when(purchaseRepository.findByCode(anyString())).thenReturn(optPurchase);
    	try { 
    		orderService.findAddressByPurchaseCode(code);
		}catch(RuntimeException ex) {
			assertNotNull(ex);
		}
	}
    
    @Test
	void findPurchasesByName() {
    	Address address1 = new Address(1, null, null, "Av. Asturias", 28029, "Madrid", "Spain");
    	Purchase purchase1 = new Purchase("ID_000001", 1, 1.33, address1);
    	Purchase purchase2 = new Purchase("ID_000002", 1, 1.33, address1);
    	List<Purchase> purchases = new ArrayList<>(List.of(purchase1, purchase2));

    	Mockito.when(purchaseRepository.findAllPurchasesByClientFirstName(anyString())).thenReturn(purchases);
		 	
		List<PurchaseDTO> purchasesDTO = orderService.findPurchasesByName("S");
		assertEquals(2,purchasesDTO.size());
	}

    
}
