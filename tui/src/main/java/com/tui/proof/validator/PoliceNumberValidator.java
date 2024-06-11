package com.tui.proof.validator;

import com.tui.proof.constants.Prices;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PoliceNumberValidator implements 
  ConstraintValidator<PoliceNumberConstraint, Integer> {

    @Override
    public boolean isValid(Integer pilotes,
      ConstraintValidatorContext cxt) {
    	for (int index = 0; index < Prices.NUMBER_OF_ORDERS.length; index++)
            if (Prices.NUMBER_OF_ORDERS[index].equals(pilotes))
                return true;
    	return false;
    }

}
