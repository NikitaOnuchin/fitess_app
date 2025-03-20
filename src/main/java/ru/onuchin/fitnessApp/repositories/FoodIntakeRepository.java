package ru.onuchin.fitnessApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.onuchin.fitnessApp.models.FoodIntake;
import ru.onuchin.fitnessApp.models.Person;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FoodIntakeRepository extends JpaRepository<FoodIntake, Long> {
    public FoodIntake findByName(String name);
    public List<FoodIntake> findByPersonAndCreateDateBetween(Person person, LocalDateTime from, LocalDateTime to);
}
