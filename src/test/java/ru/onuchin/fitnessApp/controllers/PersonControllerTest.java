package ru.onuchin.fitnessApp.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.onuchin.fitnessApp.dto.PersonDTO;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.models.Target;
import ru.onuchin.fitnessApp.services.PersonService;
import ru.onuchin.fitnessApp.services.TargetService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

    @Mock
    private PersonService personService;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private TargetService targetService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private PersonController personController;

    private static PersonDTO personDTO;
    private static Person person;
    private static Target target;

    @BeforeAll
    static void setUp() {
        personDTO = new PersonDTO("Sam", "123@mail.com", 30, 90, 180, Target.MAINTAINING);

        target = new Target(Target.MAINTAINING);

        person = new Person("Sam", "123@mail.com", 30, 90, 180, target);
    }

    @Test
    void addPerson() {
        when(bindingResult.hasErrors()).thenReturn(false);
        when(targetService.getTargetByName(Target.MAINTAINING)).thenReturn(target);
        when(modelMapper.map(personDTO, Person.class)).thenReturn(person);
        when(personService.savePerson(person)).thenReturn(person);
        when(modelMapper.map(person, PersonDTO.class)).thenReturn(personDTO);

        ResponseEntity<PersonDTO> response = personController.addPerson(personDTO, bindingResult);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(personDTO, response.getBody());
    }
}