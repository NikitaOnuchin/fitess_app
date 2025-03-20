package ru.onuchin.fitnessApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.onuchin.fitnessApp.models.Target;

@Repository
public interface TargetRepository extends JpaRepository<Target, Integer> {

    public Target findByName(String name);
}
