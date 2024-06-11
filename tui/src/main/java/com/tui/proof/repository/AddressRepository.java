package com.tui.proof.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tui.proof.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	Optional<Address> findById(Integer id);
	
}
