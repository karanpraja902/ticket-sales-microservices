package com.karan.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karan.ticket.data.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {

    @JsonProperty("event_code")
    private String eventCode;
    private Section section;
    @JsonProperty("customer_email")
    private String customerEmail;

}
