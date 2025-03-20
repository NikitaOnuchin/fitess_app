package ru.onuchin.fitnessApp.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.onuchin.fitnessApp.repositories.DishRepository;
import ru.onuchin.fitnessApp.repositories.PersonRepository;

@Component
public class DishNameValidator {


    private DishRepository dishRepository;

    @Autowired
    public DishNameValidator(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public boolean isValid(String name) {
        if (name == null) {
            return true;
        }
        return dishRepository.existsByName(name);
    }

}
