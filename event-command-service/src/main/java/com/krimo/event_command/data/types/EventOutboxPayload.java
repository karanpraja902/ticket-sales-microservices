package com.karan.event_command.data.types;

public record EventOutboxPayload (
        Long eventId,
        Boolean isActive
) {
}
