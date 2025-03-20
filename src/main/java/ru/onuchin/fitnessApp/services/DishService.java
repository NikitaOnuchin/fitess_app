package ru.onuchin.fitnessApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.onuchin.fitnessApp.models.Dish;
import ru.onuchin.fitnessApp.repositories.DishRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class DishService {
    private DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }
    @Transactional(readOnly = false)
    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public Optional<Dish> getDishByNames(String name) {
        return Optional.ofNullable(dishRepository.findByName(name));
    }
}
