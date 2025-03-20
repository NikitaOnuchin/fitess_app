package ru.onuchin.fitnessApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;

import static ru.onuchin.fitnessApp.models.Target.MAINTAINING;
import static ru.onuchin.fitnessApp.models.Target.WEIGHT_LOSS;

@Service
@Transactional(readOnly = true)
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = false)
    public Person savePerson(Person person) {
        getCalorieCalculation(person);
        return personRepository.save(person);
    }

    public Optional<Person> getPersonByName(String name) {
        return Optional.ofNullable(personRepository.findByName(name));
    }

    static void getCalorieCalculation(Person person) {
        double BMR = 88.362 +
                (9.247 * person.getWeight()) +
                (3.098 * person.getHeight()) - (4.330 * person.getAge());
        double TDEE = BMR * 1.55;
        switch (person.getTarget().getName()) {
            case (WEIGHT_LOSS):
                person.setCalorieNorm((int) Math.round(TDEE * 0.85));
                break;
            case (MAINTAINING):
                person.setCalorieNorm((int) Math.round(TDEE));
                break;
            default:
                person.setCalorieNorm((int) Math.round(TDEE * 1.15));
        }
    }
}
