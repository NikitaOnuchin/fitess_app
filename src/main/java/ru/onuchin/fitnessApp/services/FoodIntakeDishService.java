package ru.onuchin.fitnessApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.onuchin.fitnessApp.models.FoodIntakeDish;
import ru.onuchin.fitnessApp.repositories.FoodIntakeDishRepository;

@Service
@Transactional
public class FoodIntakeDishService {

    private final FoodIntakeDishRepository foodIntakeDishRepository;

    @Autowired
    public FoodIntakeDishService(FoodIntakeDishRepository foodIntakeDishRepository) {
        this.foodIntakeDishRepository = foodIntakeDishRepository;
    }

    @Transactional(readOnly = true)
    public FoodIntakeDish save(FoodIntakeDish foodIntakeDish) {
        return foodIntakeDishRepository.save(foodIntakeDish);
    }
}
