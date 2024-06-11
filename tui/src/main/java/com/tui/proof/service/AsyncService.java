package com.tui.proof.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tui.proof.constants.Prices;
import com.tui.proof.repository.PurchaseRepository;

import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Log4j2
@Service
public class AsyncService {

	private final PurchaseRepository purchaseRepository;
	
	AsyncService(PurchaseRepository purchaseRepository) {
		this.purchaseRepository = purchaseRepository;
	}
	
	/** Parallel stats calculation divided in threads*/
	public void getStats() {
		List<Integer> purchases = Arrays.asList(Prices.NUMBER_OF_ORDERS);
	    
	    Flux.fromIterable(purchases)
	        // parallel only accept integer > 0
	        .parallel(purchases.size()).runOn(Schedulers.parallel())
	        .map(purchaseRepository::calculateStats)
	        .subscribe(optionalServiceResult -> {
	        	for (Object[] purchaseStat : optionalServiceResult) {
	        	log.info("pilote type: "+ purchaseStat[0]
	        			+", total pilotes: " + ((purchaseStat[1]==null)?0:purchaseStat[1])
	        			+", total price pilotes: " +((purchaseStat[2]==null)?0:purchaseStat[2]));
	        	}
	        });
	}
	
}
