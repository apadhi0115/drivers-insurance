package com.floow.aseet.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.floow.aseet.model.Driver;
import com.floow.aseet.repo.DriverRepo;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class DriveServiceMain {


    public static void main(String[] args) throws IOException {
        DriverRepo repo = new DriverRepo();
        //Driver driver = new Driver(1L , " my name kumar", "Padhi", "1980-05-01", LocalDateTime.now());
//        List<Driver> drivers = List.of(new Driver(1L , " my name kumar", "Padhi", "1980-05-01", LocalDateTime.now()),
//                new Driver(2L , "Aseet", "Padhi", "1990-07-01", LocalDateTime.now()));
//        repo.writeJsonDataToFlatFile(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(drivers));


            try (Reader reader = new FileReader("floow.txt")) {
                //Gson gson = new Gson();
                Driver[] drivers = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(reader, Driver[].class);
                Arrays.stream(drivers).forEach(System.out::println);
            }

    }
}
