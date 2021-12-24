package com.floow.aseet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.floow.aseet.model.Driver;
import com.floow.aseet.repo.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DriverService {
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    Map<String,String> map = new HashMap<>();
    @Autowired
    DriverRepo repo;

    public List<Driver> getDrivers() throws IOException {
        return repo.readJsonDataFromFlatFile();
    }

    public List<Driver> getDrivers(String date) throws IOException {
        List<Driver> drivers = repo.readJsonDataFromFlatFile();
        System.out.println("original list size "+drivers.size());
        LocalDateTime dateTimeInput = getLocalDateFromStringDate(date);
        List<Driver> filteredList = drivers.stream().filter(driver -> driver.getCreation_date().isAfter(dateTimeInput)).collect(Collectors.toList());
        System.out.println("filtered list size "+filteredList.size());
        return filteredList;
    }

    public ResponseEntity saveDriver(Driver driver) throws IOException {
        driver.setId(UUID.randomUUID());
        driver.setCreation_date(LocalDateTime.now());
        List<Driver> existDriverList = getDrivers();
        existDriverList.add(driver);
        String driverJson = mapper.writeValueAsString(existDriverList);
        repo.writeJsonDataToFlatFile(driverJson);
        return new ResponseEntity<Driver>(driver, HttpStatus.CREATED);
    }
    private LocalDateTime getLocalDateFromStringDate(String date){
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(date, FORMATTER);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
        System.out.println("Local date time for input " +ldt);
        return ldt;
    }
}
