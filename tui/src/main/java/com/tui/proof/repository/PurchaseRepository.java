package com.tui.proof.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tui.proof.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, String> {

	Optional<Purchase> findByCode(String code);

	@Query(value = "SELECT P.* FROM PURCHASES P"
			+ " JOIN ADDRESSES A"
			+ " ON A.ID = P.ADDRESS_ID"
			+ " JOIN CLIENTS C"
			+ " ON C.ID = A.CLIENT_ID"
			+ " AND C.FIRST_NAME like %:name%", 
			nativeQuery = true)
	List<Purchase> findAllPurchasesByClientFirstName(@Param("name") String name);
	
	@Query(value = "SELECT :piloteType, SUM(P.PILOTES), SUM(P.ORDER_TOTAL) FROM PURCHASES P WHERE P.PILOTES=:piloteType",
			nativeQuery = true)
	List<Object[]> calculateStats(@Param("piloteType") int piloteType);
	
	@Query(value = "SELECT ORDER_SEQUENCE.NEXTVAL FROM DUAL", 
			nativeQuery = true)
	int getOrderSequence();
}
