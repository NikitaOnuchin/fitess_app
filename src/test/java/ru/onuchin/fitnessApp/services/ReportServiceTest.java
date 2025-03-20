package ru.onuchin.fitnessApp.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.onuchin.fitnessApp.dto.CaloriesReportDTO;
import ru.onuchin.fitnessApp.dto.CaloriesReportHistoryRequestDTO;
import ru.onuchin.fitnessApp.models.*;
import ru.onuchin.fitnessApp.repositories.FoodIntakeRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceTest {

    private final static String NAME = "Sam";
    private static final LocalDate DATE = LocalDate.parse("2025-03-19");


    private static List<FoodIntake> foodIntakes;

    @Mock
    private FoodIntakeRepository foodIntakeRepository;

    @Mock
    private PersonService personService;

    @InjectMocks
    private ReportService reportService;

    @BeforeAll
    public static void getFoodIntakes() {
        List<FoodIntakeDish> foodIntakeDishList = new ArrayList<>();
        Dish dish1 = new Dish("item_1", 500, 2.3, 24.5, 23);
        Dish dish2 = new Dish("item_2", 500, 2.3, 24.5, 23);
        Dish dish3 = new Dish("item_3", 500, 2.3, 24.5, 23);
        foodIntakeDishList.add(new FoodIntakeDish(dish1));
        foodIntakeDishList.add(new FoodIntakeDish(dish2));
        foodIntakeDishList.add(new FoodIntakeDish(dish3));
        FoodIntake foodIntake = new FoodIntake();
        foodIntake.setFoodIntakeDishList(foodIntakeDishList);
        foodIntake.setCreateDate(DATE.atStartOfDay());
        foodIntakes = Collections.singletonList(foodIntake);
    }

    @Test
    void getCaloriesReportTest() {
        LocalDateTime startOfDay = DATE.atStartOfDay();
        LocalDateTime endOfDay = DATE.atTime(LocalTime.MAX);

        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target("Мистер мускул"), 2_000);
        when(personService.getPersonByName(NAME)).thenReturn(Optional.of(person));
        when(foodIntakeRepository.findByPersonAndCreateDateBetween(person, startOfDay, endOfDay)).thenReturn(foodIntakes);

        CaloriesReportDTO caloriesReportDTOActual = reportService.getCaloriesReport(NAME, DATE);

        assertEquals(1500, caloriesReportDTOActual.getSumCalories());
        assertTrue(caloriesReportDTOActual.isCaloriesNorm());
    }

    @Test
    void isCaloriesNormTest() {
        int sumCalories = 2000;
        int normCalories = 1500;

        boolean actual = ReportService.IsCaloriesNorm(sumCalories, normCalories);

        assertFalse(actual);

        sumCalories = 1500;
        normCalories = 2000;

        actual = ReportService.IsCaloriesNorm(sumCalories, normCalories);

        assertTrue(actual);

        sumCalories = 2000;

        actual = ReportService.IsCaloriesNorm(sumCalories, normCalories);

        assertTrue(actual);
    }

    @Test
    void getSumCaloriesTest() {
        int sumCalories = ReportService.getSumCalories(foodIntakes);
        assertEquals(1500, sumCalories);
    }

}