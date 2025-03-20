package ru.onuchin.fitnessApp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.onuchin.fitnessApp.models.*;
import ru.onuchin.fitnessApp.repositories.FoodIntakeDishRepository;
import ru.onuchin.fitnessApp.repositories.PersonRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodIntakeDishServiceTest {

    private static final LocalDateTime DATE = LocalDateTime.parse("2025-03-19T10:50:01");

    @Mock
    private FoodIntakeDishRepository foodIntakeDishRepository;

    @InjectMocks
    private FoodIntakeDishService foodIntakeDishService;

    @Test
    void saveTest() {
        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target("Мистер мускул"), 2_000);
        FoodIntake foodIntake = new FoodIntake(person, "breakfast", DATE);
        Dish dish = new Dish("Apple", 100, 1.0, 2.1, 30.1);
        FoodIntakeDish foodIntakeDish = new FoodIntakeDish(foodIntake, dish);
        when(foodIntakeDishRepository.save(foodIntakeDish)).thenReturn(foodIntakeDish);

        FoodIntakeDish foodIntakeDishActual  = foodIntakeDishService.save(foodIntakeDish);

        assertNotNull(foodIntakeDishActual);
        assertEquals(foodIntakeDish, foodIntakeDishActual);
    }
}