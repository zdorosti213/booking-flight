package com.dfreight.reservation.service;

import com.dfreight.flightticket.TicketDto;
import com.dfreight.reservation.aspect.PerformanceLogger;
import com.dfreight.reservation.event.PersisEvent;
import com.dfreight.reservation.model.Ticket;
import com.dfreight.reservation.service.restclient.FlightTicketClient;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ApplicationEventPublisher eventPublisher;
    private final FlightTicketClient flightTicketClient;

    @Override
    @PerformanceLogger
    public List<TicketDto> getAllFlights(String source, String destination) {
        List<TicketDto> ticketList = flightTicketClient.getTicketListJson();

        eventPublisher.publishEvent(new PersisEvent(this, Ticket.mapDto2Entity(ticketList), Ticket.class));

        return filterBySourceAndDestination(ticketList, source,  destination);
    }

    public List<TicketDto> filterBySourceAndDestination(List<TicketDto> ticketList, String source, String destination){
        return ticketList.stream()
                .collect(Collectors.toMap(TicketDto::getAirline, TicketDto::getTicketInfos))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> entry.getValue()
                                .stream()
                                .filter(t -> source != null ? source.equals(t.getFrom()) : true)
                                .filter(t -> destination != null ? destination.equals(t.getTo()) : true)
                                .collect(Collectors.toList()))
                ).entrySet()
                .stream().map(e -> {
                    TicketDto ticketDto = new TicketDto();
                    ticketDto.setAirline(e.getKey());
                    ticketDto.setTicketInfos(e.getValue());
                    return ticketDto;
                })
                .collect(Collectors.toList());
    }
}
