package com.floow.aseet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.floow.aseet.model.Driver;
import com.floow.aseet.service.DriverService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DriverController.class)
public class DriverControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private DriverService driverService;

    private List<Driver> drivers;

    private String driverJson;

    private Driver driver;

    private String URI = "/drivers";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        drivers = List.of(new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now()),
                new Driver(UUID.randomUUID(), "Amit", "Padhi", "1990-06-01", LocalDateTime.now()));

        driver = new Driver(UUID.randomUUID(), "Junta", "kahani", "1991-07-01", LocalDateTime.now());
        driverJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(drivers);
    }

    @Test
    void getDrivers_from_service() throws Exception {

        Mockito.when(driverService.getDrivers()).thenReturn(drivers);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String driversJson = result.getResponse().getContentAsString();
        Assertions.assertThat(driversJson).isEqualToIgnoringCase(mapper.writeValueAsString(drivers));
    }


    @Test
    void save_driver_from_service() throws Exception {

        Mockito.when(driverService.getDrivers()).thenReturn(drivers);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/driver/create")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void saving_the_driver_should_throw_error() throws Exception {
        final MvcResult[] result = new MvcResult[1];
        Driver driver = new Driver(UUID.randomUUID(), "Aseet", "Padhi", "05-01-1980", LocalDateTime.now());
        String driverJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(driver);
        result[0] = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/driver/create")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result[0].getResponse().getStatus()).isEqualTo(400);

    }

    @Test
    void getDrivers_from_service_by_date() throws Exception {
        List driversAfterDate = List.of(new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now()),
                new Driver(UUID.randomUUID(), "Amit", "Padhi", "1990-06-01", LocalDateTime.now()));

        Mockito.when(driverService.getDrivers("2021-12-22")).thenReturn(driversAfterDate);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/drivers/byDate?date=2021-12-22")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String driversJson = result.getResponse().getContentAsString();
        Assertions.assertThat(driversJson).isEqualToIgnoringCase(mapper.writeValueAsString(driversAfterDate));
    }

    @Test
    void getDrivers_from_service_by_date_throws_invalid_exception() throws Exception {
        List driversAfterDate = List.of(new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now()),
                new Driver(UUID.randomUUID(), "Amit", "Padhi", "1990-06-01", LocalDateTime.now()));

        Mockito.when(driverService.getDrivers("22-12-2021")).thenReturn(driversAfterDate);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/drivers/byDate?date=22-12-2021")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
        String driversJson = result.getResponse().getContentAsString();
        Assertions.assertThat(driversJson).contains("getDriversByDate.date: Date of birth should be yyyy-MM-dd");
    }

}

