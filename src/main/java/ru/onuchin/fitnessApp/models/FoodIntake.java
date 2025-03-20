package ru.onuchin.fitnessApp.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food_intake")
public class FoodIntake {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "person_id")
    private Person person;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    @NotNull
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "foodIntake", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<FoodIntakeDish> foodIntakeDishList = new ArrayList<>();

    public FoodIntake(Person person, String foodIntake, LocalDateTime createDate) {
        this.person = person;
        this.name = foodIntake;
        this.createDate = createDate;
    }

    public FoodIntake() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    public List<FoodIntakeDish> getFoodIntakeDishList() {
        return foodIntakeDishList;
    }

    public void setFoodIntakeDishList(List<FoodIntakeDish> foodIntakeDishList) {
        this.foodIntakeDishList = foodIntakeDishList;
    }
}
