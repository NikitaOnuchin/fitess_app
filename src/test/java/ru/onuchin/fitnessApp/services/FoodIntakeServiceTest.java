package ru.onuchin.fitnessApp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.onuchin.fitnessApp.models.FoodIntake;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.models.Target;
import ru.onuchin.fitnessApp.repositories.FoodIntakeRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodIntakeServiceTest {

    private static final LocalDateTime DATE = LocalDateTime.parse("2025-03-19T10:50:01");


    @Mock
    private FoodIntakeRepository foodIntakeRepository;

    @InjectMocks
    private FoodIntakeService foodIntakeService;

    @Test
    void saveFoodIntakeTest() {
        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target("Мистер мускул"), 2_000);
        FoodIntake foodIntake = new FoodIntake(person, "breakfast", DATE);
        when(foodIntakeRepository.save(foodIntake)).thenReturn(foodIntake);

        FoodIntake foodIntakeActual = foodIntakeService.saveFoodIntake(foodIntake);

        assertNotNull(foodIntakeActual);
        assertEquals(foodIntake, foodIntakeActual);
    }

    @Test
    void enrichFoodIntakeTest() {
        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target("Мистер мускул"), 2_000);
        FoodIntake foodIntake = new FoodIntake(person, "breakfast", DATE);

        FoodIntakeService.enrichFoodIntake(foodIntake);

        assertNotNull(foodIntake.getCreateDate());
    }
}