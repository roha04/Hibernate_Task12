package org.example.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.entity.Client;
import org.example.entity.Planet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlanetValidator {
    private Validator validator;
    public PlanetValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    public List<String> validatePlanet(Planet planet) {
        List<String> listMessage = new ArrayList<>();
        Set<ConstraintViolation<Planet>> violationList = validator.validate(planet);
        for (ConstraintViolation<Planet> vl : violationList) {
            listMessage.add(vl.getMessage());
        }
        return listMessage;
    }
}
