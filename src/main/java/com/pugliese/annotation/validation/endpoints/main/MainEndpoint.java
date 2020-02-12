package com.pugliese.annotation.validation.endpoints.main;

import java.lang.reflect.Field;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pugliese.annotation.validation.fieldValidation.EqualsToValidation;
import com.pugliese.annotation.validation.fieldValidation.annotations.EqualsTo;
import com.pugliese.annotation.validation.models.MyModel;

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
            executeValidation(myModel);

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

    public void executeValidation(Object object) throws Exception {
        Class<?> objectClass = object.getClass();
        for (Field field : objectClass.getDeclaredFields()) {
            field.setAccessible(true);

            verifyAndExecuteValidationEqualsTo(field, object);
        }
    }

    public void verifyAndExecuteValidationEqualsTo(Field field, Object object)
     throws Exception {

        if (field.isAnnotationPresent(EqualsTo.class)) {
            String valueShouldBe = field.getAnnotation(EqualsTo.class).value();
            Object currentValue = field.get(object);

            EqualsToValidation validation = new EqualsToValidation(valueShouldBe, currentValue);

            boolean isValid = validation.isValid();
            System.out.println(field.get(object));

            if (!isValid) {
                String message = "The " + field.getName() + " is invalid";
                throw new Exception(message);
            }

            System.out.println(valueShouldBe);
            System.out.println("The value isValid:" + isValid);
        }
    }
}
