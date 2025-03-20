package ru.onuchin.fitnessApp.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import ru.onuchin.fitnessApp.dto.DishDTO;
import ru.onuchin.fitnessApp.models.Dish;
import ru.onuchin.fitnessApp.repositories.DishRepository;
import ru.onuchin.fitnessApp.services.DishService;
import ru.onuchin.fitnessApp.util.DishNotCreateException;

import java.beans.PropertyEditor;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishControllerTest {


    @Mock
    private DishService dishService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private DishController dishController;

    static DishDTO dishDTO;
    static Dish dish;

    @BeforeAll
    static void setUp() {
        dishDTO = new DishDTO();
        dishDTO.setName("Dish 1");
        dishDTO.setCalories(100);
        dishDTO.setProteins(5.6);
        dishDTO.setFats(5.6);
        dishDTO.setCarbohydrates(5.6);

        dish = new Dish();
        dish.setName("Dish 1");
        dish.setCalories(100);
        dish.setProteins(5.6);
        dish.setFats(5.6);
        dish.setCarbohydrates(5.6);
    }

    @Test
    public void addDishTest() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(modelMapper.map(dishDTO, Dish.class)).thenReturn(dish);
        when(dishService.saveDish(dish)).thenReturn(dish);
        when(modelMapper.map(dish, DishDTO.class)).thenReturn(dishDTO);

        ResponseEntity<DishDTO> response = dishController.addDish(dishDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(dishDTO, response.getBody());

    }
}