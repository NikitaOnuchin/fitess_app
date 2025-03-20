package ru.onuchin.fitnessApp.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.onuchin.fitnessApp.repositories.PersonRepository;

@Component
public class EmailValidator {

    @Autowired
    private PersonRepository personRepository;

    public EmailValidator(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public boolean isValid(String email) {
        if (email == null) {
            return true;
        }
        return personRepository.existsByEmail(email);
    }
}
