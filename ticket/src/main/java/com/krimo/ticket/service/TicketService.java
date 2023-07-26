package com.karan.ticket.service;

import com.karan.ticket.dao.EventDAO;
import com.karan.ticket.data.Outbox;
import com.karan.ticket.data.Section;
import com.karan.ticket.data.Ticket;
import com.karan.ticket.dto.TicketDTO;
import com.karan.ticket.exception.ApiRequestException;
import com.karan.ticket.payload.TicketPurchasePayload;
import com.karan.ticket.repository.OutboxRepository;
import com.karan.ticket.repository.TicketDetailsRepository;
import com.karan.ticket.repository.TicketRepository;
import com.karan.ticket.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public interface TicketService {

    Long purchaseTicket(TicketDTO ticketDTO);
    TicketDTO viewTicket(Long id);
    void cancelPurchase(Long id);
}

@Service
@RequiredArgsConstructor
@Transactional
class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketDetailsRepository ticketDetailsRepository;
    private final OutboxRepository outboxRepository;
    private final EventDAO eventDAO;


    @Override
    public Long purchaseTicket(TicketDTO ticketDTO) {

        Long eventId = ticketDTO.getEventId();
        Section section = ticketDTO.getSection();
        Long purchasedBy = ticketDTO.getPurchasedBy();

        // 1. Check if event is canceled
        if (eventDAO.isCanceled(eventId)) { throw new ApiRequestException("Event has been canceled."); }

        // 2. Check if sold out
        int sold = ticketRepository.getSold(eventId, section);

        int stock = ticketDetailsRepository.getStock(eventId, section);

        if (sold >= stock) { throw new ApiRequestException("Sold out."); }

        // 3. If event not canceled and ticket not sold out, create and save ticket, then save to outbox
        String ticketCode = Utils.generateSerialCode();

        Ticket ticket = Ticket.create(eventId, section, ticketCode, purchasedBy);
        Long id = ticketRepository.saveAndFlush(ticket).getId();

        String topic = "ticket_purchase";
        String eventName = eventDAO.getEventName(eventId).orElseThrow();
        String payload = Utils.writeTPPayloadToJson(new TicketPurchasePayload(eventName, purchasedBy));

        outboxRepository.save(Outbox.publish(topic, payload));

        return id;
    }

    @Override
    public TicketDTO viewTicket(Long id) {
        Ticket ticket = ticketRepository.findById(id).orElseThrow();
        return mapToTicketDTO(ticket);
    }

    @Override
    public void cancelPurchase(Long id) {
        ticketRepository.deleteById(id);
    }

    private TicketDTO mapToTicketDTO(Ticket ticket) {
        return TicketDTO.builder()
                .id(ticket.getId())
                .eventId(ticket.getEventId())
                .section(ticket.getSection())
                .ticketCode(ticket.getTicketCode())
                .purchasedBy(ticket.getPurchasedBy())
                .purchasedAt(ticket.getPurchasedAt())
                .build();
    }
}
