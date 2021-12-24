package com.floow.aseet.controller;

import com.floow.aseet.model.Driver;
import com.floow.aseet.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@RestController
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("/drivers")
    public ResponseEntity<List<Driver>> getDrivers() throws IOException {
        List<Driver> driverList = driverService.getDrivers();
        return new ResponseEntity<>(driverList, HttpStatus.OK);
    }

    @PostMapping("/driver/create")
    public ResponseEntity<Driver> saveDriver(@Valid @RequestBody Driver driver) throws IOException {
         return driverService.saveDriver(driver);

    }

    @GetMapping("/drivers/byDate")
    public ResponseEntity<List<Driver>> getDriversByDate(@Valid @RequestParam String date ) throws IOException {
        List<Driver> driverList = driverService.getDrivers(date);
        return new ResponseEntity<>(driverList, HttpStatus.OK);
    }

}
