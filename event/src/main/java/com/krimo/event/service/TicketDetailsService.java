package com.karan.event.service;

import com.karan.event.data.Event;
import com.karan.event.data.Section;
import com.karan.event.data.TicketDetails;
import com.karan.event.data.TicketDetailsPK;
import com.karan.event.dto.TicketDetailsDTO;
import com.karan.event.exception.ApiRequestException;
import com.karan.event.repository.EventRepository;
import com.karan.event.repository.TicketDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface TicketDetailsService {

    void setTicketDetails(Long eventId, TicketDetailsDTO ticketDetailsDTO);

    List<TicketDetailsDTO> getTicketDetailsByEvent(Long eventId);

    void updateTicketDetails(Long eventId, String section, TicketDetailsDTO ticketDetailsDTO);

}

@Service
@Transactional
@RequiredArgsConstructor
class TicketDetailsServiceImpl implements TicketDetailsService {

    private final EventRepository eventRepository;
    private final TicketDetailsRepository ticketDetailsRepository;

    @Override
    public void setTicketDetails(Long eventId, TicketDetailsDTO ticketDetailsDTO) {

        TicketDetails ticketDetails = TicketDetails.create(
                            pk(event(eventId), ticketDetailsDTO.getSection()),
                            ticketDetailsDTO.getPrice(),
                            ticketDetailsDTO.getTotalStock());

        ticketDetailsRepository.save(ticketDetails);
    }

    @Override
    public List<TicketDetailsDTO> getTicketDetailsByEvent(Long eventId) {
        List<TicketDetailsDTO> ticketDetailsDTOList = new ArrayList<>();
        ticketDetailsRepository.getTicketDetailsByEvent(event(eventId))
                .forEach(ticketDetails -> ticketDetailsDTOList.add(mapToTicketDetailsDTO(ticketDetails)));

        return ticketDetailsDTOList;
    }

    @Override
    public void updateTicketDetails(Long eventId, String section, TicketDetailsDTO ticketDetailsDTO) {

        TicketDetails ticketDetails = ticketDetailsRepository.getReferenceById(pk(event(eventId), Section.valueOf(section.toUpperCase())));

        if (ticketDetails.getTotalSold() != null) {
            throw new ApiRequestException("Invalid request. Tickets have already been sold.");
        }

        if (ticketDetailsDTO.getPrice() != null) { ticketDetails.setPrice(ticketDetailsDTO.getPrice()); }
        if (ticketDetailsDTO.getTotalStock() != null) { ticketDetails.setTotalStock(ticketDetailsDTO.getTotalStock()); }

        ticketDetailsRepository.save(ticketDetails);
    }


    private Event event(Long id) { return eventRepository.getReferenceById(id); }

    private TicketDetailsPK pk(Event event, Section section) {
        return TicketDetailsPK.of(event, section);
    }

    // Reusable method - entity to dto mapping
    private TicketDetailsDTO mapToTicketDetailsDTO(TicketDetails ticketDetails) {
        return TicketDetailsDTO.builder()
                .eventId(ticketDetails.getPk().getEvent().getId())
                .section(ticketDetails.getPk().getSection())
                .price(ticketDetails.getPrice())
                .totalStock(ticketDetails.getTotalStock())
                .totalSold(ticketDetails.getTotalSold())
                .build();
    }
}