package ru.onuchin.fitnessApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "food_intake_dish")
public class FoodIntakeDish {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "food_intake_id")
    private FoodIntake foodIntake;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    @NotNull
    private Dish dish;

    public FoodIntakeDish(FoodIntake personFoodIntake, Dish dish) {
        this.foodIntake = personFoodIntake;
        this.dish = dish;
    }

    public FoodIntakeDish() {
    }

    public FoodIntakeDish(Dish dish) {
        this.dish = dish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FoodIntake getFoodIntake() {
        return foodIntake;
    }

    public void setFoodIntake(FoodIntake personFoodIntake) {
        this.foodIntake = personFoodIntake;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
