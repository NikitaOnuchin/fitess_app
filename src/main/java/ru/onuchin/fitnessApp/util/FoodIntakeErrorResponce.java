package ru.onuchin.fitnessApp.util;

public class FoodIntakeErrorResponce {
    private String message;

    public FoodIntakeErrorResponce(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
