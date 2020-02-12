package com.pugliese.annotation.validation.fieldValidation;

public class EqualsToValidation implements Validation {

    private String shouldBe = "";
    private Object currentValue = null;

    public EqualsToValidation(String shouldBe, Object currentValue) {
        this.shouldBe = shouldBe;
        this.currentValue = currentValue;
    }

    @Override
    public boolean isValid() {
        if (currentValue != null) {
            return shouldBe.equals(currentValue.toString());
        }
        return false;
    }

}