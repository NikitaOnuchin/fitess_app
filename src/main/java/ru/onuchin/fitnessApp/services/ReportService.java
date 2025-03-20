package ru.onuchin.fitnessApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.onuchin.fitnessApp.dto.CaloriesReportDTO;
import ru.onuchin.fitnessApp.dto.CaloriesReportHistoryRequestDTO;
import ru.onuchin.fitnessApp.models.FoodIntake;
import ru.onuchin.fitnessApp.models.Person;
import ru.onuchin.fitnessApp.repositories.FoodIntakeRepository;
import ru.onuchin.fitnessApp.util.PersonNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportService {

    private final PersonService personService;
    private final FoodIntakeRepository foodIntakeRepository;

    @Autowired
    public ReportService(PersonService personService, FoodIntakeRepository foodIntakeRepository) {
        this.personService = personService;
        this.foodIntakeRepository = foodIntakeRepository;
    }

    public CaloriesReportDTO getCaloriesReport(String personName, LocalDate date) throws PersonNotFoundException {
        Optional<Person> personOptional = personService.getPersonByName(personName);

        if (personOptional.isEmpty()) {
            throw new PersonNotFoundException("Person - \"" + personName + "\" not found");
        }

        Person person = personOptional.get();

        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        List<FoodIntake> foodIntakeList = foodIntakeRepository.findByPersonAndCreateDateBetween(person, startOfDay, endOfDay);

        int sumCalories = getSumCalories(foodIntakeList);
        int countFoodIntake = foodIntakeList.size();

        boolean isCaloriesNorm = IsCaloriesNorm(sumCalories, person.getCalorieNorm());

        return new CaloriesReportDTO(person.getName(), date, person.getCalorieNorm(), sumCalories, isCaloriesNorm, countFoodIntake);
    }

    static boolean IsCaloriesNorm(int sumCalories, int normCalories) {
        return sumCalories <= normCalories;
    }

    static int getSumCalories(List<FoodIntake> foodIntakeList) {
        int sumCalories = 0;
        for (FoodIntake foodIntake : foodIntakeList) {
            for (var foodIntakeDish : foodIntake.getFoodIntakeDishList()) {
                sumCalories += foodIntakeDish.getDish().getCalories();
            }
        }
        return sumCalories;
    }

    public List<CaloriesReportDTO> getCaloriesReportHistory(CaloriesReportHistoryRequestDTO reqDTO) {
        LocalDate startDate = reqDTO.getDateFrom();
        LocalDate endDate = reqDTO.getDateTo();

        List<LocalDate> localDates = startDate.datesUntil(endDate.plusDays(1))
                .collect(Collectors.toList());

        List<CaloriesReportDTO> caloriesReportDTOList = new ArrayList<>();
        localDates.forEach(localDate -> {
            caloriesReportDTOList.add(getCaloriesReport(reqDTO.getPersonName(), localDate));
        });

        return caloriesReportDTOList;
    }
}
