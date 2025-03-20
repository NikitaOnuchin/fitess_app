package ru.onuchin.fitnessApp.models;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 50, message = "Name should be between 2 and 50 character")
    private String name;

    @Column(name = "email")
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @Column(name = "age")
    @NotNull(message = "Age cannot be null")
    @Min(0)
    @Max(150)
    private int age;

    @Column(name = "weight")
    @NotNull(message = "Weight cannot be null")
    @Min(0)
    @Max(300)
    private int weight;

    @Column(name = "height")
    @NotNull(message = "Height cannot be null")
    @Min(0)
    @Max(300)
    private int height;

    @ManyToOne
    @JoinColumn(name = "target_id", referencedColumnName = "id")
    private Target target;

    @Column(name = "calorie_norm")
    private int calorieNorm;

    public Person(String name, String email, int age, int weight, int height, Target target, int calorie_norm) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.target = target;
        this.calorieNorm = calorie_norm;
    }

    public Person(String name, String email, int age, int weight, int height, Target target) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.target = target;
    }

    public Person() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public int getCalorieNorm() {
        return calorieNorm;
    }

    public void setCalorieNorm(int calorie_norm) {
        this.calorieNorm = calorie_norm;
    }
}
