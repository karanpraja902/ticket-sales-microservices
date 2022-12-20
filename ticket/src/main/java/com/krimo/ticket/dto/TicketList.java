package com.karan.ticket.dto;

import com.karan.ticket.data.Ticket;
import lombok.*;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketList {

    private List<Ticket> ticketList;
}
