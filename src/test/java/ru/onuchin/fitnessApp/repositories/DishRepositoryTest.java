package ru.onuchin.fitnessApp.repositories;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.onuchin.fitnessApp.models.Dish;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class DishRepositoryTest {

    private static final String APPLE = "Apple";

    private final DishRepository dishRepository;

    private static Dish dish;

    @Autowired
    public DishRepositoryTest(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @BeforeAll
    static void setUp()  {
        dish = new Dish(APPLE, 100, 1.0, 2.1, 30.1);
    }

    @Test
    void findByNameTest() {
        dishRepository.save(dish);

        Dish actualDish = dishRepository.findByName(APPLE);

        assertNotNull(actualDish);
        assertEquals(dish, actualDish);
    }

    @Test
    void existsByName() {
        dishRepository.save(dish);
        Dish actualDish = dishRepository.findByName(APPLE);
        assertNotNull(actualDish);
    }
}