package ru.onuchin.fitnessApp.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.models.Target;
import ru.onuchin.fitnessApp.repositories.PersonRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    private static final String NAME = "Sam";

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonService personService;

    @Test
    void savePersonTest() {
        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target("Мистер мускул"), 2_000);
        when(personRepository.save(person)).thenReturn(person);

        Person personActual = personService.savePerson(person);

        assertNotNull(personActual);
        assertEquals(person, personActual);
    }

    @Test
    void getPersonByNameTest() {
        Person person = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target("Мистер мускул"), 2_000);
        when(personRepository.findByName(NAME)).thenReturn(person);

        Person personActual = personService.getPersonByName(NAME).orElse(null);

        assertNotNull(personActual);
        assertEquals(person, personActual);
    }

    @Test
    void getCalorieCalculationTest() {
        // 1 сценарий
        Person personMaintaining = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target(Target.WEIGHT_LOSS));

        PersonService.getCalorieCalculation(personMaintaining);

        assertEquals(1776, personMaintaining.getCalorieNorm());

        // 2 сценарий
        Person personWeightLoss = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target(Target.MAINTAINING));

        PersonService.getCalorieCalculation(personWeightLoss);

        assertEquals(2090, personWeightLoss.getCalorieNorm());

        // 3 сценарий
        Person personMassGain = new Person("Sam", "123@mail.com", 30,
                90, 180, new Target(Target.MASS_GAIN));

        PersonService.getCalorieCalculation(personMassGain);

        assertEquals(2403, personMassGain.getCalorieNorm());
    }
}