package com.floow.aseet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.floow.aseet.model.Driver;
import com.floow.aseet.repo.DriverRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverService.class);
    ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    Map<String, String> map = new HashMap<>();

    @Autowired
    DriverRepo repo;

    /**
     * Get the list of the drivers from the data file
     * @return List<Driver>
     * @throws IOException
     */
    public List<Driver> getDrivers() throws IOException {
        return repo.readJsonDataFromFlatFile();
    }

    /**
     * Retrieves drivers after the given date
     * @param date
     * @return List<Driver>
     * @throws IOException
     */
    public List<Driver> getDrivers(String date) throws IOException {
        List<Driver> drivers = repo.readJsonDataFromFlatFile();
        LOGGER.debug("original list size ", drivers.size());
        LocalDateTime dateTimeInput = getLocalDateFromStringDate(date);
        List<Driver> filteredList = drivers.stream().filter(driver -> driver.getCreation_date().isAfter(dateTimeInput)).collect(Collectors.toList());
        LOGGER.debug("filtered list size ", filteredList.size());
        return filteredList;
    }

    /**
     * Saves driver to the file
     * @param driver
     * @return ResponseEntity
     * @throws IOException
     */
    public ResponseEntity saveDriver(Driver driver) throws IOException {
        driver.setId(UUID.randomUUID());
        driver.setCreation_date(LocalDateTime.now());
        List<Driver> existDriverList = getDrivers();
        existDriverList.add(driver);
        String driverJson = mapper.writeValueAsString(existDriverList);
        repo.writeJsonDataToFlatFile(driverJson);
        LOGGER.debug("Driver {} saved successfully ", driver);
        return new ResponseEntity<Driver>(driver, HttpStatus.CREATED);
    }

    /**
     * Converts string date to Local date time
     * @param date
     * @return LocalDateTime
     */
    private LocalDateTime getLocalDateFromStringDate(String date) {
        DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ld = LocalDate.parse(date, FORMATTER);
        LocalDateTime ldt = LocalDateTime.of(ld, LocalDateTime.now().toLocalTime());
        LOGGER.info("Local date time {} for input ", ldt);
        return ldt;
    }
}
