package ru.onuchin.fitnessApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.onuchin.fitnessApp.models.FoodIntakeDish;

@Repository
public interface FoodIntakeDishRepository extends JpaRepository<FoodIntakeDish, Integer> {
}
