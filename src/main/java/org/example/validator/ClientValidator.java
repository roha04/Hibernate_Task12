package org.example.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.entity.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClientValidator {
    private Validator validator;
    public ClientValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
    public List<String> validateClient(Client client) {
        List<String> listMessage = new ArrayList<>();
        Set<ConstraintViolation<Client>> violationList = validator.validate(client);
        for (ConstraintViolation<Client> vl : violationList) {
            listMessage.add(vl.getMessage());
        }
        return listMessage;
    }
}
