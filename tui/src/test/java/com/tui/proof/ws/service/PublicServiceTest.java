package com.tui.proof.ws.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tui.proof.entity.Address;
import com.tui.proof.entity.Client;
import com.tui.proof.entity.Purchase;
import com.tui.proof.model.PurchaseDTO;
import com.tui.proof.repository.AddressRepository;
import com.tui.proof.repository.PurchaseRepository;
import com.tui.proof.service.PublicService;

@SpringBootTest(classes = {PublicService.class})
public class PublicServiceTest {

    @Autowired
    PublicService authService;
	
    @MockBean
    AddressRepository addressRepository;
    
    @MockBean
    PurchaseRepository purchaseRepository;
	
    @Test
	void savePurchase() {
    	Client client = new Client(1,"Sergio","Perez","666999");
    	Address address = new Address(1, client, null, "Av. Asturias", 28029, "Madrid", "Spain");
    	Optional<Address> optAddress = Optional.of(address);
    	Mockito.when(addressRepository.findById(anyInt())).thenReturn(optAddress);
    	Mockito.when(purchaseRepository.getOrderSequence()).thenReturn(1);
    	PurchaseDTO purchaseDTO = new PurchaseDTO("ID_000001",1,5, 0);
    	PurchaseDTO purchaseDTOMod = authService.savePurchase(purchaseDTO);
    	assertEquals(6.65, purchaseDTOMod.getOrderTotal());
	}

    @Test
	void updatePurchase() {
    	PurchaseDTO initPurchaseDTO = new PurchaseDTO();
    	try {
    		authService.updatePurchase(initPurchaseDTO);
    	}catch(RuntimeException ex) {
    		assertNotNull(ex);
    	}
    	//Exception no code
    	
    	initPurchaseDTO.setCode("SP_000001");
    	initPurchaseDTO.setPilotes(5);
    	initPurchaseDTO.setDeliveryAddressId(1);
    	Purchase purchase = new Purchase();
    	purchase.setCode("SP_000001");
    	purchase.setPilotes(10);
    	purchase.setCreatedOn(Instant.now());
    	purchase.setAddress(new Address(1, null, null, "Av. Asturias", 28029, "Madrid", "Spain"));
    	Optional<Purchase> optPurchase = Optional.of(purchase);
    	Mockito.when(purchaseRepository.findByCode(initPurchaseDTO.getCode())).thenReturn(optPurchase);
    	PurchaseDTO resultPurchaseDTO = authService.updatePurchase(initPurchaseDTO);
    	assertEquals(1, resultPurchaseDTO.getDeliveryAddressId());
	}
	
}
