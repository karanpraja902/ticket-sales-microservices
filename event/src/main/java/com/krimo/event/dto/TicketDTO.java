package com.karan.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karan.event.data.Section;
import lombok.Data;

@Data
public class TicketDTO {

    @JsonProperty("event_code")
    private String eventCode;
    private Section section;
}
