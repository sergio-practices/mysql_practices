package com.tui.proof.ws.service;

import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tui.proof.repository.PurchaseRepository;
import com.tui.proof.service.AsyncService;

@SpringBootTest(classes = {AsyncService.class})
public class AsyncServiceTest {

	@Autowired
	private AsyncService asyncService;
	
    @MockBean
    PurchaseRepository purchaseRepository;
	
    @Test
	void savePurchase() {
    	List<Object[]> optPurchase5 = new ArrayList<>();
    	Object[] stats5 = new Object[] {5,10,13.2};
    	List<Object[]> optPurchase10 = new ArrayList<>();
    	Object[] stats10 = new Object[] {10,10,15.5};
    	List<Object[]> optPurchase15 = new ArrayList<>();
    	Object[] stats15 = new Object[] {15,10,1.3};
    	optPurchase5.add(stats5);
    	optPurchase10.add(stats10);
    	optPurchase15.add(stats15);
    	Mockito.when(purchaseRepository.calculateStats(eq(5))).thenReturn(optPurchase5);
    	Mockito.when(purchaseRepository.calculateStats(eq(10))).thenReturn(optPurchase10);
    	Mockito.when(purchaseRepository.calculateStats(eq(15))).thenReturn(optPurchase15);
    	asyncService.getStats();
    	Mockito.verify(purchaseRepository).calculateStats(5);
    	Mockito.verify(purchaseRepository).calculateStats(10);
    	Mockito.verify(purchaseRepository).calculateStats(15);
	}
	
}
