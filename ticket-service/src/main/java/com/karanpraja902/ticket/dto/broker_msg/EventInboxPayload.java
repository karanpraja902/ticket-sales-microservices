package com.karanpraja902.ticket.dto.broker_msg;

public record EventInboxPayload(
        Long eventId,
        Boolean isActive
) {
}
