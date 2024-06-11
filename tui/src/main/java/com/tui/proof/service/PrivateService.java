package com.tui.proof.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tui.proof.entity.Address;
import com.tui.proof.entity.Purchase;
import com.tui.proof.model.AddressDTO;
import com.tui.proof.model.PurchaseDTO;
import com.tui.proof.repository.AddressRepository;
import com.tui.proof.repository.PurchaseRepository;

@Service
public class PrivateService {

	private final PurchaseRepository purchaseRepository;
	
	private final AddressRepository addressRepository;

	PrivateService(PurchaseRepository purchaseRepository,
			AddressRepository addressRepository) {
		this.purchaseRepository = purchaseRepository;
		this.addressRepository = addressRepository;
	}
	
	public AddressDTO findAddressByPurchaseCode(String code) {
		Optional<Purchase> order = purchaseRepository.findByCode(code);
		if (order.isEmpty()) 
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "code not exists");

		if (order.get().getAddress() == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "address not exists");
		
		Address address = order.get().getAddress();
		return new AddressDTO(address.getStreet(), 
				address.getPostcode(), 
				address.getCity(), 
				address.getCountry());
	}
	
	public List<PurchaseDTO> findPurchasesByName(String name) {
		return purchaseToPurchaseDTO(purchaseRepository.findAllPurchasesByClientFirstName(name));
	}
	
	private List<PurchaseDTO> purchaseToPurchaseDTO(List<Purchase> purchases) {
		List<PurchaseDTO> purchasesDTO = new ArrayList<>();
		for (Purchase purchase : purchases) {
			PurchaseDTO purchaseDTO = new PurchaseDTO(purchase.getCode(),
					purchase.getAddress().getId(),
					purchase.getPilotes(),
					purchase.getOrderTotal()
					);
			purchasesDTO.add(purchaseDTO);
		}

		return purchasesDTO;
	}
	
}
