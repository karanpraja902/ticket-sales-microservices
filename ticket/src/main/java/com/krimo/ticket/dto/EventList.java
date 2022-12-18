package com.karan.ticket.dto;

import com.karan.ticket.data.Event;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventList {

    private List<Event> eventList;
}
