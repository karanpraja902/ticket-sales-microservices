package com.karan.ticket.repository;

import com.karan.ticket.TicketApplication;
import com.karan.ticket.config.PostgresContainerEnv;
import com.karan.ticket.data.Ticket;
import com.karan.ticket.data.TicketDetails;
import com.karan.ticket.data.TicketDetailsTest;
import com.karan.ticket.data.TicketTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TicketApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketRepositoryTest extends PostgresContainerEnv {

    @Autowired
    private TicketDetailsRepository ticketDetailsRepository;
    @Autowired
    private TicketRepository ticketRepository;

    TicketDetails expected;

    @BeforeEach
    void setUp() {
        expected =TicketDetailsTest.ticketDetailsVIP();
        ticketDetailsRepository.save(expected);
    }

    @Test
    void getTicketDetailsByEvent() {

        List<TicketDetails> actual = ticketDetailsRepository
                .getTicketDetailsByEvent(expected.getPk().getEventId());

        assertThat(actual.get(0)).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void getStock() {

        int actual = ticketDetailsRepository.getStock(
                expected.getPk().getEventId(), expected.getPk().getSection());

        assertThat(actual).isEqualTo(expected.getTotalStock());
    }

    @Test
    void getSold() {
        Ticket ticket = TicketTest.ticketInit();
        ticketRepository.save(ticket);

        int actual = ticketRepository.getSold(ticket.getEventId(), ticket.getSection());

        assertThat(actual).isEqualTo(1);
    }
}
