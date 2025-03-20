package ru.onuchin.fitnessApp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.onuchin.fitnessApp.models.Dish;
import ru.onuchin.fitnessApp.repositories.DishRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    public static final String APPLE = "Apple";

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    @Test
    void saveDish() {
        final Dish dish = new Dish(APPLE, 100, 1.0, 2.1, 30.1);

        dishService.saveDish(dish);

        verify(dishRepository).save(dish);
    }

    @Test
    void getDishByNames() {
        final Dish dish = new Dish(APPLE, 100, 1.0, 2.1, 30.1);
        when(dishRepository.findByName(APPLE)).thenReturn(dish);

        Dish dishActual = dishService.getDishByNames(APPLE).orElse(null);

        assertNotNull(dishActual);
        assertEquals(dish, dishActual);
    }
}