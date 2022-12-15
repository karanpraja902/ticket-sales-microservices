package com.karan.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karan.event.data.Section;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;

@Data
public class EventDTO {

    private String venue;
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
    private String title;
    private String details;
    @JsonProperty("max_capacity")
    private HashMap<Section, Integer> maxCapacity;
    private String organizer;

}
