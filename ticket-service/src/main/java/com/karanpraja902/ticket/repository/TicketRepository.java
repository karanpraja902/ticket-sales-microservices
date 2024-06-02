package com.karanpraja902.ticket.repository;

import com.karanpraja902.ticket.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.event.eventId = ?1")
    List<Ticket> getByEvent(Long id);
}
