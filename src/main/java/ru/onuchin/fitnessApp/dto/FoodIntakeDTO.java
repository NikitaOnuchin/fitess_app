package ru.onuchin.fitnessApp.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public class FoodIntakeDTO {

    @NotNull(message = "personName cannot be null")
    private String personName;

    private String name;

    private LocalDateTime createDate;

    @NotNull(message = "dishNames cannot be null")
    private List<String> dishNames;

    public FoodIntakeDTO(String personName, String foodIntake, LocalDateTime createDate, List<String> dishNames) {
        this.personName = personName;
        this.name = foodIntake;
        this.createDate = createDate;
        this.dishNames = dishNames;
    }

    public FoodIntakeDTO() {
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public List<String> getDishNames() {
        return dishNames;
    }

    public void setDishNames(List<String> dishNames) {
        this.dishNames = dishNames;
    }
}
