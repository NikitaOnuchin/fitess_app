package ru.onuchin.fitnessApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.onuchin.fitnessApp.models.FoodIntake;
import ru.onuchin.fitnessApp.repositories.FoodIntakeRepository;

import java.time.LocalDateTime;

@Service
@Transactional(readOnly = true)
public class FoodIntakeService {

    private final FoodIntakeRepository foodIntakeRepository;

    @Autowired
    public FoodIntakeService(FoodIntakeRepository foodIntakeRepository) {
        this.foodIntakeRepository = foodIntakeRepository;
    }

    @Transactional(readOnly = false)
    public FoodIntake saveFoodIntake(FoodIntake foodIntake) {
        enrichFoodIntake(foodIntake);
        return foodIntakeRepository.save(foodIntake);
    }

    static void enrichFoodIntake(FoodIntake foodIntake) {
        foodIntake.setCreateDate(LocalDateTime.now());
    }
}
