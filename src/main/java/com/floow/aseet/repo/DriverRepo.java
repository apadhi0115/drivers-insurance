package com.floow.aseet.repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.floow.aseet.model.Driver;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DriverRepo {

    //@Bean
    public void writeJsonDataToFlatFile(String jsonData) throws IOException {
        try(FileWriter writer = new FileWriter("floow.txt")){
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.write(jsonData);
        }
    }

    //@Bean
    public List<Driver> readJsonDataFromFlatFile() throws IOException {
        if(checkIfFileExists()) {
            try (Reader reader = new FileReader("floow.txt")) {
                if (null != reader && reader.ready()) {
                    Driver[] drivers = new ObjectMapper().registerModule(new JavaTimeModule()).readValue(reader, Driver[].class);
                    return Arrays.stream(drivers).collect(Collectors.toList());
                } else {
                    return new ArrayList<>();
                }
            }
        }
        return new ArrayList<>();
    }

    private boolean checkIfFileExists(){
        Path path = Paths.get("floow.txt");
       return Files.exists(path,new LinkOption[]{ LinkOption.NOFOLLOW_LINKS });
    }

}
