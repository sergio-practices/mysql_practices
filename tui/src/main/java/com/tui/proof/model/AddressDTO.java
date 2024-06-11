package com.tui.proof.model;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Schema(name="Address", description="Address asociated to the address")
@AllArgsConstructor
@Getter
public class AddressDTO implements Serializable{
  private static final long serialVersionUID = -1863335738717509969L;
  
  private String street;
  private int postcode;
  private String city;
  private String country;
}
