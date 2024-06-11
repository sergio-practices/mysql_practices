package com.tui.proof.model;

import java.io.Serializable;

import com.tui.proof.validator.PoliceNumberConstraint;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name="Purchase", description="Purchase description")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PurchaseDTO implements Serializable{
  private static final long serialVersionUID = -687991492884056763L;
	
  @Schema(example="Purchase code")
  private String code; //requestId generated
  
  @NotNull(message = "deliveryAddressId is mandatory")
  private int deliveryAddressId;
  
  //@Pattern(regexp = "^(5|10|15)$", message = "pilotes must be 5, 10 or 15")
  @PoliceNumberConstraint(message = "pilotes must be 5, 10 or 15")
  private int pilotes;
  
  private double orderTotal;

}
