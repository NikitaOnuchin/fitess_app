package ru.onuchin.fitnessApp.dto;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

public class CaloriesReportHistoryRequestDTO {

    @NotNull(message = "personName cannot be null")
    private String personName;

    @NotNull(message = "date cannot be null")
    @PastOrPresent(message = "The date cannot be in the future")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateFrom;

    @NotNull(message = "date cannot be null")
    @PastOrPresent(message = "The date cannot be in the future")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateTo;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }
}
