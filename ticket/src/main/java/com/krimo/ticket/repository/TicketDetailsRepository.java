package com.karan.ticket.repository;

import com.karan.ticket.data.Section;
import com.karan.ticket.data.TicketDetails;
import com.karan.ticket.data.TicketDetailsPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketDetailsRepository extends JpaRepository<TicketDetails, TicketDetailsPK> {

    @Query("SELECT t FROM TicketDetails t WHERE t.pk.eventId = ?1")
    List<TicketDetails> getTicketDetailsByEvent(Long eventId);

    @Query("SELECT t.totalStock FROM TicketDetails t WHERE t.pk.eventId = ?1 AND t.pk.section = ?2")
    int getStock(Long eventId, Section section);
}
