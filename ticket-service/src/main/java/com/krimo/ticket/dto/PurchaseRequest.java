package com.karan.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karan.ticket.models.PurchaseStatus;

public record PurchaseRequest(
        Long ticketId,
        Integer quantity,
        @JsonProperty("status")
        PurchaseStatus status,
        Long customerId
) {
}
