package ru.onuchin.fitnessApp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.onuchin.fitnessApp.dto.DishDTO;
import ru.onuchin.fitnessApp.models.Dish;
import ru.onuchin.fitnessApp.services.DishService;
import ru.onuchin.fitnessApp.services.PersonService;
import ru.onuchin.fitnessApp.util.DishErrorResponse;
import ru.onuchin.fitnessApp.util.DishNotCreateException;
import ru.onuchin.fitnessApp.util.PersonErrorResponse;
import ru.onuchin.fitnessApp.util.PersonNotCreateException;
import ru.onuchin.fitnessApp.validation.DishNameValidator;

import javax.validation.Valid;

@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;
    private final ModelMapper modelMapper;
    private final DishNameValidator dishNameValidator;

    @Autowired
    public DishController(DishService dishService, ModelMapper modelMapper, DishNameValidator dishNameValidator) {
        this.dishService = dishService;
        this.modelMapper = modelMapper;
        this.dishNameValidator = dishNameValidator;
    }

    @PostMapping("/add")
    public ResponseEntity<DishDTO> addDish(@RequestBody @Valid DishDTO dishDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
            }
            throw new DishNotCreateException(errors.toString());
        }

        if (dishNameValidator.isValid(dishDTO.getName())) {
            throw new DishNotCreateException("Dish with name - \"" + dishDTO.getName() + "\" already exists. ");
        }

        Dish dish = dishService.saveDish(convertDishDTOToDish(dishDTO));
        DishDTO dishDTOResponse = convertDishToDishDTO(dish);
        return new ResponseEntity<>(dishDTOResponse, HttpStatus.CREATED);
    }

    private Dish convertDishDTOToDish(DishDTO dishDTO) {
        return modelMapper.map(dishDTO, Dish.class);
    }

    private DishDTO convertDishToDishDTO(Dish dish) {
        return modelMapper.map(dish, DishDTO.class);
    }

    @ExceptionHandler
    private ResponseEntity<DishErrorResponse> handlerException(DishNotCreateException exception) {
        return new ResponseEntity<>(new DishErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
