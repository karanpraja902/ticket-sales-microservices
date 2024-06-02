package com.karanpraja902.ticket.dto;

public record TicketDTO(
        Long ticketId,
        Long eventId,
        String type,
        Double price,
        Integer qtyStock,
        Integer qtySold
) {
}
