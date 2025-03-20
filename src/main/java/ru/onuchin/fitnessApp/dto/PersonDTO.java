package ru.onuchin.fitnessApp.dto;

import javax.persistence.*;
import javax.validation.constraints.*;

public class PersonDTO {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 character")
    private String name;

    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @Column(name = "age")
    @NotNull(message = "Age cannot be null")
    @Min(0)
    @Max(150)
    private int age;

    @NotNull(message = "Weight cannot be null")
    @Min(0)
    @Max(300)
    private int weight;

    @NotNull(message = "Height cannot be null")
    @Min(0)
    @Max(300)
    private int height;

    private String targetName;

    private int calorieNorm;

    public PersonDTO(String name, String email, int age, int weight, int height, String targetName, int calorie_norm) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.targetName = targetName;
        this.calorieNorm = calorie_norm;
    }

    public PersonDTO(String name, String email, int age, int weight, int height, String targetName) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.targetName = targetName;
    }
    public PersonDTO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public int getCalorieNorm() {
        return calorieNorm;
    }

    public void setCalorieNorm(int calorieNorm) {
        this.calorieNorm = calorieNorm;
    }
}
