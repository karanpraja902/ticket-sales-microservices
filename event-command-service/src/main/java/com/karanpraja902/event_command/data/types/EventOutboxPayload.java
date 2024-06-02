package com.karanpraja902.event_command.data.types;

public record EventOutboxPayload (
        Long eventId,
        Boolean isActive
) {
}
