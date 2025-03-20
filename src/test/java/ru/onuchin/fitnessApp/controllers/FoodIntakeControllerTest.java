package ru.onuchin.fitnessApp.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindingResult;
import ru.onuchin.fitnessApp.dto.FoodIntakeDTO;
import ru.onuchin.fitnessApp.models.Dish;
import ru.onuchin.fitnessApp.models.FoodIntake;
import ru.onuchin.fitnessApp.models.FoodIntakeDish;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.services.DishService;
import ru.onuchin.fitnessApp.services.FoodIntakeService;
import ru.onuchin.fitnessApp.services.PersonService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static ru.onuchin.fitnessApp.controllers.DishControllerTest.dish;

@ExtendWith(MockitoExtension.class)
class FoodIntakeControllerTest {

    @Mock
    private FoodIntakeService foodIntakeService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private DishService dishService;

    @Mock
    private PersonService personService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private FoodIntakeController foodIntakeController;

    private static FoodIntakeDTO foodIntakeDTO;
    private static FoodIntake foodIntake;
    private static Person person;
    private static Dish apple;
    private static Dish banana;

    @BeforeAll
    public static void setUp() {
        foodIntakeDTO = new FoodIntakeDTO();
        foodIntakeDTO.setName("Breakfast");
        foodIntakeDTO.setPersonName("John Doe");
        foodIntakeDTO.setDishNames(List.of("Apple", "Banana"));

        person = new Person();
        person.setName("John Doe");


        apple = new Dish("Apple", 100, 1.0, 2.1, 30.1);
        banana = new Dish("Banana", 100, 1.0, 2.1, 30.1);

        foodIntake = new FoodIntake();
        foodIntake.setName("Breakfast");
        foodIntake.setPerson(person);
        foodIntake.getFoodIntakeDishList().add(new FoodIntakeDish(foodIntake, apple));
        foodIntake.getFoodIntakeDishList().add(new FoodIntakeDish(foodIntake, banana));
    }

    @Test
    void addFoodIntakeTest() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(personService.getPersonByName("John Doe")).thenReturn(Optional.of(person));
        when(dishService.getDishByNames("Apple")).thenReturn(Optional.of(apple));
        when(dishService.getDishByNames("Banana")).thenReturn(Optional.of(banana));
        when(foodIntakeService.saveFoodIntake(any(FoodIntake.class))).thenReturn(foodIntake);
        when(modelMapper.map(foodIntake, FoodIntakeDTO.class)).thenReturn(foodIntakeDTO);

        ResponseEntity<FoodIntakeDTO> response = foodIntakeController.addFoodIntake(foodIntakeDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(foodIntakeDTO, response.getBody());
    }
}