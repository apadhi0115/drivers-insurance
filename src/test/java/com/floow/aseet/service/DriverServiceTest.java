package com.floow.aseet.service;

import com.floow.aseet.model.Driver;
import com.floow.aseet.repo.DriverRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class DriverServiceTest {

    @InjectMocks
    DriverService driverService;

    @Mock
    DriverRepo driverRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAll_drivers() throws IOException {
        List<Driver> drivers = List.of(new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now()),
                new Driver(UUID.randomUUID(), "Kumar", "Das", "1990-11-11", LocalDateTime.now()));
        when(driverService.getDrivers()).thenReturn(drivers);
        List<Driver> driverList = driverService.getDrivers();
        Assertions.assertEquals(2, driverList.size());
        verify(driverRepo).readJsonDataFromFlatFile();
    }

    @Test
    public void save_drivers() throws IOException {
        Driver driver = new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now());
        driverService.saveDriver(driver);
        verify(driverRepo).writeJsonDataToFlatFile(anyString());
    }

    @Test
    public void getAll_drivers_by_date() throws IOException {
        List<Driver> drivers = List.of(new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now()),
                new Driver(UUID.randomUUID(), "Kumar", "Das", "1990-11-11", getLocalDateFromStringDate("2019-12-12")));
        when(driverService.getDrivers("2021-12-22")).thenReturn(drivers);
        List<Driver> driverList = driverService.getDrivers("2021-12-22");
        Assertions.assertEquals(1, driverList.size());
        verify(driverRepo).readJsonDataFromFlatFile();
    }

    private LocalDateTime getLocalDateFromStringDate(String date) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(date, FORMATTER);
        return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
    }
}
