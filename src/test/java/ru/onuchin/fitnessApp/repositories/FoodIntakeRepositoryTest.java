package ru.onuchin.fitnessApp.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.onuchin.fitnessApp.models.FoodIntake;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.models.Target;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FoodIntakeRepositoryTest {

    private static final LocalDateTime DATE = LocalDateTime.parse("2025-03-19T10:50:01");

    private final FoodIntakeRepository foodIntakeRepository;
    private final PersonRepository personRepository;
    private final TargetRepository targetRepository;

    @Autowired
    public FoodIntakeRepositoryTest(FoodIntakeRepository foodIntakeRepository, PersonRepository personRepository, TargetRepository targetRepository) {
        this.foodIntakeRepository = foodIntakeRepository;
        this.personRepository = personRepository;
        this.targetRepository = targetRepository;
    }

    @Test
    void findByNameTest() {
        Target target = new Target("Мистер мускул");
        targetRepository.save(target);
        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, target, 2_000);
        personRepository.save(person);
        FoodIntake foodIntake = new FoodIntake(person, "breakfast", DATE);
        foodIntakeRepository.save(foodIntake);

        FoodIntake foodIntakeActual = foodIntakeRepository.findByName("breakfast");

        assertNotNull(foodIntakeActual);
        assertEquals(foodIntake, foodIntakeActual);
    }

    @Test
    void findByPersonAndCreateDateBetweenTest() {
        Target target = new Target("Мистер мускул");
        targetRepository.save(target);
        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, target, 2_000);
        personRepository.save(person);
        FoodIntake foodIntake = new FoodIntake(person, "breakfast", DATE);
        foodIntakeRepository.save(foodIntake);

        List<FoodIntake> foodIntakeActual = foodIntakeRepository.findByPersonAndCreateDateBetween(person,
                DATE.toLocalDate().atStartOfDay(), DATE.toLocalDate().atTime(LocalTime.MAX));

        assertNotNull(foodIntakeActual);
        assertEquals(foodIntake, foodIntakeActual.get(0));
    }
}