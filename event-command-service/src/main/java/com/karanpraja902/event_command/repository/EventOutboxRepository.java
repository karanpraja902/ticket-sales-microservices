package com.karanpraja902.event_command.repository;

import com.karanpraja902.event_command.data.EventOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventOutboxRepository extends JpaRepository<EventOutbox, Long> {
}
