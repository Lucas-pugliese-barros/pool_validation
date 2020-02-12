package com.pugliese.annotation.validation.endpoints.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pugliese.annotation.validation.fieldValidation.ValidationResult;
import com.pugliese.annotation.validation.fieldValidation.annotations.EqualsTo;
import com.pugliese.annotation.validation.fieldValidation.validations.EqualsToValidation;
import com.pugliese.annotation.validation.models.MyModel;

import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainEndpoint {

    @PostMapping("/")
    public String input(@RequestBody JsonNode jsonNode) {
        ObjectMapper mapper = objectMapperNewInstance();

        try {
            MyModel myModel = mapper.convertValue(jsonNode, MyModel.class);

            List<ValidationResult> validationResults = executeValidation(myModel);
            verifyValidationResults(validationResults);            

            return myModel.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public ObjectMapper objectMapperNewInstance() {
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    public List<ValidationResult> executeValidation(Object object) throws Exception {
        List<ValidationResult> validationResults = new ArrayList<>();

        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);

            validationResults.add(verifyAndExecuteValidationEqualsTo(field, object));
        }

        return validationResults;
    }

    public @Nullable ValidationResult verifyAndExecuteValidationEqualsTo(Field field, Object object)
     throws Exception {

        if (field.isAnnotationPresent(EqualsTo.class)) {
            String valueShouldBe = field.getAnnotation(EqualsTo.class).value();
            Object currentValue = field.get(object);

            EqualsToValidation validation = new EqualsToValidation(valueShouldBe, currentValue);

            ValidationResult validationResult = validation.isValid();
            System.out.println(field.get(object));

            System.out.println(valueShouldBe);
            System.out.println("The value isValid:" + validationResult.isValid);

            return validationResult;
        }
        return null;
    }

    public void verifyValidationResults(List<ValidationResult> validationResults) {
        validationResults = validationResults.stream()
            .filter(Objects::nonNull)
            .filter(validationResult -> !validationResult.isValid)
            .collect(Collectors.toList());

        if(!validationResults.isEmpty()) {
            System.out.println("------------Erros----------");
            validationResults.stream()
                .forEach(validationResult -> System.out.println(validationResult.message));
        }
    }
}
