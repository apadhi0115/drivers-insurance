package com.floow.aseet.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Driver {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("firstname")
    private String firstname;
    @JsonProperty("lastname")
    private String lastname;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date of birth should be yyyy-MM-dd")
    @JsonProperty("date_of_birth")
    private String date_of_birth;
    @JsonProperty("creation_date")
    private LocalDateTime creation_date;
}

