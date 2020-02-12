package com.pugliese.annotation.validation.fieldValidation.validations;

import com.pugliese.annotation.validation.fieldValidation.Validation;
import com.pugliese.annotation.validation.fieldValidation.ValidationResult;

public class EqualsToValidation implements Validation {

    private String shouldBe = "";
    private Object currentValue = null;

    public EqualsToValidation(String shouldBe, Object currentValue) {
        this.shouldBe = shouldBe;
        this.currentValue = currentValue;
    }

    @Override
    public ValidationResult isValid() {
        ValidationResult validationResult = new ValidationResult();
        validationResult.isValid = shouldBe.equals(currentValue.toString());
        validationResult.message = "valores nao sao validos";
        return validationResult;
    }

}