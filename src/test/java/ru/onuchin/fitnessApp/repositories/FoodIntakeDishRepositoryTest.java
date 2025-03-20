package ru.onuchin.fitnessApp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.onuchin.fitnessApp.models.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FoodIntakeDishRepositoryTest {

    private static final LocalDateTime DATE = LocalDateTime.parse("2025-03-19T10:50:01");

    private final FoodIntakeDishRepository foodIntakeDishRepository;
    private final FoodIntakeRepository foodIntakeRepository;
    private final PersonRepository personRepository;
    private final TargetRepository targetRepository;
    private final DishRepository dishRepository;


    @Autowired
    public FoodIntakeDishRepositoryTest(FoodIntakeDishRepository foodIntakeDishRepository, FoodIntakeRepository foodIntakeRepository, PersonRepository personRepository, TargetRepository targetRepository, DishRepository dishRepository) {
        this.foodIntakeDishRepository = foodIntakeDishRepository;
        this.foodIntakeRepository = foodIntakeRepository;
        this.personRepository = personRepository;
        this.targetRepository = targetRepository;
        this.dishRepository = dishRepository;
    }

    @Test
    public void saveTest(){
        Target target = new Target("Мистер мускул");
        targetRepository.save(target);
        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, target, 2_000);
        personRepository.save(person);
        FoodIntake foodIntake = new FoodIntake(person, "breakfast", DATE);
        foodIntakeRepository.save(foodIntake);
        Dish dish = new Dish("Apple", 100, 1.0, 2.1, 30.1);
        dishRepository.save(dish);
        FoodIntakeDish foodIntakeDish = new FoodIntakeDish(foodIntake, dish);

        FoodIntakeDish foodIntakeDishActual = foodIntakeDishRepository.save(foodIntakeDish);

        assertNotNull(foodIntakeDishActual);
        assertEquals(foodIntakeDish, foodIntakeDishActual);
    }
}