package ru.onuchin.fitnessApp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class CaloriesReportDTO {

    @NotNull
    private String personName;

    @NotNull
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;
    private int normCalories;
    private int sumCalories;
    private boolean isCaloriesNorm;
    private int countFoodIntake;

    public CaloriesReportDTO(String personName, LocalDate date, int normCalories, int sumCalories, boolean isCaloriesNorm, int countFoodIntake) {
        this.personName = personName;
        this.date = date;
        this.normCalories = normCalories;
        this.sumCalories = sumCalories;
        this.isCaloriesNorm = isCaloriesNorm;
        this.countFoodIntake = countFoodIntake;
    }

    public CaloriesReportDTO() {
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getSumCalories() {
        return sumCalories;
    }

    public void setSumCalories(int sumCalories) {
        this.sumCalories = sumCalories;
    }

    public int getNormCalories() {
        return normCalories;
    }

    public void setNormCalories(int normCalories) {
        this.normCalories = normCalories;
    }

    public boolean isCaloriesNorm() {
        return isCaloriesNorm;
    }

    public void setCaloriesNorm(boolean caloriesNorm) {
        isCaloriesNorm = caloriesNorm;
    }

    public int getCountFoodIntake() {
        return countFoodIntake;
    }

    public void setCountFoodIntake(int countFoodIntake) {
        this.countFoodIntake = countFoodIntake;
    }
}
