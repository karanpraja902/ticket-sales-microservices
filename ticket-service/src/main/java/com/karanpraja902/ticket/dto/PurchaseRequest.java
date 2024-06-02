package com.karanpraja902.ticket.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.karanpraja902.ticket.models.PurchaseStatus;

public record PurchaseRequest(
        Long ticketId,
        Integer quantity,
        @JsonProperty("status")
        PurchaseStatus status,
        Long customerId
) {
}
