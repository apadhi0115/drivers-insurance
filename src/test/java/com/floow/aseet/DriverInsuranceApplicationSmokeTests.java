package com.floow.aseet;

import com.floow.aseet.controller.DriverController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DriverInsuranceApplicationSmokeTests {

    @Autowired
    private DriverController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
