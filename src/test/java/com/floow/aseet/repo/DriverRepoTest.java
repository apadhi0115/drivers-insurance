package com.floow.aseet.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.floow.aseet.model.Driver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

public class DriverRepoTest {
    @Mock
    private DriverRepo driverRepo;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void get_drivers_test() throws IOException {
        Driver driver = new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now());
        List driverList = List.of(driver);
        when(driverRepo.readJsonDataFromFlatFile()).thenReturn(driverList);
        Assertions.assertEquals(driverRepo.readJsonDataFromFlatFile(),driverList);
    }

    @Test
    public void save_driver_test() throws IOException {
        Driver driver = new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now());
        driverRepo.writeJsonDataToFlatFile(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(driver));
        Mockito.verify(driverRepo).writeJsonDataToFlatFile(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(driver));
    }

    @Test
    public void get_drivers_by_date_test() throws IOException {
        List<Driver> drivers = List.of(new Driver(UUID.randomUUID() , "Aseet", "Padhi", "1980-05-01", LocalDateTime.now()),
                new Driver(UUID.randomUUID() , "Kumar", "Das", "1990-11-11", getLocalDateFromStringDate("2019-12-12")));
        when(driverRepo.readJsonDataFromFlatFile()).thenReturn(drivers);
        Assertions.assertEquals(driverRepo.readJsonDataFromFlatFile().get(0),drivers.get(0));
    }

    private LocalDateTime getLocalDateFromStringDate(String date){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(date, FORMATTER);
        return LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
    }
}
