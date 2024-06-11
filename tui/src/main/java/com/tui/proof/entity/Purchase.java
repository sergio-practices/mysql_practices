package com.tui.proof.entity;

import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "purchases")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Purchase {

	@Id
	private String code; //requestId generated
	
	@Column(name = "pilotes", nullable = false)
	private int pilotes;
	
	@Column(name = "orderTotal", nullable = false)
	private double orderTotal;

    @ManyToOne
    @JoinColumn(name = "addressId", nullable=false)
    private Address address;
    
    @CreationTimestamp
    private Instant createdOn;
	
	public Purchase(String code, int pilotes, double orderTotal, Address address) {
		this.code = code;
		this.pilotes = pilotes;
		this.orderTotal = orderTotal;
		this.address = address;
	}
}
