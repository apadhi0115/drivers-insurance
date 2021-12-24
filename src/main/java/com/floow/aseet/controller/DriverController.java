package com.floow.aseet.controller;

import com.floow.aseet.model.Driver;
import com.floow.aseet.service.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.List;

@Validated
@RestController
public class DriverController {
    private static final Logger LOGGER = LoggerFactory.getLogger(DriverController.class);
    @Autowired
    private DriverService driverService;

    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> getDrivers() throws IOException {
        List<Driver> driverList = driverService.getDrivers();
        LOGGER.debug("getDrivers: driverList {} ", driverList);
        return new ResponseEntity<>(driverList, HttpStatus.OK);
    }

    @PostMapping("/driver/create")
    public ResponseEntity<Driver> saveDriver(@Valid @RequestBody Driver driver) throws IOException {
        LOGGER.debug("driver details passed from input {} ", driver);
        return driverService.saveDriver(driver);

    }

    @GetMapping("/drivers/byDate")
    public ResponseEntity<List<Driver>> getDriversByDate(@Valid @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of birth should be yyyy-MM-dd") @RequestParam String date) throws IOException {
        LOGGER.info("Date of birth passed in request param {} ", date);
        List<Driver> driverList = driverService.getDrivers(date);
        return new ResponseEntity<>(driverList, HttpStatus.OK);
    }

}
