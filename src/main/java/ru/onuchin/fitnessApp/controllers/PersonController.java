package ru.onuchin.fitnessApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.onuchin.fitnessApp.dto.PersonDTO;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.models.Target;
import ru.onuchin.fitnessApp.services.PersonService;
import ru.onuchin.fitnessApp.services.TargetService;
import ru.onuchin.fitnessApp.util.PersonErrorResponse;
import ru.onuchin.fitnessApp.util.PersonNotCreateException;
import ru.onuchin.fitnessApp.util.TargetNotFoundException;
import ru.onuchin.fitnessApp.validation.EmailValidator;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final ModelMapper modelMapper;
    private final TargetService targetService;
    private final EmailValidator emailValidator;

    @Autowired
    public PersonController(PersonService personService, ModelMapper modelMapper, TargetService targetService, EmailValidator emailValidator) {
        this.personService = personService;
        this.modelMapper = modelMapper;
        this.targetService = targetService;
        this.emailValidator = emailValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<PersonDTO> addPerson(@RequestBody @Valid PersonDTO personDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }
            throw new PersonNotCreateException(errors.toString());
        }

        if (emailValidator.isValid(personDTO.getEmail())) {
            throw new PersonNotCreateException("Person with email - \"" + personDTO.getEmail() + "\" already exists. ");
        }

        Person person = convertPersonDTOToPerson(personDTO);

        var personDTOResponse = convertPersonToPersonDTO(personService.savePerson(person));
        return new ResponseEntity<>(personDTOResponse, HttpStatus.CREATED);
    }

    public Person convertPersonDTOToPerson(PersonDTO personDTO) {
        Optional<Target> targetOptional = targetService.getTargetByName(personDTO.getTargetName());
        if (targetOptional.isEmpty()) {
            throw new TargetNotFoundException("Target - \"" + personDTO.getTargetName() + "\" not found. "
                    + "Use only - Похудение, Поддержание, Набор массы");
        }
        Target target = targetOptional.get();
        Person person = modelMapper.map(personDTO, Person.class);
        person.setTarget(target);

        return person;
    }

    public PersonDTO convertPersonToPersonDTO(Person person) {
        var personDTO = modelMapper.map(person, PersonDTO.class);
        personDTO.setTargetName(person.getTarget().getName());
        return personDTO;
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(TargetNotFoundException exception) {
        var errorResponse = new PersonErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(PersonNotCreateException exception) {
        var errorResponse = new PersonErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
