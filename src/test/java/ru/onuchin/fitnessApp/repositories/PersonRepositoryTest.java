package ru.onuchin.fitnessApp.repositories;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.onuchin.fitnessApp.models.Dish;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.models.Target;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PersonRepositoryTest {

    private final PersonRepository personRepository;
    private final TargetRepository targetRepository;

    @Autowired
    public PersonRepositoryTest(PersonRepository personRepository, TargetRepository targetRepository) {
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

        Person actualPerson = personRepository.findByName("Sam");

        assertNotNull(actualPerson);
        assertEquals(person, actualPerson);
    }
}