package ru.onuchin.fitnessApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.onuchin.fitnessApp.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findByName(String name);
    boolean existsByEmail(String email);
}
