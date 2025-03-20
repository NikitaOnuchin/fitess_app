package ru.onuchin.fitnessApp.util;

public class DishErrorResponse {
    private String message;

    public DishErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
