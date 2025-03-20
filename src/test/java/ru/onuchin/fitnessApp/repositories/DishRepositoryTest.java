package ru.onuchin.fitnessApp.repositories;

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

    private final DishRepository dishRepository;

    @Autowired
    public DishRepositoryTest(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @Test
    void findByNameTest() {
        Dish dish = new Dish("Apple", 100, 1.0, 2.1, 30.1);
        dishRepository.save(dish);

        Dish actualDish = dishRepository.findByName("Apple");

        assertNotNull(actualDish);
        assertEquals(dish, actualDish);
    }
}