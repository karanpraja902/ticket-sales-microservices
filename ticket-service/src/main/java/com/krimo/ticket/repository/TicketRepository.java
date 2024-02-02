package com.karan.ticket.repository;

import com.karan.ticket.models.Event;
import com.karan.ticket.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    @Query("SELECT t FROM Ticket t WHERE t.event = ?1")
    List<Ticket> getByEvent(Event event);
}
