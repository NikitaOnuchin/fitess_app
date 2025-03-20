package ru.onuchin.fitnessApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.onuchin.fitnessApp.dto.FoodIntakeDTO;
import ru.onuchin.fitnessApp.models.Dish;
import ru.onuchin.fitnessApp.models.FoodIntake;
import ru.onuchin.fitnessApp.models.FoodIntakeDish;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.services.DishService;
import ru.onuchin.fitnessApp.services.FoodIntakeService;
import ru.onuchin.fitnessApp.services.PersonService;
import ru.onuchin.fitnessApp.util.DishNotFoundException;
import ru.onuchin.fitnessApp.util.FoodIntakeErrorResponce;
import ru.onuchin.fitnessApp.util.FoodIntakeNotCreateException;
import ru.onuchin.fitnessApp.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/foodIntake")
public class FoodIntakeController {

    private final FoodIntakeService foodIntakeService;
    private final ModelMapper modelMapper;
    private final DishService dishService;
    private final PersonService personService;

    @Autowired
    public FoodIntakeController(FoodIntakeService foodIntakeService, ModelMapper modelMapper, DishService dishService, PersonService personService) {
        this.foodIntakeService = foodIntakeService;
        this.modelMapper = modelMapper;
        this.dishService = dishService;
        this.personService = personService;
    }

    @PostMapping("/add")
    public ResponseEntity<FoodIntakeDTO> addFoodIntake(@RequestBody @Valid FoodIntakeDTO foodIntakeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }
            throw new FoodIntakeNotCreateException(errors.toString());
        }

        FoodIntake foodIntake = convertFoodIntakeDTOToFoodIntake(foodIntakeDTO);
        foodIntake = foodIntakeService.saveFoodIntake(foodIntake);

        FoodIntakeDTO foodIntakeDTOResponse = convertFoodIntakeToFoodIntakeDTO(foodIntake);

        return new ResponseEntity<>(foodIntakeDTOResponse, HttpStatus.CREATED);

    }

    private FoodIntake convertFoodIntakeDTOToFoodIntake(FoodIntakeDTO foodIntakeDTO) {
        var foodIntake = new FoodIntake();
        foodIntake.setName(foodIntakeDTO.getName());
        Optional<Person> optionalPerson = personService.getPersonByName(foodIntakeDTO.getPersonName());
        if (optionalPerson.isPresent()) {
            foodIntake.setPerson(optionalPerson.get());
        } else {
            throw new PersonNotFoundException("Person - \"" + foodIntakeDTO.getPersonName() + "\" not found");
        }
        List<Dish> dishes = new ArrayList<>();

        for (String dishName : foodIntakeDTO.getDishNames()) {
            Optional<Dish> optionalDish = dishService.getDishByNames(dishName);
            if (optionalDish.isPresent()) {
                dishes.add(optionalDish.get());
            } else {
                throw new DishNotFoundException("DishNames contains dishes that are not in the system");
            }
        }

        for (Dish dish : dishes) {
            FoodIntakeDish foodIntakeDish = new FoodIntakeDish();
            foodIntakeDish.setDish(dish);
            foodIntakeDish.setFoodIntake(foodIntake);
            foodIntake.getFoodIntakeDishList().add(foodIntakeDish);
        }

        return foodIntake;
    }

    private FoodIntakeDTO convertFoodIntakeToFoodIntakeDTO(FoodIntake foodIntake) {
        var foodIntakeDTO = modelMapper.map(foodIntake, FoodIntakeDTO.class);
        List<String> dishNames = new ArrayList<>();
        foodIntake.getFoodIntakeDishList().forEach(
                foodIntakeDish ->
                        dishNames.add(foodIntakeDish.getDish().getName())
        );
        foodIntakeDTO.setDishNames(dishNames);

        return foodIntakeDTO;
    }

    @ExceptionHandler
    private ResponseEntity<FoodIntakeErrorResponce> handleErrors(PersonNotFoundException exception) {
        var errorResponse = new FoodIntakeErrorResponce(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<FoodIntakeErrorResponce> handleErrors(DishNotFoundException exception) {
        var errorResponse = new FoodIntakeErrorResponce(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<FoodIntakeErrorResponce> handleErrors(FoodIntakeNotCreateException exception) {
        var errorResponse = new FoodIntakeErrorResponce(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
