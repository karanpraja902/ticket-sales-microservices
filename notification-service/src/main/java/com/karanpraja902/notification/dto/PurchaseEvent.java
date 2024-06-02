package com.karanpraja902.notification.dto;

import java.time.LocalDateTime;

public record PurchaseEvent(
        Long eventId,
        Long userId,
        LocalDateTime timestamp
) {
}
