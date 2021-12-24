package com.floow.aseet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.floow.aseet.model.Driver;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DriverInsuranceApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturn_drivers() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/drivers")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void shouldSave_driver() throws Exception {
        Driver driver = new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1980-05-01", LocalDateTime.now());
        String driverJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(driver);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/driver/create")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void saving_the_driver_should_throw_bad_request_error() throws Exception {
        Driver driver = new Driver(UUID.randomUUID(), "Aseet", "Padhi", "05-01-1980", LocalDateTime.now());
        String driverJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(driver);
        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/driver/create")
                        .content(driverJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(400);

    }

    @Test
    public void get_the_drivers_by_date() throws Exception {
        Driver driver = new Driver(UUID.randomUUID(), "Aseet", "Padhi", "05-01-1980", LocalDateTime.now());
        String driverJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(driver);
        MvcResult result = this.mockMvc.perform(get("/drivers/byDate?date=2021-12-22")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertThat(result).isNotNull();

        Assertions.assertThat(result.getResponse().getStatus()).isEqualTo(200);

    }
}
