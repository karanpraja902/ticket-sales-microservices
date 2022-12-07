package com.karan.ticket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karan.ticket.data.Event;
import com.karan.ticket.data.Section;
import com.karan.ticket.data.Ticket;
import com.karan.ticket.dto.TicketDTO;
import com.karan.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final TicketRepository ticketRepository;

    public String buyTicket(TicketDTO ticketDTO) throws JsonProcessingException {

        String data = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ticketDTO);

        final String uri = "http://localhost:8081/api/v1/event/%s/attendees";

        Collection<Section> fullSections = webClient
                .put()
                .uri(String.format(uri, ticketDTO.getEventCode()))
                .header(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(data))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Collection<Section>>() {})
                .block();

        if(fullSections!=null) {
            if (fullSections.contains(ticketDTO.getSection())) {
                return null;
            }
        }
//                .body(Mono.just(ticketDTO), TicketDTO.class);
//                .bodyValue(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(ticketDTO));

        String ticketCode = UUID.randomUUID().toString();

        Ticket ticket = Ticket.builder()
                .ticketCode(ticketCode)
                .section(ticketDTO.getSection())
                .purchaseDateTime(LocalDateTime.now())
                .build();

        Collection<Ticket> tickets = new ArrayList<>();

        Event event = ticketRepository.findByEventCode(ticketDTO.getEventCode()).isPresent()
                ? ticketRepository.findByEventCode(ticketDTO.getEventCode()).get()
                : new Event(ticketDTO.getEventCode(), tickets);

        event.setEventCode(event.getEventCode());
        tickets = event.getTickets();
        tickets.add(ticket);
        event.setTickets(tickets);
        ticketRepository.save(event);

        return ticketCode;
    }

    public List<Event> allEvents () {
        return ticketRepository.findAll().stream().toList();
    }

    public List<Ticket> eventTickets(String eventCode) {
        return ticketRepository.findByEventCode(eventCode).get().getTickets().stream().toList();
    }
}
