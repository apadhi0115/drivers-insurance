package com.floow.aseet.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverTest {

    @Test
    public void test_driver_construct() {
        Driver driver = new Driver(UUID.randomUUID(), "Aseet", "Padhi", "1990-05-01", LocalDateTime.now());
        assertThat(driver).isNotNull();
        assertThat(driver.getFirstname()).isEqualToIgnoringCase("aseet");
        assertThat(driver.getLastname()).isEqualToIgnoringCase("padhi");
        assertThat(driver.getDate_of_birth()).isEqualToIgnoringCase("1990-05-01");
    }
}
