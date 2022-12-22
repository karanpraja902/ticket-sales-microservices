package com.karan.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karan.ticket.data.Section;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDTO {

    @JsonProperty("event_code")
    private String eventCode;
    private Section section;
    @JsonProperty("customer_email")
    private String customerEmail;

}
