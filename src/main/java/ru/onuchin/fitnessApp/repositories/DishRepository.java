package ru.onuchin.fitnessApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.onuchin.fitnessApp.models.Dish;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    public Dish findByName(String name);
    public boolean existsByName(String name);
}
