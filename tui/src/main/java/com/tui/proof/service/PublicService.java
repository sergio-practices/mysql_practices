package com.tui.proof.service;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tui.proof.constants.Prices;
import com.tui.proof.entity.Address;
import com.tui.proof.entity.Client;
import com.tui.proof.entity.Purchase;
import com.tui.proof.model.PurchaseDTO;
import com.tui.proof.repository.AddressRepository;
import com.tui.proof.repository.PurchaseRepository;

@Service
public class PublicService {

	private final PurchaseRepository purchaseRepository;
	
	private final AddressRepository addressRepository;

	PublicService(PurchaseRepository purchaseRepository,
			AddressRepository addressRepository) {
		this.purchaseRepository = purchaseRepository;
		this.addressRepository = addressRepository;
	}

	/**
	 * Check if the address is correct, then calculates its CODE (the initials of the name 
	 * and a sequence) and saves it.
	 * @param purchaseDTO
	 * @return
	 */
	public PurchaseDTO savePurchase(PurchaseDTO purchaseDTO) {
	    // update Order
		Optional<Address> address = addressRepository.findById(purchaseDTO.getDeliveryAddressId());
		if (address.isEmpty())
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "wrong address");

		String purchaseCode = calculateCode(address.get().getClient());
		double deliveryTotal = purchaseDTO.getPilotes() * Prices.PILOTE_SINGLE_PRICE;
		Purchase purchase = new Purchase(purchaseCode, 
				purchaseDTO.getPilotes(), 
				deliveryTotal, 
				address.get());
		purchaseRepository.save(purchase);
		purchaseDTO.setCode(purchaseCode);
		purchaseDTO.setOrderTotal(deliveryTotal);
	    
		return purchaseDTO;

	}
	
	public PurchaseDTO updatePurchase(PurchaseDTO purchaseDTO) {
		Purchase purchase = validateAndCreateObject(purchaseDTO);
		purchaseRepository.save(purchase);
		return purchaseDTO;

	}

	/**
	 * Validates if the code is not null and exists, if the modificatin is before 5 minutes
	 * and if the address exists. If there is a modification in the pilotes number, recalculates
	 * the total price.
	 * @param purchaseDTO Object to modify
	 * @return New Object to save in DB
	 */
	private Purchase validateAndCreateObject(PurchaseDTO purchaseDTO) {
		if (purchaseDTO.getCode() == null || purchaseDTO.getCode().isBlank()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "code ismandatory");
		}
		Optional<Purchase> currentPurchase = purchaseRepository.findByCode(purchaseDTO.getCode());

		if (currentPurchase.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "code not exists");
		}
		
		Instant now = Instant.now();
		Duration res = Duration.between(currentPurchase.get().getCreatedOn(), now);
		if (res.getSeconds()>300)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "purchase created more than 5 minutes ago");

		if (currentPurchase.get().getAddress() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "address not exists");
		
		//If there is a new number of pilotes we change the price
		if (currentPurchase.get().getPilotes()!= purchaseDTO.getPilotes()){
			purchaseDTO.setOrderTotal(purchaseDTO.getPilotes() * Prices.PILOTE_SINGLE_PRICE);
		}

		return new Purchase(purchaseDTO.getCode(), 
				purchaseDTO.getPilotes(), 
				purchaseDTO.getOrderTotal(),
				currentPurchase.get().getAddress(),
				currentPurchase.get().getCreatedOn());
	}
	
	private String calculateCode(Client client) {
		String val = String.valueOf(purchaseRepository.getOrderSequence());
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < 6 - val.length()) {
	        sb.append('0');
	    }
	    
		return String.valueOf(client.getFirstName().charAt(0))
				+ String.valueOf(client.getLastName().charAt(0))
				+ "_"
				+ sb.toString()+val;
	}
	
}
