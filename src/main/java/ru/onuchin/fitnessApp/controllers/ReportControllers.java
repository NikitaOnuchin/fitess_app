package ru.onuchin.fitnessApp.controllers;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.onuchin.fitnessApp.dto.CaloriesReportDTO;
import ru.onuchin.fitnessApp.dto.CaloriesReportHistoryRequestDTO;
import ru.onuchin.fitnessApp.dto.CaloriesReportRequestDTO;
import ru.onuchin.fitnessApp.services.ReportService;
import ru.onuchin.fitnessApp.util.FoodIntakeErrorResponce;
import ru.onuchin.fitnessApp.util.PersonErrorResponse;
import ru.onuchin.fitnessApp.util.PersonNotFoundException;
import ru.onuchin.fitnessApp.util.ReportNotCreateException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportControllers {

    private final ReportService reportService;

    @Autowired
    public ReportControllers(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/calories")
    public ResponseEntity<CaloriesReportDTO> getCaloriesForPerson(@Valid CaloriesReportRequestDTO caloriesRequestDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                if (error.getField().equals("date")) {
                    errors.append(error.getField()).append(": ").append("field date should be: format: dd-MM-yyyy and not more current date").append("\n");
                } else {
                    errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
                }
            }
            throw new ReportNotCreateException(errors.toString());
        }

        CaloriesReportDTO reportDTO = reportService.getCaloriesReport(caloriesRequestDTO.getPersonName(), caloriesRequestDTO.getDate());
        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }

    @GetMapping("/caloriesHistory")
    public ResponseEntity<List<CaloriesReportDTO>> getCaloriesForPersonHistory(@Valid CaloriesReportHistoryRequestDTO caloriesReportHistoryRequestDTO,
                                                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                if (error.getField().equals("dateTo") || error.getField().equals("dateFrom")) {
                    errors.append(error.getField()).append(": ").append("Field - ").append(error.getField()).append(" should be format: dd-MM-yyyy").append("\n");
                } else {
                    errors.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("\n");
                }
            }
            throw new ReportNotCreateException(errors.toString());
        }


        List<CaloriesReportDTO> reportDTO = reportService.getCaloriesReportHistory(caloriesReportHistoryRequestDTO);
        return new ResponseEntity<>(reportDTO, HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<FoodIntakeErrorResponce> handleErrors(PersonNotFoundException exception) {
        var errorResponse = new FoodIntakeErrorResponce(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handlerException(ReportNotCreateException exception) {
        var errorResponse = new PersonErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<PersonErrorResponse> handleInvalidDateFormat(InvalidFormatException exception) {
        var errorResponse = new PersonErrorResponse(exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<PersonErrorResponse> handleInvalidDateFormat(IllegalArgumentException exception) {
        var errorResponse = new PersonErrorResponse("Error: " + exception.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
