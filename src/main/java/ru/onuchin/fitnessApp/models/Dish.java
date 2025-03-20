package ru.onuchin.fitnessApp.models;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Dish {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "calories")
    @NotNull
    @Min(0)
    private Integer calories;

    @Column(name = "proteins")
    @NotNull
    @DecimalMin("0.0")
    private Double proteins;

    @Column(name = "fats")
    @NotNull
    @DecimalMin("0.0")
    private Double fats;

    @Column(name = "carbohydrates")
    @NotNull
    @DecimalMin("0.0")
    private Double carbohydrates;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FoodIntakeDish> foodIntakeDishList = new ArrayList<>();

    public Dish(String name, int calories, double proteins, double fats, double carbohydrates) {
        this.name = name;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbohydrates = carbohydrates;
    }

    public Dish() {
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

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public Double getProteins() {
        return proteins;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(Double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public List<FoodIntakeDish> getFoodIntakeDishList() {
        return foodIntakeDishList;
    }

    public void setFoodIntakeDishList(List<FoodIntakeDish> foodIntakeDishList) {
        this.foodIntakeDishList = foodIntakeDishList;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof Dish)) return false;

        Dish dish = (Dish) object;
        return Objects.equals(id, dish.id) && Objects.equals(name, dish.name) && Objects.equals(calories, dish.calories) && Objects.equals(proteins, dish.proteins) && Objects.equals(fats, dish.fats) && Objects.equals(carbohydrates, dish.carbohydrates) && Objects.equals(foodIntakeDishList, dish.foodIntakeDishList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(name);
        result = 31 * result + Objects.hashCode(calories);
        result = 31 * result + Objects.hashCode(proteins);
        result = 31 * result + Objects.hashCode(fats);
        result = 31 * result + Objects.hashCode(carbohydrates);
        result = 31 * result + Objects.hashCode(foodIntakeDishList);
        return result;
    }
}
