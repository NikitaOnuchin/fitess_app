package ru.onuchin.fitnessApp.util;

public class ReportErrorResponse {
    private String message;

    public ReportErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
