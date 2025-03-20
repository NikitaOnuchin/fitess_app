package ru.onuchin.fitnessApp.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.onuchin.fitnessApp.dto.CaloriesReportDTO;
import ru.onuchin.fitnessApp.dto.CaloriesReportHistoryRequestDTO;
import ru.onuchin.fitnessApp.dto.CaloriesReportRequestDTO;
import ru.onuchin.fitnessApp.services.ReportService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportControllersTest {

    private final static String NAME = "Sam";
    private static final LocalDate DATE = LocalDate.parse("2025-03-19");

    @Mock
    private ReportService reportService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ReportControllers reportControllers;

    @Test
    void getCaloriesForPersonTest() {
        // Arrange
        CaloriesReportRequestDTO requestDTO = new CaloriesReportRequestDTO();
        requestDTO.setPersonName(NAME);
        requestDTO.setDate(DATE);
        CaloriesReportDTO reportDTO = new CaloriesReportDTO();
        reportDTO.setSumCalories(2000);
        when(reportService.getCaloriesReport(NAME, DATE)).thenReturn(reportDTO);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<CaloriesReportDTO> response = reportControllers.getCaloriesForPerson(requestDTO, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2000, Objects.requireNonNull(response.getBody()).getSumCalories());
    }

    @Test
    void getCaloriesForPersonHistory() {
        CaloriesReportHistoryRequestDTO requestDTO = new CaloriesReportHistoryRequestDTO();
        requestDTO.setPersonName(NAME);
        requestDTO.setDateFrom(DATE);
        requestDTO.setDateTo(DATE.plusDays(-1));
        CaloriesReportDTO reportDTO1 = new CaloriesReportDTO();
        reportDTO1.setSumCalories(2000);
        CaloriesReportDTO reportDTO2 = new CaloriesReportDTO();
        reportDTO2.setSumCalories(0);
        List<CaloriesReportDTO> reportDTOs = Arrays.asList(reportDTO1, reportDTO2);
        when(reportService.getCaloriesReportHistory(requestDTO)).thenReturn(reportDTOs);
        when(bindingResult.hasErrors()).thenReturn(false);

        ResponseEntity<List<CaloriesReportDTO>> response = reportControllers.getCaloriesForPersonHistory(requestDTO, bindingResult);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
        assertEquals(2000, response.getBody().get(0).getSumCalories());
        assertEquals(0, response.getBody().get(1).getSumCalories());
    }
}